package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.action.EventActions;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.Stats;
import com.watersfall.clocgame.model.nation.*;
import com.watersfall.clocgame.model.policies.Policy;
import com.watersfall.clocgame.schedulers.DayScheduler;
import com.watersfall.clocgame.util.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TurnMonth implements Runnable
{
	@Override
	public void run()
	{
		Connection connection = null;
		System.out.println("Running turn");
		Time.month++;
		Time.currentMonth = (int)(Time.month % 12);
		try
		{
			DayScheduler.resetIncrement();
			connection = Database.getDataSource().getConnection();
			PreparedStatement ids = connection.prepareStatement("SELECT id FROM cloc_login");
			connection.prepareStatement("UPDATE cloc_main SET month=month+1").execute();
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

					economy.setRecentConscription(economy.getRecentConscription() / 2);
					economy.setRecentDeconscription(economy.getRecentDeconscription() / 2);

					/*
					 ** Domestic
					 */
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

					nation.getArmy().setFortification((int)(nation.getArmy().getFortification()
							+ nation.getFortificationChange().get("fortification.net")));

					if(nation.getFamineLevel() < 0)
					{
						nation.getDomestic().setMonthsInFamine(nation.getDomestic().getMonthsInFamine() + 1);
					}

					/*
					** Events
					 */
					if((nation.getDomestic().getStability() < 30 && nation.getDomestic().getApproval() < 30)
						|| (nation.getDomestic().getStability() < 15 || nation.getDomestic().getApproval() < 15))
					{
						if(Math.random() > 0.00)
						{
							ArrayList<City> cities = new ArrayList<>(nation.getCities().getCities().values());
							cities.removeIf((City::hasStrike));
							cities.removeIf((city -> {
								for(Events event : nation.getNews().getEvents().values())
								{
									if(event.getCityId() == city.getId())
										return true;
								}
								return false;
							}));
							if(!cities.isEmpty())
							{
								City city = (City)cities.toArray()[(int)(Math.random() * cities.size())];
								Events.sendEvent(connection, nation, Event.STRIKE, Event.generateEventText(Event.STRIKE), city.getId());
							}
						}
					}


					/*
					** Event Timeouts
					 */
					for(Events event : nation.getNews().getEvents().values())
					{
						if(Time.month - event.getMonth() > 3)
						{
							switch(event.getEvent())
							{
								case STRIKE:
									EventActions.Strike.ignore(nation, event);
							}
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
			** Modifiers
			 */
			for(Modifiers modifier : Modifiers.values())
			{
				PreparedStatement statement = connection.prepareStatement("DELETE FROM modifiers WHERE type=? AND start<?");
				statement.setString(1, modifier.name());
				statement.setLong(2, Time.month - modifier.getLength());
				statement.execute();
			}

			/*
			 ** Logs
			 */
			connection.prepareStatement("DELETE FROM cloc_war_logs").execute();
			connection.commit();
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
