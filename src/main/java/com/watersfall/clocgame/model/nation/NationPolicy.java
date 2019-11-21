package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.util.Util;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationPolicy extends NationBase
{
	private @Getter int id;
	private @Getter int manpower;
	private @Getter int changeManpower;
	private @Getter int food;
	private @Getter int changeFood;
	private @Getter int economy;
	private @Getter int changeEconomy;

	public NationPolicy(int id, int manpower, int changeManpower, int food, int changeFood, int economy, int changeEconomy)
	{
		this.id = id;
		this.manpower = manpower;
		this.changeManpower = changeManpower;
		this.food = food;
		this.changeFood = changeFood;
		this.economy = economy;
		this.changeEconomy = changeEconomy;
	}

	public NationPolicy(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.results = results;
		this.manpower = results.getInt("manpower_policy");
		this.changeManpower = results.getInt("manpower_change");
		this.food = results.getInt("food_policy");
		this.changeFood = results.getInt("food_change");
		this.economy = results.getInt("economy_policy");
		this.changeEconomy = results.getInt("economy_change");
	}

	public NationPolicy(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT manpower_policy, manpower_change, food_policy, food_change, economy_policy, economy_change, id " + "FROM cloc_policy " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT manpower_policy, manpower_change, food_policy, food_change, economy_policy, economy_change, id " + "FROM cloc_policy " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.connection = connection;
			this.id = id;
			this.safe = safe;
			this.manpower = results.getInt(1);
			this.changeManpower = results.getInt(2);
			this.food = results.getInt(3);
			this.changeFood = results.getInt(4);
			this.economy = results.getInt(5);
			this.changeEconomy = results.getInt(6);
		}
	}

	public void setManpower(int manpower) throws SQLException
	{
		if(manpower > 4 || manpower < 0)
		{
			manpower = 0;
		}
		this.manpower = manpower;
		this.changeManpower = Util.turn;
		results.updateInt("manpower_policy", manpower);
		results.updateInt("manpower_change", Util.turn);
	}

	public void setFood(int food) throws SQLException
	{
		if(food > 2 || food < 0)
		{
			food = 0;
		}
		this.food = food;
		this.changeFood = Util.turn;
		results.updateInt("food_policy", food);
		results.updateInt("food_change", Util.turn);
	}

	public void setEconomy(int economy) throws SQLException
	{
		if(economy > 4 || economy < 0)
		{
			economy = 0;
		}
		this.economy = economy;
		this.changeEconomy = Util.turn;
		results.updateInt("economy_policy", economy);
		results.updateInt("economy_change", Util.turn);
	}
}
