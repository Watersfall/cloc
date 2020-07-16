package com.watersfall.clocgame.action;

import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.decisions.Decision;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationEconomy;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;

public class DecisionActions
{
	public DecisionActions(){}

	//<editor-fold desc="Domestic Policies">
	public static String arrest(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.INCREASE_ARREST_QUOTAS);
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
			nation.getDomestic().setStability(nation.getDomestic().getStability() + 5);
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() - 5);
			nation.getDomestic().setGovernment(nation.getDomestic().getGovernment() - 5);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			return Responses.arrest();
		}
	}

	public static String free(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.PARDON_CRIMINALS);
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
			nation.getDomestic().setStability(nation.getDomestic().getStability() - 5);
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + 5);
			nation.getDomestic().setGovernment(nation.getDomestic().getGovernment() + 5);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			return Responses.free();
		}
	}

	public static String landClearance(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.LAND_CLEARANCE);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else
		{
			int gain = (int)(Math.random() * 2500) + 500;
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.getDomestic().setLand(nation.getDomestic().getLand() + gain);
			return Responses.landClearance(gain);
		}
	}

	public static String propaganda(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.PROPAGANDA);
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
			return Responses.propaganda();
		}
	}

	public static String warPropaganda(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.WAR_PROPAGANDA);
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
			return Responses.propaganda();
		}
	}
	//</editor-fold>

	//<editor-fold desc="Economic Policies">
	public static String freeMoneyCapitalist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = nation.getEconomy();
		economy.setBudget(economy.getBudget() + 1000);
		economy.setEconomic(economy.getEconomic() + 5);
		return Responses.freeMoneyCapitalist();
	}

	public static String freeMoneyCommunist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = nation.getEconomy();
		economy.setBudget(economy.getBudget() + 1000);
		economy.setEconomic(economy.getEconomic() - 5);
		return Responses.freeMoneyCommunist();
	}
	//</editor-fold>

	//<editor-fold desc="Foreign Policies">
	private static String align(Nation nation, int align) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.ALIGN_NEUTRAL);
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
			return Responses.deconscript();
		}
	}

	public static String train(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		long cost = nation.getDecisionCost(Decision.TRAIN);
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
			return Responses.train();
		}
	}

	public static String fortify(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		long cost = nation.getDecisionCost(Decision.FORTIFY);
		if(nation.getEconomy().getSteel() < cost)
		{
			return Responses.noSteel();
		}
		else if(nation.getArmy().getFortification() > 10000)
		{
			return Responses.alreadyFortified();
		}
		else
		{
			int increase = nation.getMaximumFortificationLevel() / 10;
			nation.getArmy().setFortification(nation.getArmy().getFortification() + increase);
			nation.getEconomy().setSteel(nation.getEconomy().getSteel() - cost);
			return Responses.fortified();
		}
	}
	//</editor-fold>
}
