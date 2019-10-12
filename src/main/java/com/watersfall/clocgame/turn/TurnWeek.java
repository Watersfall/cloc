package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationDomestic;
import com.watersfall.clocgame.model.nation.NationEconomy;

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
			connection.prepareStatement("UPDATE cloc_main SET turn=turn+1").execute();
			ResultSet results = ids.executeQuery();
			while(results.next())
			{
				int id = results.getInt(1);
				Nation nation = new Nation(connection, id, true);
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
				if(coal.get("net") + economy.getCoal() >= coal.get("costs") && iron.get("net") + economy.getIron() >= iron.get("costs") && coal.get("net") + economy.getOil() >= oil.get("costs"))
				{
					economy.setCoal(economy.getCoal() + coal.get("net"));
					economy.setIron(economy.getIron() + iron.get("net"));
					economy.setOil(economy.getOil() + oil.get("net"));
					if(steel.get("net") + economy.getSteel() >= steel.get("costs"))
					{
						economy.setSteel(economy.getSteel() + steel.get("net"));
					}
					else
					{
						economy.setSteel(economy.getSteel() + steel.get("total"));
					}
					if(nitrogen.get("net") + economy.getNitrogen() >= nitrogen.get("costs"))
					{
						economy.setNitrogen(economy.getNitrogen() + nitrogen.get("net"));
					}
					else
					{
						economy.setNitrogen(economy.getNitrogen() + nitrogen.get("total"));
					}
				}
				else
				{
					economy.setCoal(economy.getCoal() + coal.get("total"));
					economy.setIron(economy.getIron() + iron.get("total"));
					economy.setOil(economy.getOil() + oil.get("total"));
				}
				economy.setFood(economy.getFood() + nation.getFoodProduction().get("net"));

				// Growth & GDP
				economy.setGdp(economy.getGdp() + economy.getGrowth());
				economy.setGrowth(economy.getGrowth() + nation.getGrowthChange().get("net"));
				if(economy.getGdp() < 0)
				{
					economy.setGdp(0);
				}

				/*
				 ** Domestic
				 */

				if(economy.getFood() < 0)
				{
					economy.setFood(0);
				}
				else
				{
					domestic.setPopulation(domestic.getPopulation() + (domestic.getPopulation() * (nation.getPopulationGrowth().get("total") * 0.25)));
				}

				domestic.setApproval(domestic.getApproval() + nation.getApprovalChange().get("total"));
				domestic.setStability(domestic.getStability() + nation.getStabilityChange().get("total"));

				nation.update();
			}

			/*
			 ** Logs
			 */

			connection.prepareStatement("DELETE FROM cloc_war_logs").execute();

			connection.commit();

		}
		catch(SQLException e)
		{
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
