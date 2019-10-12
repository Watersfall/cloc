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
	/**
	 * Method to check if a nation has the required costs for researching a technology
	 * @param nation The nation to check
	 * @param tech The tech to check
	 * @return A displayable response of what resource is lacking, or null if the nation has all required resources
	 */
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

	/**
	 * Method to run the costs of researching a tech
	 * This method runs assuming the costs have already been checked and that nothing will go negative
	 * @param nation The nation to update
	 * @param tech The tech to research
	 * @return A displayable message if an exception occurs, or null if everything just works
	 */
	private static String doCosts(Nation nation, Technology tech)
	{
		try
		{
			for(HashMap.Entry<String, Integer> entry : tech.getCosts().entrySet())
			{
				String getName = "get" + entry.getKey().substring(0, 1).toUpperCase().concat(entry.getKey().substring(1));
				String setName = "set" + entry.getKey().substring(0, 1).toUpperCase().concat(entry.getKey().substring(1));
				Method getMethod = nation.getEconomy().getClass().getMethod(getName);
				Method setMethod = nation.getEconomy().getClass().getMethod(setName, double.class);
				double current = (double)getMethod.invoke(nation.getEconomy());
				setMethod.invoke(nation.getEconomy(), current - entry.getValue());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Responses.genericException(e);
		}
		return null;
	}

	/**
	 * Action to research a technology
	 * @param nation The nation to update
	 * @param tech The Technologies Enum constant representing the tech to research
	 * @return A displayable message for the results of the research attempt
	 * @throws SQLException if a database issue happens
	 */
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
				nation.getTech().setTechnology(tech, nation.getTech().getTechnology(tech) + 1);
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
