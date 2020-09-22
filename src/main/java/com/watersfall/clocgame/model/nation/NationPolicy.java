package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.UpdatableIntId;
import com.watersfall.clocgame.model.policies.Policy;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationPolicy extends UpdatableIntId
{
	public static final String TABLE_NAME = "nation_policy";

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
		super(TABLE_NAME, id);
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
		switch(policy.getCategory())
		{
			case FOOD:
				return this.food;
			case ECONOMY:
				return this.economy;
			case MANPOWER:
				return this.manpower;
			case FORTIFICATION:
				return this.fortification;
			case FARM_SUBSIDIZATION:
				return this.farmingSubsidies;
			default:
				return null;
		}
	}

	public int getPolicyChange(Policy policy)
	{
		switch(policy.getCategory())
		{
			case FOOD:
				return this.changeFood;
			case ECONOMY:
				return this.changeEconomy;
			case MANPOWER:
				return this.changeManpower;
			case FORTIFICATION:
				return this.changeFortification;
			case FARM_SUBSIDIZATION:
				return this.changeFarmingSubsidies;
			default:
				return 0;
		}
	}

	public void setManpower(Policy manpower)
	{
		this.setField("manpower_policy", manpower);
		this.manpower = manpower;
	}

	public void setChangeManpower(int changeManpower)
	{
		this.setField("manpower_change", changeManpower);
		this.changeManpower = changeManpower;
	}

	public void setFood(Policy food)
	{
		this.setField("food_policy", food);
		this.food = food;
	}

	public void setChangeFood(int changeFood)
	{
		this.setField("food_change", changeFood);
		this.changeFood = changeFood;
	}

	public void setEconomy(Policy economy)
	{
		this.setField("economy_policy", economy);
		this.economy = economy;
	}

	public void setChangeEconomy(int changeEconomy)
	{
		this.setField("economy_change", changeEconomy);
		this.changeEconomy = changeEconomy;
	}

	public void setFortification(Policy fortification)
	{
		this.setField("fortification_policy", fortification);
		this.fortification = fortification;
	}

	public void setChangeFortification(int changeFortification)
	{
		this.setField("fortification_change", changeFortification);
		this.changeFortification = changeFortification;
	}

	public void setFarmingSubsidies(Policy farming)
	{
		this.setField("farming_subsidies", farming);
		this.farmingSubsidies = farming;
	}

	public void setChangeFarmingSubsidies(int changeFarmingSubsidies)
	{
		this.setField("farming_subsidies_change", changeFarmingSubsidies);
		this.changeFarmingSubsidies = changeFarmingSubsidies;
	}
}
