package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationPolicy extends Updatable
{
	public static final String TABLE_NAME = "cloc_policy";
	private @Getter int manpower;
	private @Getter int changeManpower;
	private @Getter int food;
	private @Getter int changeFood;
	private @Getter int economy;
	private @Getter int changeEconomy;

	public NationPolicy(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.manpower = results.getInt("manpower_policy");
		this.changeManpower = results.getInt("manpower_change");
		this.food = results.getInt("food_policy");
		this.changeFood = results.getInt("food_change");
		this.economy = results.getInt("economy_policy");
		this.changeEconomy = results.getInt("economy_change");
	}

	public void setManpower(int manpower)
	{
		this.addField("manpower_policy", manpower);
		this.manpower = manpower;
	}

	public void setChangeManpower(int changeManpower)
	{
		this.addField("manpower_change", changeManpower);
		this.changeManpower = changeManpower;
	}

	public void setFood(int food)
	{
		this.addField("food_policy", food);
		this.food = food;
	}

	public void setChangeFood(int changeFood)
	{
		this.addField("food_change", changeFood);
		this.changeFood = changeFood;
	}

	public void setEconomy(int economy)
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
