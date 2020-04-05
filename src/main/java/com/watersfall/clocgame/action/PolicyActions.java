package com.watersfall.clocgame.action;

import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationEconomy;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;

public class PolicyActions
{
	public PolicyActions(){}

	//<editor-fold desc="Constants"
	//<editor-fold desc="IDs">
	public static final int ID_COAL_MINE = 0;
	public static final int ID_IRON_MINE = 1;
	public static final int ID_DRILL = 2;
	public static final int ID_INDUSTRIALIZE = 3;
	public static final int ID_MILITARIZE = 4;
	public static final int ID_NITROGEN_PLANT = 5;
	public static final int ID_UNIVERSITY = 6;
	public static final int ID_PORT = 7;
	public static final int ID_BARRACK = 8;
	public static final int ID_RAILROAD = 9;
	public static final int ID_ARREST = 10;
	public static final int ID_FREE = 11;
	public static final int ID_LAND_CLEARANCE = 12;
	public static final int ID_PROPAGANDA = 13;
	public static final int ID_WAR_PROPAGANDA = 14;
	public static final int ID_ALIGN = 15;
	public static final int ID_TRAIN = 16;
	public static final int ID_CREATE_TREATY = 17;
	//</editor-fold>
	//<editor-fold desc="Modifiers"
	private static final int CRACKDOWN_STAB = 5;
	private static final int CRACKDOWN_APPROVAL = -5;
	private static final int CRACKDOWN_GOV = -5;
	private static final int FREE_STAB = -5;
	private static final int FREE_APPROVAL = 5;
	private static final int FREE_GOV = 5;
	private static final int FREE_MONEY_ECON = 5;
	//</editor-fold>
	//<editor-fold desc="Gain">
	private static final int FREE_MONEY_GAIN = 1000;
	//</editor-fold>
	//</editor-fold>

