package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.TextKey;
import com.watersfall.clocgame.model.city.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Time;

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
			NationDao dao = new NationDao(connection, true);
			connection.prepareStatement("UPDATE main SET day=day+1").execute();
			PreparedStatement ids = connection.prepareStatement("SELECT id FROM login");
			ResultSet results = ids.executeQuery();
			while(results.next())
			{
				int id = results.getInt(1);
				Nation nation = dao.getNationById(id);
				try
				{
					nation.getStats().setBudget(nation.getStats().getBudget() + nation.getBudgetChange());
					for(Integer cityId : nation.getCities().keySet())
					{
						City city = nation.getCities().get(cityId);
						city.setPopulation(city.getPopulation()
								+ (long)(city.getPopulation() * (city.getPopulationGrowth(nation).get(TextKey.Population.NET) / (100L * Time.daysPerMonth[Time.currentMonth]))));
					}
					nation.processProduction();
					dao.saveNation(nation);
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					PreparedStatement statement = connection.prepareStatement("INSERT INTO news (sender, receiver, content) VALUES (1, ?, ?)");
					statement.setInt(1, nation.getId());
					statement.setString(2, e.getLocalizedMessage());
					statement.execute();
				}
			}
			PreparedStatement updateEfficiency = connection.prepareStatement("UPDATE factories " +
					"SET efficiency=LEAST(efficiency+(0.1 * (10000.0 / (efficiency / 100.0))), 10000) WHERE production_id IS NOT NULL");
			updateEfficiency.execute();
			connection.commit();
			Time.day++;
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
