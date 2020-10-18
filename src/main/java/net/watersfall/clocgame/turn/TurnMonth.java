package net.watersfall.clocgame.turn;

import net.watersfall.clocgame.action.EventActions;
import net.watersfall.clocgame.dao.AlignmentDao;
import net.watersfall.clocgame.dao.ArmyDao;
import net.watersfall.clocgame.dao.EventDao;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.Stats;
import net.watersfall.clocgame.model.TextKey;
import net.watersfall.clocgame.model.alignment.Alignment;
import net.watersfall.clocgame.model.alignment.Alignments;
import net.watersfall.clocgame.model.city.City;
import net.watersfall.clocgame.model.event.Event;
import net.watersfall.clocgame.model.event.Events;
import net.watersfall.clocgame.model.military.army.Army;
import net.watersfall.clocgame.model.military.army.ArmyEquipment;
import net.watersfall.clocgame.model.military.army.Battalion;
import net.watersfall.clocgame.model.modifier.Modifiers;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.nation.NationStats;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.schedulers.DayScheduler;
import net.watersfall.clocgame.util.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
			int nationCount = 0;
			DayScheduler.resetIncrement();
			connection = Database.getDataSource().getConnection();
			NationDao dao = new NationDao(connection, true);
			PreparedStatement ids = connection.prepareStatement("SELECT id FROM login");
			connection.prepareStatement("UPDATE main SET month=month+1").execute();
			ResultSet results = ids.executeQuery();
			while(results.next())
			{
				nationCount++;
				int id = results.getInt(1);
				Nation nation = dao.getNationById(id);
				try
				{
					NationStats stats = nation.getStats();
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
					stats.setCoal(stats.getCoal() + coal.get(TextKey.Resource.TOTAL_GAIN));
					stats.setIron(stats.getIron() + iron.get(TextKey.Resource.TOTAL_GAIN));
					stats.setOil(stats.getOil() + oil.get(TextKey.Resource.TOTAL_GAIN));
					stats.setResearch(stats.getResearch() + research.get(TextKey.Resource.NET));
					stats.setFood(stats.getFood() + food.get(TextKey.Resource.NET));
					stats.setGrowth(stats.getGrowth() + nation.getGrowthChange().get(TextKey.Growth.NET));
					stats.setGdp(stats.getGdp() + stats.getGrowth());

					if(stats.getCoal() > coal.get(TextKey.Resource.FACTORY_UPKEEP)
							&& stats.getIron() > iron.get(TextKey.Resource.FACTORY_UPKEEP)
							&& stats.getOil() > oil.get(TextKey.Resource.FACTORY_UPKEEP))
					{
						stats.setSteel(stats.getSteel() + steel.get(TextKey.Resource.NET));
						stats.setNitrogen(stats.getNitrogen() + nitrogen.get(TextKey.Resource.NET));
					}

					stats.setRecentConscription(stats.getRecentConscription() / 2);
					stats.setRecentDeconscription(stats.getRecentDeconscription() / 2);

					/*
					 ** Domestic
					 */
					stats.setApproval(stats.getApproval() + nation.getApprovalChange().get(TextKey.Approval.NET));
					stats.setStability(stats.getStability() + nation.getStabilityChange().get(TextKey.Stability.NET));

					/*
					 ** Military
					 */
					stats.setWarProtection(stats.getWarProtection() - 1);

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

					for(Army army : nation.getArmies())
					{
						if(nation.getEquipmentUpgrades().containsKey(army))
						{
							for(Map.Entry<Producibles, Integer> entry : nation.getEquipmentUpgrades().get(army).entrySet())
							{
								int gain = entry.getValue();
								if(entry.getValue() > 0)
								{
									nation.getProducibles().setProducible(entry.getKey(), nation.getProducibles().getProducible(entry.getKey()) - gain);
									for(Battalion battalion : army.getBattalions())
									{
										if(battalion.isValidUpgrade(entry.getKey().getProducible()))
										{
											Producible lowest = battalion.getLowestTierEquipment(entry.getKey().getProducible().getCategory());
											for(ArmyEquipment equipment : battalion.getEquipment())
											{
												if(equipment.getEquipment().getProducible() == lowest)
												{
													if(gain >= equipment.getAmount())
													{
														equipment.setField("type", entry.getKey().name());
														gain -= equipment.getAmount();
													}
													else
													{
														equipment.setField("amount", equipment.getAmount() - gain);
														new ArmyDao(connection, true).createEquipment(battalion.getId(), entry.getKey(), gain);
														gain = 0;
													}
												}
											}
										}
									}
								}
							}
						}
						if(nation.getArmyEquipmentChange().containsKey(army))
						{
							for(Map.Entry<ProducibleCategory, Integer> entry : nation.getArmyEquipmentChange().get(army).entrySet())
							{
								int gain = nation.getArmyEquipmentChange().get(army).get(entry.getKey());
								if(entry.getValue() > 0)
								{
									for(Producibles producible : Producibles.getProduciblesForCategory(entry.getKey()))
									{
										if(gain <= 0)
										{
											break;
										}
										for(Battalion battalion : army.getBattalions())
										{
											if(nation.getProducibles().getProducible(producible) > 0)
											{
												int g = Math.min(gain, battalion.getRequiredEquipment().getOrDefault(producible.getProducible().getCategory(), 0));
												ArmyEquipment equipment = null;
												for(ArmyEquipment eq : battalion.getEquipment())
												{
													if(eq.getEquipment() == producible)
													{
														equipment = eq;
													}
												}
												if(equipment != null)
												{
													equipment.setField("amount", equipment.getAmount() + g);
												}
												else
												{
													new ArmyDao(connection, true).createEquipment(battalion.getId(), producible, g);
												}
												nation.getProducibles().setProducible(producible, nation.getProducibles().getProducible(producible) - g);
												gain -= g;
											}
										}
									}
								}
							}
						}
						if(nation.getArmyManpowerChange().containsKey(army))
						{
							int gain = nation.getArmyManpowerChange().get(army);
							for(Battalion battalion : army.getBattalions())
							{
								if(gain <= 0)
								{
									break;
								}
								int g = Math.min(gain, battalion.getNeededManpower());
								battalion.setField("size", battalion.getSize() + g);
								gain -= g;
							}
						}
					}

					stats.setFortification((int)(stats.getFortification()
							+ nation.getFortificationChange().get(TextKey.Fortification.NET)));

					if(nation.getFamineLevel() < 0)
					{
						nation.getStats().setMonthsInFamine(nation.getStats().getMonthsInFamine() + 1);
					}

					/*
					** Events
					 */
					if((nation.getStats().getStability() < 30 && nation.getStats().getApproval() < 30)
						|| (nation.getStats().getStability() < 15 || nation.getStats().getApproval() < 15))
					{
						if(Math.random() > 0.00)
						{
							ArrayList<City> cities = new ArrayList<>(nation.getCities().values());
							cities.removeIf((City::hasStrike));
							cities.removeIf((city -> {
								for(Event event : nation.getEvents())
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
								eventDao.createEvent(nation.getId(), Events.STRIKE, city.getId());
							}
						}
					}


					/*
					** Event Timeouts
					 */
					for(Event event : nation.getEvents())
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

					/*
					** Reputation
					 */
					int eGain = (int)(nation.getMaxReputation(Alignments.ENTENTE) / 20.0);
					int currentE = nation.getStats().getEntenteReputation();
					if(currentE + eGain > nation.getMaxReputation(Alignments.ENTENTE))
					{
						nation.getStats().setReputation(Alignments.ENTENTE, nation.getMaxReputation(Alignments.ENTENTE));
					}
					else
					{
						nation.getStats().setReputation(Alignments.ENTENTE, eGain + currentE);
					}
					int cGain = (int)(nation.getMaxReputation(Alignments.CENTRAL_POWERS) / 20.0);
					int currentC = nation.getStats().getCentralPowersReputation();
					if(currentC + cGain > nation.getMaxReputation(Alignments.CENTRAL_POWERS))
					{
						nation.getStats().setReputation(Alignments.CENTRAL_POWERS, nation.getMaxReputation(Alignments.CENTRAL_POWERS));
					}
					else
					{
						nation.getStats().setReputation(Alignments.CENTRAL_POWERS, cGain + currentC);
					}

					nation.getStats().setCurrentFighters(nation.getFighterChange() + nation.getStats().getCurrentFighters());


					PreparedStatement statement = connection.prepareStatement("INSERT INTO nation_history " +
							"(nation_id, month, gdp, growth, population, airforce, navy, army, casualties) " +
							"VALUES (?,?,?,?,?,?,?,?,?)");
					statement.setInt(1, nation.getId());
					statement.setLong(2, Time.month);
					statement.setDouble(3, nation.getStats().getGdp());
					statement.setDouble(4, nation.getStats().getGrowth());
					statement.setLong(5, nation.getTotalPopulation());
					statement.setLong(6, 0L);
					statement.setLong(7, 0L);
					statement.setInt(8, nation.getArmySize());
					statement.setLong(9, stats.getCasualties());
					statement.execute();

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
			connection.prepareStatement("DELETE FROM war_logs").execute();

			/*
			 ** Alignment calculations
			 */
			AlignmentDao alignmentDao = new AlignmentDao(connection, true);
			for(Alignments alignment : Alignments.values())
			{
				if(alignment != Alignments.NEUTRAL)
				{
					Alignment updateAlignment = alignmentDao.getAlignmentById(alignment);
					long ic = (long)((Math.random() * 75) + 50) * nationCount;
					long icPerLine = (long)((double)ic / (double) Alignment.ALLOWED_PRODUCIBLES.size());
					for(Producibles producible : Alignment.ALLOWED_PRODUCIBLES)
					{
						double randIc = icPerLine * ((Math.random() * 75.0 + 50.0) / 100.0);
						long produced = (long)Math.ceil(randIc / producible.getProducible().getProductionICCost());
						alignmentDao.updateProducible(producible, updateAlignment, produced);
					}
				}
			}

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
