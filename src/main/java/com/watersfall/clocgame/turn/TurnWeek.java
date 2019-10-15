package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.database.Database;
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
				HashMap<String, Double> weapons = nation.getTotalWeaponsProduction();
				HashMap<String, Double> research = nation.getTotalResearchProduction();
				HashMap<String, Integer> food = nation.getFoodProduction();
				economy.setCoal(economy.getCoal() + coal.get("total"));
				economy.setIron(economy.getIron() + iron.get("total"));
				economy.setOil(economy.getOil() + oil.get("total"));
				economy.setResearch(economy.getResearch() + research.get("total"));
				economy.setFood(economy.getFood() + food.get("net"));
				if(economy.getCoal() >= -coal.get("civilian factory demands") && economy.getIron() >= -iron.get("civilian factory demands"))
				{
					economy.setSteel(economy.getSteel() + steel.get("total"));
					economy.setCoal(economy.getCoal() + coal.get("civilian factory demands"));
					economy.setIron(economy.getIron() + iron.get("civilian factory demands"));
					economy.setGrowth(economy.getGrowth() + nation.getGrowthChange().get("civilian industry"));
				}
				if(economy.getCoal() >= -coal.get("military factory demands") && economy.getIron() >= -iron.get("military factory demands"))
				{
					nation.getArmy().setMusket(nation.getArmy().getMusket() + weapons.get("total").intValue());
					economy.setCoal(economy.getCoal() + coal.get("military factory demands"));
					economy.setIron(economy.getIron() + iron.get("military factory demands"));
					economy.setGrowth(economy.getGrowth() + nation.getGrowthChange().get("military industry"));
				}
				if(economy.getCoal() >= -coal.get("nitrogen plant demands") && economy.getIron() >= -iron.get("nitrogen plant demands"))
				{
					economy.setNitrogen(economy.getNitrogen() + nitrogen.get("total"));
					economy.setCoal(economy.getCoal() + coal.get("nitrogen plant demands"));
					economy.setIron(economy.getIron() + iron.get("nitrogen plant demands"));
					economy.setGrowth(economy.getGrowth() + nation.getGrowthChange().get("nitrogen industry"));
				}
				economy.setGrowth(economy.getGrowth() + nation.getGrowthChange().get("army"));
				economy.setGdp(economy.getGdp() + economy.getGrowth());

				/*
				 ** Domestic
				 */
				domestic.setPopulation(domestic.getPopulation() + (nation.getPopulationGrowth().get("total")).longValue());
				domestic.setApproval(domestic.getApproval() + nation.getApprovalChange().get("total"));
				domestic.setStability(domestic.getStability() + nation.getStabilityChange().get("total"));
				nation.update();

				/*
				** Military
				 */
				nation.getArmy().setTraining(nation.getArmy().getTraining() - 1);
				nation.getMilitary().setWarProtection(nation.getMilitary().getWarProtection() - 1);
			}

			/*
			 ** Logs
			 */
			connection.prepareStatement("DELETE FROM cloc_war_logs").execute();

			connection.commit();
			Util.turn++;

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
