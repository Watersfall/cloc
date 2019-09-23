package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;

public class ResearchActions
{
	private static String checkCosts(Nation nation, Technology tech)
	{
		try
		{
			for(HashMap.Entry<String, Integer> entry : tech.getCosts().entrySet())
			{
				String methodName = "get" + entry.getKey().substring(0, 1).toUpperCase().concat(entry.getKey().substring(1));
				Method method = nation.getEconomy().getClass().getMethod(methodName);
				Double amount = (double)method.invoke(nation.getEconomy());
				if(amount < entry.getValue())
				{
					return Responses.no(entry.getKey());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Responses.genericException(e);
		}
		return null;
	}

	private static String doCosts(Nation nation, Technology tech)
	{
		try
		{
			for(HashMap.Entry<String, Integer> entry : tech.getCosts().entrySet())
			{
				String methodName = "set" + entry.getKey().substring(0, 1).toUpperCase().concat(entry.getKey().substring(1));
				Method method = nation.getEconomy().getClass().getMethod(methodName, double.class);
				method.invoke(nation.getEconomy(), entry.getValue());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Responses.genericException(e);
		}
		return null;
	}

	public static String doResearch(Nation nation, Technologies tech) throws SQLException
	{
		if(!tech.getTechnology().isAvailable(nation))
		{
			return Responses.missingPrerequisite();
		}
		String costs = checkCosts(nation, tech.getTechnology());
		if(costs != null)
		{
			return costs;
		}
		else if(nation.getTech().getResearchedTechs().contains(tech))
		{
			return Responses.alreadyHaveTech();
		}
		else
		{
			doCosts(nation, tech.getTechnology());
			String response;
			if((int)(Math.random() * 100) <= tech.getTechnology().getSuccessChance(nation))
			{
				nation.getTech().setTech(tech);
				response = Responses.researchSucceeded();
			}
			else
			{
				response = Responses.researchFailed();
			}
			nation.getTech().update();
			nation.getEconomy().update();
			return response;
		}
	}
}
