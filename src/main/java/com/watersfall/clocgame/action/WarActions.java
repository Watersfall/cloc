package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.LogDao;
import com.watersfall.clocgame.dao.NewsDao;
import com.watersfall.clocgame.model.LogType;
import com.watersfall.clocgame.model.city.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.news.News;
import com.watersfall.clocgame.model.producible.IFighterPower;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.model.producible.Producibles;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Time;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class WarActions
{
	private static String winWar(Nation attacker, Nation defender) throws SQLException
	{
		PreparedStatement updateWar = attacker.getConn().prepareStatement("UPDATE wars SET end=?, winner=? WHERE attacker=? AND defender=? AND end=-1");
		updateWar.setLong(1, Time.month);
		updateWar.setInt(2, attacker.getId());
		if(attacker.getOffensive() != null && attacker.getOffensive().getId() == defender.getId())
		{
			updateWar.setInt(3, attacker.getId());
			updateWar.setInt(4, defender.getId());
		}
		else
		{
			updateWar.setInt(3, defender.getId());
			updateWar.setInt(4, attacker.getId());
		}
		updateWar.execute();
		defender.getStats().setWarProtection(4);
		defender.getStats().setArmySize(10);
		defender.getStats().setLostManpower(defender.getStats().getLostManpower() - 10000);
		int landChange = defender.getStats().getLand() / 10;
		double ironChange = defender.getStats().getIron() / 3;
		double coalChange = defender.getStats().getCoal() / 3;
		double oilChange = defender.getStats().getOil() / 3;
		double nitrogenChange = defender.getStats().getNitrogen() / 3;
		double steelChange = defender.getStats().getSteel() / 3;
		double budgetChange = defender.getStats().getBudget() / 3;
		defender.getStats().setLand(defender.getStats().getLand() - landChange);
		defender.getStats().setIron(defender.getStats().getIron() - ironChange);
		defender.getStats().setCoal(defender.getStats().getCoal() - coalChange);
		defender.getStats().setOil(defender.getStats().getOil() - oilChange);
		defender.getStats().setNitrogen(defender.getStats().getNitrogen() - nitrogenChange);
		defender.getStats().setSteel(defender.getStats().getSteel() - steelChange);
		defender.getStats().setBudget(defender.getStats().getBudget() - budgetChange);
		attacker.getStats().setLand(attacker.getStats().getLand() + landChange);
		attacker.getStats().setIron(attacker.getStats().getIron() + ironChange);
		attacker.getStats().setCoal(attacker.getStats().getCoal() + coalChange);
		attacker.getStats().setOil(attacker.getStats().getOil() + oilChange);
		attacker.getStats().setNitrogen(attacker.getStats().getNitrogen() + nitrogenChange);
		attacker.getStats().setSteel(attacker.getStats().getSteel() + steelChange);
		attacker.getStats().setBudget(attacker.getStats().getBudget() + budgetChange);
		for(City city : defender.getCities().values())
		{
			city.setDevastation(city.getDevastation() + 25);
		}
		String message = News.createMessage(News.ID_WAR_LOST, attacker.getNationUrl());
		NewsDao dao = new NewsDao(attacker.getConn(), true);
		dao.createNews(attacker.getId(), defender.getId(), message);
		return Responses.warWon();
	}

	private static void doLog(Nation attacker, Nation defender, LogType type) throws SQLException
	{
		LogDao dao = new LogDao(attacker.getConn(), true);
		dao.createLog(attacker.getId(), type);
	}

	private static void doCasualties(Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		attacker.getStats().setArmySize(attacker.getStats().getArmySize() - attackerCasualties);
		attacker.getStats().setCasualties(attacker.getStats().getCasualties() + attackerCasualties);
		attacker.getStats().setLostManpower(attacker.getStats().getLostManpower() + attackerCasualties * 1000);
		attacker.damagePopulation(attackerCasualties * 1000);
		defender.getStats().setArmySize(defender.getStats().getArmySize() - defenderCasualties);
		defender.getStats().setCasualties(defender.getStats().getCasualties() + defenderCasualties);
		defender.getStats().setLostManpower(defender.getStats().getLostManpower() + defenderCasualties * 1000);
		defender.damagePopulation(defenderCasualties * 1000);
	}

	private static String battleWon(Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		String message = News.createMessage(News.ID_DEFENSIVE_LOST, attacker.getNationUrl(), defenderCasualties, attackerCasualties);
		NewsDao dao = new NewsDao(attacker.getConn(), true);
		dao.createNews(attacker.getId(), defender.getId(), message);
		return Responses.offensiveVictory(attackerCasualties, defenderCasualties);
	}

	private static String battleLost(Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		String message = News.createMessage(News.ID_DEFENSIVE_WON, attacker.getNationUrl(), defenderCasualties, attackerCasualties);
		NewsDao dao = new NewsDao(attacker.getConn(), true);
		dao.createNews(attacker.getId(), defender.getId(), message);
		return Responses.offensiveDefeat(attackerCasualties, defenderCasualties);
	}

	private static String cityBattleWon(City city, Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		city.setDevastation(city.getDevastation() + 5 + (int)(Math.random() * 6));
		String message = News.createMessage(News.ID_DEFENSIVE_CITY_LOST, attacker.getNationUrl(), city.getName(), attackerCasualties, defenderCasualties);
		NewsDao dao = new NewsDao(attacker.getConn(), true);
		dao.createNews(attacker.getId(), defender.getId(), message);
		return Responses.offensiveCityVictory(city, attackerCasualties, defenderCasualties);
	}

	private static String cityBattleLost(City city, Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		city.setDevastation(city.getDevastation() + (int)(Math.random() * 6));
		String message = News.createMessage(News.ID_DEFENSIVE_CITY_WON, attacker.getNationUrl(), city.getName(), defenderCasualties, attackerCasualties);
		NewsDao dao = new NewsDao(attacker.getConn(), true);
		dao.createNews(attacker.getId(), defender.getId(), message);
		return Responses.offensiveCityDefeat(city, attackerCasualties, defenderCasualties);
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

	public static String infantryBattle(Nation attacker, Nation defender) throws SQLException
	{
		LogDao dao = new LogDao(attacker.getConn(), true);
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(dao.checkLog(attacker.getId(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getStats().getArmySize() < 5)
		{
			return Responses.noTroopsForAttack();
		}
		else if(defender.getStats().getArmySize() <= 5)
		{
			return winWar(attacker, defender);
		}
		else
		{
			double attackerPower = attacker.getPower();
			double defenderPower = defender.getPower();
			double defenseMod = Math.max(0, (defender.getDefense() - attacker.getBreakthrough()));
			defenderPower = defenderPower + (defenderPower * defenseMod);
			int attackerCasualties = attacker.getCasualties(attackerPower, defenderPower);
			int defenderCasualties = defender.getCasualties(defenderPower, attackerPower);
			if(attackerCasualties > 0.75 * attacker.getStats().getArmySize())
			{
				defenderCasualties = (int)(defenderCasualties * ((attacker.getStats().getArmySize() * 0.75) / attackerCasualties));
				attackerCasualties = (int)(attacker.getStats().getArmySize() * 0.75);
			}
			if(defenderCasualties > 0.75 * defender.getStats().getArmySize())
			{
				attackerCasualties = (int)(attackerCasualties * ((defender.getStats().getArmySize() * 0.75) / defenderCasualties));
				defenderCasualties = (int)(defender.getStats().getArmySize() * 0.75);
			}
			doLog(attacker, defender, LogType.LAND);
			doCasualties(attacker, defender, attackerCasualties, defenderCasualties);
			if(attackerPower > defenderPower)
			{
				return battleWon(attacker, defender, attackerCasualties, defenderCasualties);
			}
			else
			{
				return battleLost(attacker, defender, attackerCasualties, defenderCasualties);
			}
		}
	}

	public static String cityBattle(Nation attacker, Nation defender) throws SQLException
	{
		LogDao dao = new LogDao(attacker.getConn(), true);
		NewsDao newsDao = new NewsDao(attacker.getConn(), true);
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(dao.checkLog(attacker.getId(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getStats().getArmySize() < 5)
		{
			return Responses.noTroopsForAttack();
		}
		else if(defender.getStats().getArmySize() <= 5)
		{
			return winWar(attacker, defender);
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
				City city = (City)collection.toArray()[(int)(Math.random() * collection.size())];
				double attackerPower = attacker.getPower();
				double defenderPower = defender.getPower();
				double attackerPowerDecideIfWin = attackerPower;
				double defenseMod = Math.max(0, (defender.getDefense() - attacker.getBreakthrough()));
				defenderPower = defenderPower + (defenderPower * defenseMod);
				attackerPower *= 0.85;
				int attackerCasualties = attacker.getCasualties(attackerPower, defenderPower);
				int defenderCasualties = defender.getCasualties(defenderPower, attackerPower);
				if(attackerCasualties > 0.75 * attacker.getStats().getArmySize())
				{
					defenderCasualties = (int)(defenderCasualties * ((attacker.getStats().getArmySize() * 0.75) / attackerCasualties));
					attackerCasualties = (int)(attacker.getStats().getArmySize() * 0.75);
				}
				if(defenderCasualties > 0.75 * defender.getStats().getArmySize())
				{
					attackerCasualties = (int)(attackerCasualties * ((defender.getStats().getArmySize() * 0.75) / defenderCasualties));
					defenderCasualties = (int)(defender.getStats().getArmySize() * 0.75);
				}
				doLog(attacker, defender, LogType.LAND);
				doCasualties(attacker, defender, attackerCasualties, defenderCasualties);
				if(attackerPowerDecideIfWin > defenderPower)
				{
					return cityBattleWon(city, attacker, defender, attackerCasualties, defenderCasualties);
				}
				else
				{
					return cityBattleLost(city, attacker, defender, attackerCasualties, defenderCasualties);
				}
			}
		}
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

	public static String airBombTroops(Nation attacker, Nation defender) throws SQLException
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
		else if(attacker.getFighterPower(true) == 0 || attacker.getBomberPower() == 0)
		{
			return Responses.attackerNoAirforce();
		}
		else
		{
			if(checkInterception(attacker, defender))
			{
				return airBattle(attacker, defender, true);
			}
			int damage = Math.max(1, (int)Math.sqrt(attacker.getBomberPower()));
			damage = Math.min(damage, defender.getStats().getArmySize() / 10);
			String message = News.createMessage(News.ID_AIR_BOMB_TROOPS, attacker.getNationUrl(), damage);
			newsDao.createNews(attacker.getId(), defender.getId(), message);
			doCasualties(attacker, defender, 0, damage);
			return Responses.bombTroops(damage);
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

	public static String entrench(Nation attacker, Nation defender) throws SQLException
	{
		LogDao dao = new LogDao(attacker.getConn(), true);
		NewsDao newsDao = new NewsDao(attacker.getConn(), true);
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(dao.checkLog(attacker.getId(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getStats().getFortification() >= attacker.getMaximumFortificationLevel())
		{
			return Responses.alreadyFortified();
		}
		else
		{
			int increase = Math.max(250, attacker.getMaximumFortificationLevel() / 10);
			attacker.getStats().setFortification(attacker.getStats().getFortification() + increase);
			doLog(attacker, defender, LogType.LAND);
			String message = News.createMessage(News.ID_FORTIFICATION, attacker.getNationUrl());
			newsDao.createNews(attacker.getId(), defender.getId(), message);
			return Responses.fortified();
		}
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