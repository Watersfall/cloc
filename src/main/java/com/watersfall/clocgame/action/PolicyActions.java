package com.watersfall.clocgame.action;

import com.watersfall.clocgame.model.Policy;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Util;

import java.sql.SQLException;

public class PolicyActions
{
	/**
	 * Updates a nation's economy policy
	 * @param nation The nation
	 * @param policy The policy to be set
	 * @return The message to be displayed
	 * @throws SQLException If a database issue occurs
	 */
	public static String economy(Nation nation, Policy policy) throws SQLException
	{
		if(nation.getPolicy().getEconomy() == policy)
		{
			return Responses.policySame();
		}
		else if(nation.getPolicy().getChangeEconomy() + 1 > Util.month)
		{
			return Responses.noChange();
		}
		else if(policy == Policy.WAR_ECONOMY && !nation.isAtWar())
		{
			return Responses.policyNoWar();
		}
		else
		{
			nation.getPolicy().setEconomy(policy);
			nation.update();
			return Responses.policyUpdated();
		}
	}

	/**
	 * Updates a nation's manpower policy
	 * @param nation The nation
	 * @param policy The policy to be set
	 * @return The response message to be displayed
	 * @throws SQLException If a database issue happens
	 */
	public static String manpower(Nation nation, Policy policy) throws SQLException
	{
		if(nation.getPolicy().getManpower() == policy)
		{
			return Responses.policySame();
		}
		else if(nation.getPolicy().getChangeManpower() + 1 > Util.month)
		{
			return Responses.noChange();
		}
		else if(policy == Policy.SCRAPING_THE_BARREL_MANPOWER && !nation.isAtWar())
		{
			return Responses.policyNoWar();
		}
		else
		{
			nation.getPolicy().setManpower(policy);
			nation.update();
			return Responses.policyUpdated();
		}
	}

	/**
	 * Updates a nation's food policy
	 * @param nation The nation
	 * @param policy The policy to be set
	 * @return The response message to be displayed
	 * @throws SQLException If a database issue happens
	 */
	public static String food(Nation nation, Policy policy) throws SQLException
	{
		if(nation.getPolicy().getFood() == policy)
		{
			return Responses.policySame();
		}
		else if(nation.getPolicy().getChangeFood() + 1 > Util.month)
		{
			return Responses.noChange();
		}
		else
		{
			nation.getPolicy().setFood(policy);
			nation.update();
			return Responses.policyUpdated();
		}
	}
}
