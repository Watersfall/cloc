package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Policy;
import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationPolicy extends Updatable
{
	public static final String TABLE_NAME = "cloc_policy";

	/**
	 * Economy Types
	 */
	public static final int CIVILIAN_ECONOMY = 0;
	public static final int EXTRACTION_ECONOMY = 1;
	public static final int INDUSTRY_ECONOMY = 2;
	public static final int AGRARIAN_ECONOMY = 3;
	public static final int WAR_ECONOMY = 4;
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

	private @Getter Policy manpower;
	private @Getter int changeManpower;
	private @Getter Policy food;
	private @Getter int changeFood;
	private @Getter Policy economy;
	private @Getter int changeEconomy;

	public NationPolicy(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.manpower = Policy.valueOf(results.getString("manpower_policy"));
		this.changeManpower = results.getInt("manpower_change");
		this.food = Policy.valueOf(results.getString("food_policy"));
		this.changeFood = results.getInt("food_change");
		this.economy = Policy.valueOf(results.getString("economy_policy"));
		this.changeEconomy = results.getInt("economy_change");
	}

	public Policy getPolicy(Policy policy)
	{
		if(policy.name().contains("ECONOMY"))
			return this.economy;
		else if (policy.name().contains("FOOD"))
			return this.food;
		else
			return this.manpower;
	}

	public int getPolicyChange(Policy policy)
	{
		if(policy.name().contains("ECONOMY"))
			return this.changeEconomy;
		else if (policy.name().contains("FOOD"))
			return this.changeFood;
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
}
