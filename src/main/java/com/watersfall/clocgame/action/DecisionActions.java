package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class DecisionActions
{
	/**
	 * Economy Types
	 */
	public static final int SOCIALIST_ECONOMY = 0;
	public static final int CIVILIAN_ECONOMY = 1;
	public static final int STANDARD_ECONOMY = 2;
	public static final int MILITARY_ECONOMY = 3;
	public static final int TOTAL_WAR_ECONOMY = 4;

	/**
	 * Conscription Types
	 */
	public static final int DISARMED_MANPOWER = 0;
	public static final int VOLUNTEER_MANPOWER = 1;
	public static final int RECRUITMENT_MANPOWER = 2;
	public static final int MANDATORY_MANPOWER = 3;
	public static final int SCRAPING_THE_BARREL_MANPOWER = 4;

	/**
	 * Food Types
	 */
	public static final int RATIONING_FOOD = 0;
	public static final int NORMAL_FOOD = 1;
	public static final int FREE_FOOD = 2;

	/**
	 * Updates a nation's economy policy
	 * @param connection The SQL Connection to use
	 * @param user The nation's ID
	 * @param value The ID of the policy to switch to, matching the economy types
	 *              with a maximum of 4 (Total War Economy) and a minimum of 0 (Socialist Economy)
	 * @return The message to be displayed
	 * @throws SQLException If a database issue occurs
	 */
	public static String economy(Connection connection, int user, int value) throws SQLException
	{
		if(value > 4 || value < 0)
		{
			return Responses.genericError();
		}
		else
		{
			Nation nation = new Nation(connection, user, true);
			if(nation.getPolicy().getEconomy() == value)
			{
				return Responses.policySame();
			}
			else if(nation.getPolicy().getChangeEconomy() + 1 > Util.turn)
			{
				return Responses.noChange();
			}
			else if(value == 4 && nation.getOffensive() == 0 && nation.getDefensive() == 0)
			{
				return Responses.policyNoWar();
			}
			else
			{
				nation.getPolicy().setEconomy(value);
				nation.getPolicy().update();
				return Responses.policyUpdated();
			}
		}
	}

	/**
	 * Updates a nation's manpower policy
	 * @param connection The SQL Connection to use
	 * @param user The nation's id
	 * @param value The ID of the policy to switch to, matching the manpower constants
	 *              Maximum of 4, minimum of 0
	 * @return The response message to be displayed
	 * @throws SQLException If a database issue happens
	 */
	public static String manpower(Connection connection, int user, int value) throws SQLException
	{
		if(value > 4 || value < 0)
		{
			return Responses.genericError();
		}
		else
		{
			Nation nation = new Nation(connection, user, true);
			if(nation.getPolicy().getManpower() == value)
			{
				return Responses.policySame();
			}
			else if(nation.getPolicy().getChangeManpower() + 1 > Util.turn)
			{
				return Responses.noChange();
			}
			else if(value == 4 && nation.getOffensive() == 0 && nation.getDefensive() == 0)
			{
				return Responses.policyNoWar();
			}
			else
			{
				nation.getPolicy().setManpower(value);
				nation.getPolicy().update();
				return Responses.policyUpdated();
			}
		}
	}

	/**
	 * Updates a nation's food policy
	 * @param connection The SQL Connection to use
	 * @param user The nation's id
	 * @param value The ID of the policy to switch to, matching the food constants
	 *              Maximum of 2, minimum of 0
	 * @return The response message to be displayed
	 * @throws SQLException If a database issue happens
	 */
	public static String food(Connection connection, int user, int value) throws SQLException
	{
		if(value > 2 || value < 0)
		{
			return Responses.genericError();
		}
		else
		{
			Nation nation = new Nation(connection, user, true);
			if(nation.getPolicy().getFood() == value)
			{
				return Responses.policySame();
			}
			else if(nation.getPolicy().getChangeFood() + 1 > Util.turn)
			{
				return Responses.noChange();
			}
			else
			{
				nation.getPolicy().setFood(value);
				nation.getPolicy().update();
				return Responses.policyUpdated();
			}
		}
	}
}
