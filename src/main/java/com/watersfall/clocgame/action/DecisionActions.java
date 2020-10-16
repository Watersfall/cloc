package com.watersfall.clocgame.action;

import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.alignment.Alignments;
import com.watersfall.clocgame.model.decisions.Decision;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationStats;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;

public class DecisionActions
{
	public DecisionActions(){}

	//<editor-fold desc="Domestic Policies">
	public static String arrest(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.INCREASE_ARREST_QUOTAS);
		if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getStats().getGovernment() < 5)
		{
			return Responses.noCriminals();
		}
		else if(nation.getStats().getApproval() < 5)
		{
			return Responses.hated();
		}
		else
		{
			nation.getStats().setStability(nation.getStats().getStability() + 5);
			nation.getStats().setApproval(nation.getStats().getApproval() - 5);
			nation.getStats().setGovernment(nation.getStats().getGovernment() - 5);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.arrest();
		}
	}

	public static String free(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.PARDON_CRIMINALS);
		if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getStats().getGovernment() > 95)
		{
			return Responses.noPrisoners();
		}
		else if(nation.getStats().getStability() < 5)
		{
			return Responses.unstable();
		}
		else
		{
			nation.getStats().setStability(nation.getStats().getStability() - 5);
			nation.getStats().setApproval(nation.getStats().getApproval() + 5);
			nation.getStats().setGovernment(nation.getStats().getGovernment() + 5);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.free();
		}
	}

	public static String landClearance(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.LAND_CLEARANCE);
		if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else
		{
			int gain = (int)(Math.random() * 2500) + 500;
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			nation.getStats().setLand(nation.getStats().getLand() + gain);
			return Responses.landClearance(gain);
		}
	}

	public static String propaganda(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.PROPAGANDA);
		if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getStats().getApproval() > 99)
		{
			return Responses.propagandaMaxApproval();
		}
		else
		{
			nation.getStats().setApproval(nation.getStats().getApproval() + 10);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
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
		else if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getStats().getApproval() > 99)
		{
			return Responses.propagandaMaxApproval();
		}
		else
		{
			nation.getStats().setApproval(nation.getStats().getApproval() + 10);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.propaganda();
		}
	}
	//</editor-fold>

	//<editor-fold desc="Economic Policies">
	public static String freeMoneyCapitalist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		economy.setBudget(economy.getBudget() + 1000);
		economy.setEconomic(economy.getEconomic() + 5);
		return Responses.freeMoneyCapitalist();
	}

	public static String freeMoneyCommunist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		economy.setBudget(economy.getBudget() + 1000);
		economy.setEconomic(economy.getEconomic() - 5);
		return Responses.freeMoneyCommunist();
	}
	//</editor-fold>

	//<editor-fold desc="Foreign Policies">
	private static String align(Nation nation, Alignments align) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.ALIGN_NEUTRAL);
		if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getStats().getAlignment() == align)
		{
			return Responses.alreadyYourAlignment();
		}
		else if(align != Alignments.NEUTRAL && nation.getStats().getReputation(align) < 1000)
		{
			return Responses.notEnough();
		}
		else if(Alignments.opposites(nation.getStats().getAlignment(), align))
		{
			return Responses.alreadyYourAlignment();
		}
		else
		{
			if(nation.getStats().getAlignment() == Alignments.NEUTRAL || Alignments.opposites(align, nation.getStats().getAlignment()))
			{
				Alignments opposite;
				if(nation.getStats().getAlignment() == Alignments.NEUTRAL)
				{
					opposite = align.opposite();
				}
				else
				{
					opposite = nation.getStats().getAlignment().opposite();
				}
				nation.getStats().setReputation(opposite, Math.min(0, nation.getStats().getReputation(opposite)));
			}
			nation.getStats().setAlignment(align);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.align(align);
		}
	}

	public static String alignEntente(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(nation, Alignments.ENTENTE);
	}

	public static String alignNeutral(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(nation, Alignments.NEUTRAL);
	}

	public static String alignCentralPowers(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(nation, Alignments.CENTRAL_POWERS);
	}
	//</editor-fold>

	//<editor-fold desc="Military Policies">
	public static String conscript(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{/*
		if(nation.getFreeManpower() < 2000)
		{
			return Responses.noManpower();
		}
		else
		{
			nation.getStats().setArmySize(nation.getStats().getArmySize() + 2);
			nation.getStats().setRecentConscription(nation.getStats().getRecentConscription() + 1);
			return Responses.conscript();
		}*/
		return null;
	}

	public static String deconscript(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{/*
		if(nation.getStats().getArmySize() <= 5)
		{
			return Responses.noTroops();
		}
		else
		{
			nation.getStats().setArmySize(nation.getStats().getArmySize() - 2);
			nation.getStats().setRecentDeconscription(nation.getStats().getRecentDeconscription() + 1);
			return Responses.deconscript();
		}*/
		return null;
	}

	public static String train(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{/*
		long cost = nation.getDecisionCost(Decision.TRAIN);
		if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getStats().getArmyTraining() >= 100)
		{
			return Responses.fullTrained();
		}
		else
		{
			nation.getStats().setArmyTraining(nation.getStats().getArmyTraining() + 5);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.train();
		}*/
		return null;
	}

	public static String fortify(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{/*
		long cost = nation.getDecisionCost(Decision.FORTIFY);
		if(nation.getStats().getSteel() < cost)
		{
			return Responses.noSteel();
		}
		else if(nation.getStats().getFortification() > 10000)
		{
			return Responses.alreadyFortified();
		}
		else
		{
			int increase = nation.getMaximumFortificationLevel() / 10;
			nation.getStats().setFortification(nation.getStats().getFortification() + increase);
			nation.getStats().setSteel(nation.getStats().getSteel() - cost);
			return Responses.fortified();
		}*/
		return null;
	}
	//</editor-fold>
}
