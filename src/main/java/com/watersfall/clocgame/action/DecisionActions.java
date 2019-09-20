package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class DecisionActions
{
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
