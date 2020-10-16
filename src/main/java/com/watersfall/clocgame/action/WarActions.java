package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.LogDao;
import com.watersfall.clocgame.dao.NewsDao;
import com.watersfall.clocgame.model.city.City;
import com.watersfall.clocgame.model.military.army.BattlePlan;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.news.News;
import com.watersfall.clocgame.model.producible.IFighterPower;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.model.producible.Producibles;
import com.watersfall.clocgame.model.war.LogType;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class WarActions
{
	private static void doLog(Nation attacker, Nation defender, LogType type) throws SQLException
	{
		LogDao dao = new LogDao(attacker.getConn(), true);
		dao.createLog(attacker.getId(), type);
	}

	private static int runFighterCalc(Nation nation, double power)
	{
		ArrayList<Producibles> planes = Producibles.getProduciblesByCategories(ProducibleCategory.BOMBER_PLANE, ProducibleCategory.FIGHTER_PLANE);
		//Adding fighters again to double their chance of being picked
		planes.addAll(Producibles.getProduciblesForCategory(ProducibleCategory.FIGHTER_PLANE));
		planes.removeIf(plane -> nation.getProducibles().getProducible(plane) <= 0);
		int totalLosses = 0;
		while(power > 0)
		{
			Producibles plane = planes.get((int)(Math.random() * planes.size()));
			if(plane.getProducible().getCategory() == ProducibleCategory.FIGHTER_PLANE)
			{
				IFighterPower fighter = (IFighterPower) plane.getProducible();
				if(nation.getProducibles().getProducible(plane) > 0)
				{
					int random = (int)(Math.random() * fighter.getFighterPower() * 2);
					if(random > fighter.getFighterPower())
					{
						nation.getProducibles().setProducible(plane, nation.getProducibles().getProducible(plane) - 1);
						power -= random;
						totalLosses++;
					}
				}
				else
				{
					if(nation.getFighterPower(false) < 1)
					{
						break;
					}
				}
			}
			else
			{
				IFighterPower defense = (IFighterPower) plane.getProducible();
				if(nation.getProducibles().getProducible(plane) > 0)
				{
					int random = (int)(Math.random() * defense.getFighterPower() * 2);
					if(random > defense.getFighterPower())
					{
						nation.getProducibles().setProducible(plane, nation.getProducibles().getProducible(plane) - 1);
						power -= random;
						totalLosses++;
					}
				}
				else
				{
					if(nation.getFighterPower(false) < 1)
					{
						break;
					}
				}
			}
		}
		nation.fixCurrentPlanes();
		return totalLosses;
	}

	private static boolean checkInterception(Nation attacker, Nation defender) throws SQLException
	{
		doLog(attacker, defender, LogType.AIR);
		if(defender.getFighterPower(false) * 2 > attacker.getFighterPower(false))
		{
			return Math.random() < (defender.getFighterPower(false) / attacker.getFighterPower(false));
		}
		return false;
	}

	public static String airBattle(Nation attacker, Nation defender, boolean interception) throws SQLException
	{
		LogDao dao = new LogDao(attacker.getConn(), true);
		NewsDao newsDao = new NewsDao(attacker.getConn(), true);
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(dao.checkLog(attacker.getId(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getFighterPower(true) == 0)
		{
			return Responses.attackerNoAirforce();
		}
		else if(defender.getFighterPower(false) == 0)
		{
			return Responses.defenderNoAirforce();
		}
		else
		{
			double attackerPower = attacker.getFighterPower(true);
			double defenderPower = defender.getFighterPower(false);
			int totalAttackerLosses = runFighterCalc(attacker, defenderPower);
			int totalDefenderLosses = runFighterCalc(defender, attackerPower);
			doLog(attacker, defender, LogType.AIR);
			String message = News.createMessage(News.ID_AIR_BATTLE, attacker.getNationUrl(), totalDefenderLosses, totalAttackerLosses);
			newsDao.createNews(attacker.getId(), defender.getId(), message);
			if(interception)
			{
				return Responses.airBattleInterception(totalAttackerLosses, totalDefenderLosses);
			}
			else
			{
				return Responses.airBattle(totalAttackerLosses, totalDefenderLosses);
			}
		}
	}

	public static String airBombCity(Nation attacker, Nation defender) throws SQLException
	{
		LogDao dao = new LogDao(attacker.getConn(), true);
		NewsDao newsDao = new NewsDao(attacker.getConn(), true);
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(dao.checkLog(attacker.getId(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getFighterPower(false) == 0 || attacker.getBomberPower() == 0)
		{
			return Responses.attackerNoAirforce();
		}
		else
		{
			Collection<City> collection = defender.getCities().values();
			collection.removeIf(city -> city.getDevastation() >= 100);
			if(collection.isEmpty())
			{
				return Responses.defenderAlreadyDevastated();
			}
			else
			{
				if(checkInterception(attacker, defender))
				{
					return airBattle(attacker, defender, true);
				}
				City city = (City)collection.toArray()[(int)(Math.random() * collection.size())];
				int damage = Math.max(1, (int)Math.sqrt(attacker.getBomberPower()));
				city.setDevastation(city.getDevastation() + damage);
				String message = News.createMessage(News.ID_AIR_BOMBARD, attacker.getNationUrl(), city.getName());
				newsDao.createNews(attacker.getId(), defender.getId(), message);
				return Responses.bombard();
			}
		}
	}

	public static String landBattle(BattlePlan attack, BattlePlan defense)
	{
		int attacks = attack.getAttack();
		int defenses = defense.getDefense();
		while(attacks > 0)
		{
			attacks -= 1;
			double rand = Math.random();
			if(defenses > 0)
			{
				defenses -= 1;
				if(rand > 0.75)
				{
					defense.damage(rand);
				}
			}
			else if(rand > 0.4)
			{
				defense.damage(rand);
			}
		}
		attacks = defense.getAttack();
		defenses = attack.getBreakthrough();
		while(attacks > 0)
		{
			attacks -= 1;
			double rand = Math.random();
			if(defenses > 0)
			{
				defenses -= 1;
				if(rand > 0.75)
				{
					attack.damage(rand);
				}
			}
			else if(rand > 0.4)
			{
				attack.damage(rand);
			}
		}
		return "";
	}

	public static String sendPeace(Nation sender, Nation receiver) throws SQLException
	{
		if(!sender.isAtWarWith(receiver))
		{
			return Responses.noWar();
		}
		else
		{
			return sender.sendPeace(receiver);
		}
	}
}