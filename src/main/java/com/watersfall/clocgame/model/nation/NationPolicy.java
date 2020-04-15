package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Policy;
import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationPolicy extends Updatable
{
	public static final String TABLE_NAME = "cloc_policy";

	private @Getter Policy manpower;
	private @Getter int changeManpower;
	private @Getter Policy food;
	private @Getter int changeFood;
	private @Getter Policy economy;
	private @Getter int changeEconomy;
	private @Getter Policy fortification;
	private @Getter int changeFortification;
	private @Getter Policy farmingSubsidies;
	private @Getter int changeFarmingSubsidies;

	public NationPolicy(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.manpower = Policy.valueOf(results.getString("manpower_policy"));
		this.changeManpower = results.getInt("manpower_change");
		this.food = Policy.valueOf(results.getString("food_policy"));
		this.changeFood = results.getInt("food_change");
		this.economy = Policy.valueOf(results.getString("economy_policy"));
		this.changeEconomy = results.getInt("economy_change");
		this.fortification = Policy.valueOf(results.getString("fortification_policy"));
		this.changeFortification = results.getInt("fortification_change");
		this.farmingSubsidies = Policy.valueOf(results.getString("farming_subsidies"));
		this.changeFarmingSubsidies = results.getInt("farming_subsidies_change");
	}

	public Policy getPolicy(Policy policy)
	{
		if(policy.name().contains("ECONOMY"))
			return this.economy;
		else if (policy.name().contains("FOOD"))
			return this.food;
		else if(policy.name().contains("FORTIFICATION"))
			return this.fortification;
		else if(policy.name().contains("SUBSIDIES_FARMING"))
			return this.farmingSubsidies;
		else
			return this.manpower;
	}

	public int getPolicyChange(Policy policy)
	{
		if(policy.name().contains("ECONOMY"))
			return this.changeEconomy;
		else if (policy.name().contains("FOOD"))
			return this.changeFood;
		else if(policy.name().contains("FORTIFICATION"))
			return this.changeFortification;
		else if(policy.name().contains("SUBSIDIES_FARMING"))
			return this.changeFarmingSubsidies;
		else
			return this.changeManpower;
	}

	public void setManpower(Policy manpower)
	{
		this.addField("manpower_policy", manpower);
		this.manpower = manpower;
	}

	public void setChangeManpower(int changeManpower)
	{
		this.addField("manpower_change", changeManpower);
		this.changeManpower = changeManpower;
	}

	public void setFood(Policy food)
	{
		this.addField("food_policy", food);
		this.food = food;
	}

	public void setChangeFood(int changeFood)
	{
		this.addField("food_change", changeFood);
		this.changeFood = changeFood;
	}

	public void setEconomy(Policy economy)
	{
		this.addField("economy_policy", economy);
		this.economy = economy;
	}

	public void setChangeEconomy(int changeEconomy)
	{
		this.addField("economy_change", changeEconomy);
		this.changeEconomy = changeEconomy;
	}

	public void setFortification(Policy fortification)
	{
		this.addField("fortification_policy", fortification);
		this.fortification = fortification;
	}

	public void setChangeFortification(int changeFortification)
	{
		this.addField("fortification_change", changeFortification);
		this.changeFortification = changeFortification;
	}

	public void setFarmingSubsidies(Policy farming)
	{
		this.addField("farming_subsidies", farming);
		this.farmingSubsidies = farming;
	}

	public void setChangeFarmingSubsidies(int changeFarmingSubsidies)
	{
		this.addField("farming_subsidies_change", changeFarmingSubsidies);
		this.changeFarmingSubsidies = changeFarmingSubsidies;
	}
}
