package net.watersfall.clocgame.action;

import net.watersfall.clocgame.exception.CityNotFoundException;
import net.watersfall.clocgame.exception.NationNotFoundException;
import net.watersfall.clocgame.exception.NotLoggedInException;
import net.watersfall.clocgame.model.alignment.Alignments;
import net.watersfall.clocgame.model.decisions.Decision;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.nation.NationStats;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Util;
import org.json.JSONObject;

import java.sql.SQLException;

public class DecisionActions
{
	public DecisionActions(){}

	//<editor-fold desc="Domestic Policies">
	public static String arrest(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.INCREASE_ARREST_QUOTAS);
		JSONObject object = new JSONObject();
		if(nation.getStats().getBudget() < cost)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getGovernment() < 5)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noCriminals());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getApproval() < 5)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.hated());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else
		{
			nation.getStats().setStability(nation.getStats().getStability() + 5);
			nation.getStats().setApproval(nation.getStats().getApproval() - 5);
			nation.getStats().setGovernment(nation.getStats().getGovernment() - 5);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
			object.put(JsonFields.MESSAGE.name(), Responses.arrest());
			object.put(JsonFields.SUCCESS.name(), true);
		}
		return object.toString();
	}

	public static String free(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.PARDON_CRIMINALS);
		JSONObject object = new JSONObject();
		if(nation.getStats().getBudget() < cost)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getGovernment() > 95)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noPrisoners());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getStability() < 5)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.unstable());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else
		{
			nation.getStats().setStability(nation.getStats().getStability() - 5);
			nation.getStats().setApproval(nation.getStats().getApproval() + 5);
			nation.getStats().setGovernment(nation.getStats().getGovernment() + 5);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
			object.put(JsonFields.MESSAGE.name(), Responses.free());
			object.put(JsonFields.SUCCESS.name(), true);
		}
		return object.toString();
	}

	public static String landClearance(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.LAND_CLEARANCE);
		JSONObject object = new JSONObject();
		if(nation.getStats().getBudget() < cost)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else
		{
			int gain = (int)(Math.random() * 2500) + 500;
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			nation.getStats().setLand(nation.getStats().getLand() + gain);
			object.put(JsonFields.DECISION_COST.name() + "_" + Decision.LAND_CLEARANCE.name(), nation.getDecisionCostDisplayString(Decision.LAND_CLEARANCE));
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
			object.put(JsonFields.MESSAGE.name(), Responses.landClearance(gain));
			object.put(JsonFields.SUCCESS.name(), true);
		}
		return object.toString();
	}

	public static String propaganda(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.PROPAGANDA);
		JSONObject object = new JSONObject();
		if(nation.getStats().getBudget() < cost)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getApproval() > 99)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.propagandaMaxApproval());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else
		{
			nation.getStats().setApproval(nation.getStats().getApproval() + 10);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			object.put(JsonFields.DECISION_COST.name() + "_" + Decision.PROPAGANDA.name(), nation.getDecisionCostDisplayString(Decision.PROPAGANDA));
			object.put(JsonFields.DECISION_COST.name() + "_" + Decision.WAR_PROPAGANDA.name(), nation.getDecisionCostDisplayString(Decision.WAR_PROPAGANDA));
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
			object.put(JsonFields.MESSAGE.name(), Responses.propaganda());
			object.put(JsonFields.SUCCESS.name(), true);
		}
		return object.toString();
	}

	public static String warPropaganda(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.WAR_PROPAGANDA);
		JSONObject object = new JSONObject();
		if(nation.getOffensive() == null && nation.getDefensive() == null)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noWar());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getBudget() < cost)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getApproval() > 99)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.propagandaMaxApproval());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else
		{
			nation.getStats().setApproval(nation.getStats().getApproval() + 10);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			object.put(JsonFields.DECISION_COST.name() + "_" + Decision.WAR_PROPAGANDA.name(), nation.getDecisionCostDisplayString(Decision.WAR_PROPAGANDA));
			object.put(JsonFields.DECISION_COST.name() + "_" + Decision.PROPAGANDA.name(), nation.getDecisionCostDisplayString(Decision.PROPAGANDA));
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
			object.put(JsonFields.MESSAGE.name(), Responses.propaganda());
			object.put(JsonFields.SUCCESS.name(), true);
		}
		return object.toString();
	}
	//</editor-fold>

	//<editor-fold desc="Economic Policies">
	public static String freeMoneyCapitalist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		economy.setBudget(economy.getBudget() + 1000);
		economy.setEconomic(economy.getEconomic() + 5);
		JSONObject object = new JSONObject();
		object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(economy.getBudget()));
		object.put(JsonFields.BUDGET.name(), Util.formatNumber(economy.getBudget()));
		object.put(JsonFields.MESSAGE.name(), Responses.freeMoneyCapitalist());
		object.put(JsonFields.SUCCESS.name(), true);
		return object.toString();
	}

	public static String freeMoneyCommunist(Nation nation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		economy.setBudget(economy.getBudget() + 1000);
		economy.setEconomic(economy.getEconomic() - 5);
		JSONObject object = new JSONObject();
		object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(economy.getBudget()));
		object.put(JsonFields.BUDGET.name(), Util.formatNumber(economy.getBudget()));
		object.put(JsonFields.MESSAGE.name(), Responses.freeMoneyCommunist());
		object.put(JsonFields.SUCCESS.name(), true);
		return object.toString();
	}
	//</editor-fold>

	//<editor-fold desc="Foreign Policies">
	private static String align(Nation nation, Alignments align) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		long cost = nation.getDecisionCost(Decision.ALIGN_NEUTRAL);
		JSONObject object = new JSONObject();
		if(nation.getStats().getBudget() < cost)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(nation.getStats().getAlignment() == align)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.alreadyYourAlignment());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(align != Alignments.NEUTRAL && nation.getStats().getReputation(align) < 1000)
		{
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
			object.put(JsonFields.SUCCESS.name(), false);
		}
		else if(Alignments.opposites(nation.getStats().getAlignment(), align))
		{
			object.put(JsonFields.MESSAGE.name(), Responses.alreadyYourAlignment());
			object.put(JsonFields.SUCCESS.name(), false);
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
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
			object.put(JsonFields.MESSAGE.name(), Responses.align(align));
			object.put(JsonFields.SUCCESS.name(), true);
		}
		return object.toString();
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
