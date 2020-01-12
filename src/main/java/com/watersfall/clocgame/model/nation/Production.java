package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public @Data @AllArgsConstructor class Production
{
	private final int id;
	private final int owner;
	private int factories;
	private int efficiency;
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
			getProduction.setInt(1, id);
			ResultSet results = getProduction.executeQuery();
			if(results.first())
			{
				production = new Production(
						results.getInt("id"), 
						results.getInt("owner"), 
						results.getInt("factories"), 
						results.getInt("efficiency"),
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

	public static void deleteProductionById(int id) throws SQLException
	{
		Connection conn = Database.getDataSource().getConnection();
		deleteProductionById(id, conn);
		conn.commit();
		conn.close();
	}

	public static void deleteProductionById(int id, Connection conn) throws SQLException
	{
		PreparedStatement delete = conn.prepareStatement("DELETE FROM production WHERE id=?");
		delete.setInt(1, id);
		delete.execute();
	}

	public static void createDefaultProduction(int owner, Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("INSERT INTO production (owner, factories, efficiency, production, progress) VALUES (?, 1, 2500, 'MUSKET', 0)");
		statement.setInt(1, owner);
		statement.execute();
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
		PreparedStatement statement = conn.prepareStatement("UPDATE production SET factories=?, efficiency=?, production=?, progress=? WHERE id=?");
		statement.setInt(1, this.factories);
		statement.setInt(2, this.efficiency);
		statement.setString(3, this.production);
		statement.setInt(4, this.progress);
		statement.setInt(5, this.id);
		statement.execute();
	}

	public double getIc()
	{
		return (this.efficiency / 10000.0) * this.factories;
	}

	public String getProductionString()
	{
		double speed = this.getIc() / getProductionAsTechnology().getTechnology().getProductionCost();
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
			double timeRemaining = (getProductionAsTechnology().getTechnology().getProductionCost() - (this.progress / 100.0)) / this.getIc();
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
