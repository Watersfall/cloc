package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.Policy;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.*;
import java.util.HashMap;

public @Data @AllArgsConstructor class Production
{
	private final int id;
	private final int owner;
	private HashMap<Integer, Factory> factories;
	private String production;
	private int progress;
	
	public static Production getProductionById(int id)
	{
		Connection conn = null;
		Production production = null;
		try
		{
			conn = Database.getDataSource().getConnection();
			PreparedStatement getProduction = conn.prepareStatement("SELECT * FROM production WHERE id=?");
			PreparedStatement getFactories = conn.prepareStatement("SELECT * FROM factories WHERE production_id=?");
			getProduction.setInt(1, id);
			getFactories.setInt(1, id);
			ResultSet resultsFactories = getFactories.executeQuery();
			HashMap<Integer, Factory> map = new HashMap<>();
			while(resultsFactories.next())
			{
				map.put(resultsFactories.getInt("id"), new Factory(resultsFactories.getInt("id"), resultsFactories));
			}
			ResultSet results = getProduction.executeQuery();
			if(results.first())
			{
				production = new Production(
						results.getInt("id"), 
						results.getInt("owner"),
						map,
						results.getString("production"),
						results.getInt("progress"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try 
			{
				conn.rollback();
			} 
			catch (Exception e2) 
			{
				//Ignored
				e.printStackTrace();
			}
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{
				//Ignored
				e.printStackTrace();
			}
		}
		return production;
	}

	public static void deleteProductionById(int id, Connection conn) throws SQLException
	{
		PreparedStatement efficiency = conn.prepareStatement("UPDATE factories SET efficiency=1500 WHERE production_id=?");
		efficiency.setInt(1, id);
		efficiency.execute();
		PreparedStatement delete = conn.prepareStatement("DELETE FROM production WHERE id=?");
		delete.setInt(1, id);
		delete.execute();
	}

	public static void createDefaultProduction(int owner, Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("INSERT INTO production (owner, production, progress) VALUES (?, 'MUSKET', 0)", Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, owner);
		statement.execute();
		ResultSet key = statement.getGeneratedKeys();
		key.first();
		PreparedStatement factory = conn.prepareStatement("UPDATE factories SET production_id=? WHERE production_id IS NULL LIMIT 1");
		factory.setInt(1, key.getInt(1));
		factory.execute();
	}
	
	public void update() throws SQLException
	{
		Connection conn = Database.getDataSource().getConnection();
		update(conn);
		conn.commit();
		conn.close();
	}

	public void update(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("UPDATE production SET production=?, progress=? WHERE id=?");
		statement.setString(1, this.production);
		statement.setInt(2, this.progress);
		statement.setInt(3, this.id);
		statement.execute();
	}

	public int getEfficiency()
	{
		int total = 0;
		for(Factory factory : factories.values())
		{
			total += factory.getEfficiency();
		}
		return total / factories.size();
	}

	public double getIc(Policy policy)
	{
		int total = 0;
		for(Factory factory : factories.values())
		{
			total += factory.getEfficiency();
		}
		if(policy == Policy.WAR_ECONOMY)
		{
			total *= 1.25;
		}
		return total / 10000.0;
	}

	public void addFactories(int amount, Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("UPDATE factories SET production_id=?, efficiency=1500 " +
				"WHERE production_id IS NULL LIMIT ?");
		statement.setInt(1, this.id);
		statement.setInt(2, amount);
		statement.execute();
	}

	public void removeFactories(int amount, Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("UPDATE factories SET production_id = NULL, efficiency=1500 " +
					"WHERE production_id=? ORDER BY efficiency LIMIT ?");
		statement.setInt(1, this.id);
		statement.setInt(2, amount);
		statement.execute();
	}

	public String getProductionString(Policy policy)
	{
		double speed = this.getIc(policy) / getProductionAsTechnology().getTechnology().getProductionCost();
		if(speed >= 1)
		{
			return String.format("%.2f&nbsp;per&nbsp;day", speed);
		}
		else if((speed = speed * 7) >= 1)
		{
			return String.format("%.2f&nbsp;per&nbsp;week", speed);
		}
		else
		{
			double timeRemaining = (getProductionAsTechnology().getTechnology().getProductionCost() - (this.progress / 100.0)) / this.getIc(policy);
			if(timeRemaining > 7)
			{
				timeRemaining = timeRemaining / 7;
				return String.format("Completed&nbsp;in&nbsp;%.2f&nbsp;weeks", timeRemaining);
			}
			else
			{
				return String.format("Completed&nbsp;in&nbsp;%.2f&nbsp;days", timeRemaining);
			}
		}
	}

	public Technologies getProductionAsTechnology()
	{
		return Technologies.valueOf(this.production);
	}
}
