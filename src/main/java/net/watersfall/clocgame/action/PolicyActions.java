package net.watersfall.clocgame.action;

import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.policies.Policy;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Time;
import org.json.JSONObject;

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
		JSONObject object = new JSONObject();
		if(nation.getPolicy().getEconomy() == policy)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policySame());
		}
		else if(nation.getPolicy().getChangeEconomy() + 1 > Time.month)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noChange());
		}
		else if(policy == Policy.WAR_ECONOMY && !nation.isAtWar())
		{
			return Responses.policyNoWar();
		}
		else
		{
			nation.getPolicy().setEconomy(policy);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policyUpdated());
		}
		return object.toString();
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
		JSONObject object = new JSONObject();
		if(nation.getPolicy().getManpower() == policy)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policySame());
		}
		else if(nation.getPolicy().getChangeManpower() + 1 > Time.month)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noChange());
		}
		else if(policy == Policy.SCRAPING_THE_BARREL_MANPOWER && !nation.isAtWar())
		{
			return Responses.policyNoWar();
		}
		else
		{
			nation.getPolicy().setManpower(policy);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policyUpdated());
		}
		return object.toString();
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
		JSONObject object = new JSONObject();
		if(nation.getPolicy().getFood() == policy)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policySame());
		}
		else if(nation.getPolicy().getChangeFood() + 1 > Time.month)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noChange());
		}
		else
		{
			nation.getPolicy().setFood(policy);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policyUpdated());
		}
		return object.toString();
	}

	public static String fortification(Nation nation, Policy policy) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(nation.getPolicy().getFortification() == policy)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policySame());
		}
		else if(nation.getPolicy().getChangeFortification() + 1 > Time.month)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noChange());
		}
		else
		{
			nation.getPolicy().setFortification(policy);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policyUpdated());
		}
		return object.toString();
	}

	public static String farmSubsidization(Nation nation, Policy policy) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(nation.getPolicy().getFarmingSubsidies() == policy)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policySame());
		}
		else if(nation.getPolicy().getChangeFarmingSubsidies() + 1 > Time.month)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noChange());
		}
		else
		{
			nation.getPolicy().setFarmingSubsidies(policy);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.policyUpdated());
		}
		return object.toString();
	}
}
