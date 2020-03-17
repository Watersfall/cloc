package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDay implements Runnable
{
	@Override
	public void run()
	{
		Connection connection = null;
		System.out.println("Running budget tick");
		try
		{
			connection = Database.getDataSource().getConnection();
			connection.prepareStatement("UPDATE cloc_main SET day=day+1").execute();
			PreparedStatement ids = connection.prepareStatement("SELECT id FROM cloc_login");
			ResultSet results = ids.executeQuery();
			while(results.next())
			{
				int id = results.getInt(1);
				Nation nation = new Nation(connection, id, true);
				try
				{
					nation.getEconomy().setBudget(nation.getEconomy().getBudget() + nation.getBudgetChange());
					nation.getDomestic().setPopulation(nation.getDomestic().getPopulation()
							+ (long)(nation.getDomestic().getPopulation() * (nation.getPopulationGrowth().get("population.net") / (4 * 7))));
					nation.processProduction();
					nation.update();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					PreparedStatement statement = connection.prepareStatement("INSERT INTO cloc_news (sender, receiver, content, image) VALUES (1, ?, ?, '')");
					statement.setInt(1, nation.getId());
					statement.setString(2, e.getLocalizedMessage());
					statement.execute();
				}
			}
			PreparedStatement updateEfficiency = connection.prepareStatement("UPDATE factories " +
					"SET efficiency=LEAST(efficiency+(0.1 * (10000.0 / (efficiency / 100.0))), 10000) WHERE production_id IS NOT NULL");
			updateEfficiency.execute();
			connection.commit();
			Util.day++;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try
			{
				connection.rollback();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
	}
}
