package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.Policy;
import com.watersfall.clocgame.model.Stats;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationDomestic;
import com.watersfall.clocgame.model.nation.NationEconomy;
import com.watersfall.clocgame.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TurnWeek implements Runnable
{
	@Override
	public void run()
	{
		Connection connection = null;
		System.out.println("Running turn");
		try
		{
			connection = Database.getDataSource().getConnection();
			PreparedStatement ids = connection.prepareStatement("SELECT id FROM cloc_login");
			connection.prepareStatement("UPDATE cloc_main SET week=week+1").execute();
			ResultSet results = ids.executeQuery();
			while(results.next())
			{
				int id = results.getInt(1);
				Nation nation = new Nation(connection, id, true);
				try
				{
					NationEconomy economy = nation.getEconomy();
					NationDomestic domestic = nation.getDomestic();
					/*
					 ** Economy
					 */

					// Production
					HashMap<String, Double> coal = nation.getTotalCoalProduction();
					HashMap<String, Double> iron = nation.getTotalIronProduction();
					HashMap<String, Double> oil = nation.getTotalOilProduction();
					HashMap<String, Double> steel = nation.getTotalSteelProduction();
					HashMap<String, Double> nitrogen = nation.getTotalNitrogenProduction();
					HashMap<String, Double> research = nation.getTotalResearchProduction();
					HashMap<String, Double> food = nation.getFoodProduction();
					economy.setCoal(economy.getCoal() + coal.get("resource.total"));
					economy.setIron(economy.getIron() + iron.get("resource.total"));
					economy.setOil(economy.getOil() + oil.get("resource.total"));
					economy.setResearch(economy.getResearch() + research.get("resource.net"));
					economy.setFood(economy.getFood() + food.get("resource.net"));
					economy.setGrowth(economy.getGrowth() + nation.getGrowthChange().get("growth.net"));
					economy.setGdp(economy.getGdp() + economy.getGrowth());

					if(economy.getCoal() > coal.get("resource.factoryUpkeep")
							&& economy.getIron() > iron.get("resource.factoryUpkeep")
							&& economy.getOil() > oil.get("resource.factoryUpkeep"))
					{
						economy.setSteel(economy.getSteel() + steel.get("resource.net"));
						economy.setNitrogen(economy.getNitrogen() + nitrogen.get("resource.net"));
					}

					/*
					 ** Domestic
					 */
					domestic.setPopulation(domestic.getPopulation() + (nation.getPopulationGrowth().get("population.net")).longValue());
					domestic.setApproval(domestic.getApproval() + nation.getApprovalChange().get("approval.net"));
					domestic.setStability(domestic.getStability() + nation.getStabilityChange().get("stability.net"));

					/*
					 ** Military
					 */
					if(nation.getPolicy().getEconomy() != Policy.WAR_ECONOMY)
					{
						nation.getArmy().setTraining(nation.getArmy().getTraining() - 1);
					}
					nation.getMilitary().setWarProtection(nation.getMilitary().getWarProtection() - 1);

					if(nation.isAtWar())
					{
						for(City city : nation.getCities().getCities().values())
						{
							city.setDevastation(city.getDevastation() - (int)(Math.random() * 5));
						}
					}
					else
					{
						for(City city : nation.getCities().getCities().values())
						{
							city.setDevastation(city.getDevastation() - ((int)(Math.random() * 5) + 5));
						}
					}

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

			/*
			 ** Logs
			 */
			connection.prepareStatement("DELETE FROM cloc_war_logs").execute();
			connection.commit();
			Util.week++;
			Stats.getInstance().updateStats();
			Stats.getInstance().writeLog();
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
