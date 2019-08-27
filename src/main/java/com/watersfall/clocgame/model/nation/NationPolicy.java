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
	private @Getter
	int id;
	private @Getter
	int manpower;
	private @Getter
	int changeManpower;
	private @Getter
	int food;
	private @Getter
	int changeFood;
	private @Getter
	int economy;
	private @Getter
	int changeEconomy;

	public NationPolicy(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT manpower, manpower_change, food, food_change, economy, economy_change, id " + "FROM cloc_policy " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT manpower, manpower_change, food, food_change, economy, economy_change, id " + "FROM cloc_policy " + "WHERE id=?");
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
		results.updateInt(1, manpower);
		results.updateInt(2, Util.getTurn());
	}

	public void setFood(int food) throws SQLException
	{
		results.updateInt(3, food);
		results.updateInt(4, Util.getTurn());
	}

	public void setEconomy(int economy) throws SQLException
	{
		results.updateInt(5, economy);
		results.updateInt(6, Util.getTurn());
	}
}
