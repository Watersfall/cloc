package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;

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
			PreparedStatement ids = connection.prepareStatement("SELECT id FROM cloc_login");
			ResultSet results = ids.executeQuery();
			while(results.next())
			{
				int id = results.getInt(1);
				Nation nation = new Nation(connection, id, true);
				nation.getEconomy().setBudget(nation.getEconomy().getBudget() + nation.getBudgetChange());
				nation.getDomestic().setPopulation(nation.getDomestic().getPopulation()
						+ (long)(nation.getDomestic().getPopulation() * nation.getPopulationGrowth().get("total") / 7));
				nation.processProduction();
				nation.update();
			}

			connection.commit();

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
