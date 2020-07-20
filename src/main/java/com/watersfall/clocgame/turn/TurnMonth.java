package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.action.EventActions;
import com.watersfall.clocgame.dao.EventDao;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.Stats;
import com.watersfall.clocgame.model.TextKey;
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
			NationDao dao = new NationDao(connection, true);
			PreparedStatement ids = connection.prepareStatement("SELECT id FROM cloc_login");
			connection.prepareStatement("UPDATE cloc_main SET month=month+1").execute();
			ResultSet results = ids.executeQuery();
			while(results.next())
			{
				int id = results.getInt(1);
				Nation nation = dao.getNationById(id);
				try
				{
					NationEconomy economy = nation.getEconomy();
					NationDomestic domestic = nation.getDomestic();
					/*
					 ** Economy
					 */

					// Production
					HashMap<TextKey, Double> coal = nation.getTotalCoalProduction();
					HashMap<TextKey, Double> iron = nation.getTotalIronProduction();
					HashMap<TextKey, Double> oil = nation.getTotalOilProduction();
					HashMap<TextKey, Double> steel = nation.getTotalSteelProduction();
					HashMap<TextKey, Double> nitrogen = nation.getTotalNitrogenProduction();
					HashMap<TextKey, Double> research = nation.getTotalResearchProduction();
					HashMap<TextKey, Double> food = nation.getFoodProduction();
					economy.setCoal(economy.getCoal() + coal.get(TextKey.Resource.TOTAL_GAIN));
					economy.setIron(economy.getIron() + iron.get(TextKey.Resource.TOTAL_GAIN));
					economy.setOil(economy.getOil() + oil.get(TextKey.Resource.TOTAL_GAIN));
					economy.setResearch(economy.getResearch() + research.get(TextKey.Resource.NET));
					economy.setFood(economy.getFood() + food.get(TextKey.Resource.NET));
					economy.setGrowth(economy.getGrowth() + nation.getGrowthChange().get(TextKey.Growth.NET));
					economy.setGdp(economy.getGdp() + economy.getGrowth());

					if(economy.getCoal() > coal.get(TextKey.Resource.FACTORY_UPKEEP)
							&& economy.getIron() > iron.get(TextKey.Resource.FACTORY_UPKEEP)
							&& economy.getOil() > oil.get(TextKey.Resource.FACTORY_UPKEEP))
					{
						economy.setSteel(economy.getSteel() + steel.get(TextKey.Resource.NET));
						economy.setNitrogen(economy.getNitrogen() + nitrogen.get(TextKey.Resource.NET));
					}

					economy.setRecentConscription(economy.getRecentConscription() / 2);
					economy.setRecentDeconscription(economy.getRecentDeconscription() / 2);

					/*
					 ** Domestic
					 */
					domestic.setApproval(domestic.getApproval() + nation.getApprovalChange().get(TextKey.Approval.NET));
					domestic.setStability(domestic.getStability() + nation.getStabilityChange().get(TextKey.Stability.NET));

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
						for(City city : nation.getCities().values())
						{
							city.setDevastation(city.getDevastation() - (int)(Math.random() * 5));
						}
					}
					else
					{
						for(City city : nation.getCities().values())
						{
							city.setDevastation(city.getDevastation() - ((int)(Math.random() * 5) + 5));
						}
					}

					nation.getArmy().setFortification((int)(nation.getArmy().getFortification()
							+ nation.getFortificationChange().get(TextKey.Fortification.NET)));

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
							ArrayList<City> cities = new ArrayList<>(nation.getCities().values());
							cities.removeIf((City::hasStrike));
							cities.removeIf((city -> {
								for(Events event : nation.getEvents())
								{
									if(event.getCityId() == city.getId())
										return true;
								}
								return false;
							}));
							if(!cities.isEmpty())
							{
								City city = (City)cities.toArray()[(int)(Math.random() * cities.size())];
								EventDao eventDao = new EventDao(connection, true);
								eventDao.createEvent(nation.getId(), Event.STRIKE, Event.generateEventText(Event.STRIKE), city.getId());
							}
						}
					}


					/*
					** Event Timeouts
					 */
					for(Events event : nation.getEvents())
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

					dao.saveNation(nation);
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