	//<editor-fold desc="Domestic Policies">
	public static String arrest(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getPolicyCost(ID_ARREST);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getGovernment() < 5)
		{
			return Responses.noCriminals();
		}
		else if(nation.getDomestic().getApproval() < 5)
		{
			return Responses.hated();
		}
		else
		{
			nation.getDomestic().setStability(nation.getDomestic().getStability() + CRACKDOWN_STAB);
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + CRACKDOWN_APPROVAL);
			nation.getDomestic().setGovernment(nation.getDomestic().getGovernment() + CRACKDOWN_GOV);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.arrest();
		}
	}

	public static String free(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getPolicyCost(ID_FREE);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getGovernment() > 95)
		{
			return Responses.noPrisoners();
		}
		else if(nation.getDomestic().getStability() < 5)
		{
			return Responses.unstable();
		}
		else
		{
			nation.getDomestic().setStability(nation.getDomestic().getStability() + FREE_STAB);
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + FREE_APPROVAL);
			nation.getDomestic().setGovernment(nation.getDomestic().getGovernment() + FREE_GOV);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.free();
		}
	}

	public static String landClearance(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getPolicyCost(ID_LAND_CLEARANCE);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else
		{
			int gain = (int)(Math.random() * 2500) + 500;
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.getDomestic().setLand(nation.getDomestic().getLand() + gain);
			nation.update();
			return Responses.landClearance(gain);
		}
	}

	public static String propaganda(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getPolicyCost(ID_PROPAGANDA);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getApproval() > 99)
		{
			return Responses.propagandaMaxApproval();
		}
		else
		{
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + 10);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.propaganda();
		}
	}

	public static String warPropaganda(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getPolicyCost(ID_WAR_PROPAGANDA);
		if(nation.getOffensive() == null && nation.getDefensive() == null)
		{
			return Responses.propagandaNoWar();
		}
		else if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getApproval() > 99)
		{
			return Responses.propagandaMaxApproval();
		}
		else
		{
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + 10);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.propaganda();
		}
	}
	//</editor-fold>

	//<editor-fold desc="Economic Policies">
	public static String freeMoneyCapitalist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = nation.getEconomy();
		economy.setBudget(economy.getBudget() + FREE_MONEY_GAIN);
		economy.setEconomic(economy.getEconomic() + FREE_MONEY_ECON);
		nation.update();
		return Responses.freeMoneyCapitalist();
	}

	public static String freeMoneyCommunist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = nation.getEconomy();
		economy.setBudget(economy.getBudget() + FREE_MONEY_GAIN);
		economy.setEconomic(economy.getEconomic() - FREE_MONEY_ECON);
		nation.update();
		return Responses.freeMoneyCommunist();
	}
	//</editor-fold>

	//<editor-fold desc="Foreign Policies">
	private static String align(Nation nation, int align) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getPolicyCost(ID_ALIGN);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getForeign().getAlignment() == align)
		{
			return Responses.alreadyYourAlignment();
		}
		else
		{
			nation.getForeign().setAlignment(align);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.align(align);
		}
	}

	public static String alignEntente(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(nation, 1);
	}

	public static String alignNeutral(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(nation, 0);
	}

	public static String alignCentralPowers(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(nation, -1);
	}
	//</editor-fold>

	//<editor-fold desc="Military Policies">
	public static String conscript(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		if(nation.getFreeManpower() < 2000)
		{
			return Responses.noManpower();
		}
		else
		{
			nation.getArmy().setSize(nation.getArmy().getSize() + 2);
			nation.getEconomy().setRecentConscription(nation.getEconomy().getRecentConscription() + 1);
			nation.update();
			return Responses.conscript();
		}
	}

	public static String deconscript(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		if(nation.getArmy().getSize() <= 5)
		{
			return Responses.noTroops();
		}
		else
		{
			nation.getArmy().setSize(nation.getArmy().getSize() - 2);
			nation.getEconomy().setRecentDeconscription(nation.getEconomy().getRecentDeconscription() + 1);
			nation.update();
			return Responses.deconscript();
		}
	}

	public static String train(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		long cost = nation.getPolicyCost(ID_TRAIN);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getArmy().getTraining() >= 100)
		{
			return Responses.fullTrained();
		}
		else
		{
			nation.getArmy().setTraining(nation.getArmy().getTraining() + 5);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.train();
		}
	}
	//</editor-fold>


	public int getID_COAL_MINE()
	{
		return ID_COAL_MINE;
	}

	public int getID_IRON_MINE()
	{
		return ID_IRON_MINE;
	}

	public int getID_DRILL()
	{
		return ID_DRILL;
	}

	public int getID_INDUSTRIALIZE()
	{
		return ID_INDUSTRIALIZE;
	}

	public int getID_MILITARIZE()
	{
		return ID_MILITARIZE;
	}

	public int getID_NITROGEN_PLANT()
	{
		return ID_NITROGEN_PLANT;
	}

	public int getID_UNIVERSITY()
	{
		return ID_UNIVERSITY;
	}

	public int getID_PORT()
	{
		return ID_PORT;
	}

	public int getID_BARRACK()
	{
		return ID_BARRACK;
	}

	public int getID_RAILROAD()
	{
		return ID_RAILROAD;
	}

	public int getID_ARREST()
	{
		return ID_ARREST;
	}

	public int getID_FREE()
	{
		return ID_FREE;
	}

	public int getID_LAND_CLEARANCE()
	{
		return ID_LAND_CLEARANCE;
	}

	public int getID_PROPAGANDA()
	{
		return ID_PROPAGANDA;
	}

	public int getID_WAR_PROPAGANDA()
	{
		return ID_WAR_PROPAGANDA;
	}

	public int getID_ALIGN()
	{
		return ID_ALIGN;
	}

	public int getID_TRAIN()
	{
		return ID_TRAIN;
	}

	public int getID_CREATE_TREATY()
	{
		return ID_CREATE_TREATY;
	}
}
