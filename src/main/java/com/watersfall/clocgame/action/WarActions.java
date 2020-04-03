package com.watersfall.clocgame.action;

import com.watersfall.clocgame.model.military.Fighter;
import com.watersfall.clocgame.model.military.LogType;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.News;
import com.watersfall.clocgame.model.war.Log;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class WarActions
{
	private static String winWar(Nation attacker, Nation defender) throws SQLException
	{
		PreparedStatement updateWar = attacker.getConn().prepareStatement("UPDATE cloc_war SET end=?, winner=? WHERE attacker=? AND defender=? AND end=-1");
		updateWar.setLong(1, Util.month);
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
		defender.getMilitary().setWarProtection(4);
		String message = News.createMessage(News.ID_WAR_LOST, attacker.getNationUrl());
		News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
		attacker.getArmy().setFortification(0);
		defender.getArmy().setFortification(0);
		update(attacker, defender);
		return Responses.warWon();
	}

	private static void doLog(Nation attacker, Nation defender, LogType type) throws SQLException
	{
		Log.createLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), type, 0);
	}

	private static void doCasualties(Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		attacker.getArmy().setSize(attacker.getArmy().getSize() - attackerCasualties);
		attacker.getDomestic().setManpowerLost(attacker.getDomestic().getManpowerLost() + attackerCasualties * 1000);
		attacker.damagePopulation(attackerCasualties * 1000);
		defender.getArmy().setSize(defender.getArmy().getSize() - defenderCasualties);
		defender.getDomestic().setManpowerLost(defender.getDomestic().getManpowerLost() + defenderCasualties * 1000);
		defender.damagePopulation(defenderCasualties * 1000);
	}

	private static String battleWon(Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		update(attacker, defender);
		News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(),
				News.createMessage(News.ID_DEFENSIVE_LOST, attacker.getNationUrl(), defenderCasualties, attackerCasualties));
		return Responses.offensiveVictory(attackerCasualties, defenderCasualties);
	}

	private static String battleLost(Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		update(attacker, defender);
		News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(),
				News.createMessage(News.ID_DEFENSIVE_WON, attacker.getNationUrl(), defenderCasualties, attackerCasualties));
		return Responses.offensiveDefeat(attackerCasualties, defenderCasualties);
	}

	private static String cityBattleWon(City city, Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		city.setDevastation(city.getDevastation() + 5 + (int)(Math.random() * 6));
		News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(),
				News.createMessage(News.ID_DEFENSIVE_CITY_LOST, attacker.getNationUrl(), city.getName(), attackerCasualties, defenderCasualties));
		update(attacker, defender);
		return Responses.offensiveCityVictory(city, attackerCasualties, defenderCasualties);
	}

	private static String cityBattleLost(City city, Nation attacker, Nation defender, int attackerCasualties, int defenderCasualties) throws SQLException
	{
		city.setDevastation(city.getDevastation() + (int)(Math.random() * 6));
		News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(),
				News.createMessage(News.ID_DEFENSIVE_CITY_WON, attacker.getNationUrl(), city.getName(), defenderCasualties, attackerCasualties));
		update(attacker, defender);
		return Responses.offensiveCityDefeat(city, attackerCasualties, defenderCasualties);
	}

	private static void update(Nation attacker, Nation defender) throws SQLException
	{
		attacker.update();
		defender.update();
	}

	public static String infantryBattle(Nation attacker, Nation defender) throws SQLException
	{
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getArmy().getSize() < 5)
		{
			return Responses.noTroopsForAttack();
		}
		else if(defender.getArmy().getSize() <= 5)
		{
			return winWar(attacker, defender);
		}
		else
		{
			double attackerPower = attacker.getPower();
			double defenderPower = defender.getPower();
			defenderPower = defenderPower + (defenderPower * (1 - attacker.getBreakthrough()));
			int attackerCasualties = attacker.getCasualties(attackerPower, defenderPower);
			int defenderCasualties = defender.getCasualties(defenderPower, attackerPower);
			if(attackerCasualties > 0.75 * attacker.getArmy().getSize())
			{
				defenderCasualties = (int)(defenderCasualties * ((attacker.getArmy().getSize() * 0.75) / attackerCasualties));
				attackerCasualties = (int)(attacker.getArmy().getSize() * 0.75);
			}
			if(defenderCasualties > 0.75 * defender.getArmy().getSize())
			{
				attackerCasualties = (int)(attackerCasualties * ((defender.getArmy().getSize() * 0.75) / defenderCasualties));
				defenderCasualties = (int)(defender.getArmy().getSize() * 0.75);
			}
			attacker.getArmy().setFortification(0);
			defender.getArmy().setFortification(defender.getArmy().getFortification() - 1);
			doLog(attacker, defender, LogType.LAND);
			doCasualties(attacker, defender, attackerCasualties, defenderCasualties);
			update(attacker, defender);
			if(attackerPower > defenderPower)
			{
				String message = News.createMessage(News.ID_DEFENSIVE_LOST
						, attacker.getNationUrl(), Integer.toString(defenderCasualties), Integer.toString(attackerCasualties));
				News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
				return battleWon(attacker, defender, attackerCasualties, defenderCasualties);
			}
			else
			{
				String message = News.createMessage(News.ID_DEFENSIVE_WON
						, attacker.getNationUrl(), Integer.toString(defenderCasualties), Integer.toString(attackerCasualties));
				News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
				return battleLost(attacker, defender, attackerCasualties, defenderCasualties);
			}
		}
	}

	public static String cityBattle(Nation attacker, Nation defender) throws SQLException
	{
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getArmy().getSize() < 5)
		{
			return Responses.noTroopsForAttack();
		}
		else if(defender.getArmy().getSize() <= 5)
		{
			return winWar(attacker, defender);
		}
		else
		{
			Collection<City> collection = defender.getCities().getCities().values();
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
				defenderPower = defenderPower + (defenderPower * (1 - attacker.getBreakthrough()));
				attackerPower *= 0.85;
				int attackerCasualties = attacker.getCasualties(attackerPower, defenderPower);
				int defenderCasualties = defender.getCasualties(defenderPower, attackerPower);
				if(attackerCasualties > 0.75 * attacker.getArmy().getSize())
				{
					defenderCasualties = (int)(defenderCasualties * ((attacker.getArmy().getSize() * 0.75) / attackerCasualties));
					attackerCasualties = (int)(attacker.getArmy().getSize() * 0.75);
				}
				if(defenderCasualties > 0.75 * defender.getArmy().getSize())
				{
					attackerCasualties = (int)(attackerCasualties * ((defender.getArmy().getSize() * 0.75) / defenderCasualties));
					defenderCasualties = (int)(defender.getArmy().getSize() * 0.75);
				}
				doLog(attacker, defender, LogType.LAND);
				doCasualties(attacker, defender, attackerCasualties, defenderCasualties);
				attacker.getArmy().setFortification(0);
				defender.getArmy().setFortification(defender.getArmy().getFortification() - 1);
				update(attacker, defender);
				if(attackerPowerDecideIfWin > defenderPower)
				{
					String message = News.createMessage(News.ID_DEFENSIVE_CITY_LOST,
							attacker.getNationUrl(),
							city.getName(),
							Integer.toString(defenderCasualties),
							Integer.toString(attackerCasualties));
					News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
					return cityBattleWon(city, attacker, defender, attackerCasualties, defenderCasualties);
				}
				else
				{
					String message = News.createMessage(News.ID_DEFENSIVE_CITY_WON,
							attacker.getNationUrl(),
							city.getName(),
							Integer.toString(attackerCasualties),
							Integer.toString(defenderCasualties));
					News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
					return cityBattleLost(city, attacker, defender, attackerCasualties, defenderCasualties);
				}
			}
		}
	}

	public static String airBattle(Nation attacker, Nation defender) throws SQLException
	{
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getFighterPower() == 0)
		{
			return Responses.attackerNoAirforce();
		}
		else if(defender.getFighterPower() == 0)
		{
			return Responses.defenderNoAirforce();
		}
		else
		{
			double attackerPower = attacker.getFighterPower();
			double defenderPower = defender.getFighterPower();
			System.out.println("Attack Power:  " + attackerPower);
			System.out.println("Defense Power: " + defenderPower);
			int totalAttackerLosses = 0;
			int totalDefenderLosses = 0;
			while(attackerPower > 0)
			{
				Fighter fighter = Fighter.values()[(int)(Math.random() * Fighter.values().length)];
				if(defender.getFighter(fighter) > 0)
				{
					int random = (int)(Math.random() * fighter.getPower() * 2);
					if(random > fighter.getPower())
					{
						defender.setFighter(fighter, defender.getFighter(fighter) - 1);
						attackerPower -= random;
						totalDefenderLosses++;
					}
				}
				else
				{
					if(defender.getFighterPower() < 1)
					{
						break;
					}
				}
			}
			while(defenderPower > 0)
			{
				Fighter fighter = Fighter.values()[(int)(Math.random() * Fighter.values().length)];
				if(attacker.getFighter(fighter) > 0)
				{
					int random = (int)(Math.random() * fighter.getPower() * 2);
					if(random > fighter.getPower())
					{
						attacker.setFighter(fighter, attacker.getFighter(fighter) - 1);
						defenderPower -= random;
						totalAttackerLosses++;
					}
				}
				else
				{
					if(attacker.getFighterPower() < 1)
					{
						break;
					}
				}
			}
			doLog(attacker, defender, LogType.AIR);
			News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(),
					News.createMessage(News.ID_AIR_BATTLE, attacker.getNationUrl(), totalDefenderLosses, totalAttackerLosses));
			update(attacker, defender);
			return Responses.airBattle(totalAttackerLosses, totalDefenderLosses);
		}
	}

	public static String airBombTroops(Nation attacker, Nation defender) throws SQLException
	{
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getFighterPower() == 0 || attacker.getBomberPower() == 0)
		{
			return Responses.attackerNoAirforce();
		}
		else
		{
			doLog(attacker, defender, LogType.AIR);
			if(defender.getFighterPower() * 2 > attacker.getFighterPower())
			{
				boolean intercepted = Math.random() < (defender.getFighterPower() / attacker.getFighterPower());
				if(intercepted)
				{
					return airBattle(attacker, defender);
				}
			}
			int damage = Math.max(1, (int)Math.sqrt(attacker.getBomberPower()));
			damage = Math.min(damage, defender.getArmy().getSize() / 10);
			String newsMessage = News.createMessage(News.ID_AIR_BOMB_TROOPS, attacker.getNationUrl(), damage);
			News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), newsMessage);
			doCasualties(attacker, defender, 0, damage);
			update(attacker, defender);
			return Responses.bombTroops(damage);
		}
	}

	public static String airBombCity(Nation attacker, Nation defender) throws SQLException
	{
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getFighterPower() == 0 || attacker.getBomberPower() == 0)
		{
			return Responses.attackerNoAirforce();
		}
		else
		{
			Collection<City> collection = defender.getCities().getCities().values();
			collection.removeIf(city -> city.getDevastation() >= 100);
			if(collection.isEmpty())
			{
				return Responses.defenderAlreadyDevastated();
			}
			else
			{
				doLog(attacker, defender, LogType.AIR);
				if(defender.getFighterPower() * 2 > attacker.getFighterPower())
				{
					boolean intercepted = Math.random() < (defender.getFighterPower() / attacker.getFighterPower());
					if(intercepted)
					{
						return airBattle(attacker, defender);
					}
				}
				City city = (City)collection.toArray()[(int)(Math.random() * collection.size())];
				int damage = Math.max(1, (int)Math.sqrt(attacker.getBomberPower()));
				city.setDevastation(city.getDevastation() + damage);
				String newsMessage = News.createMessage(News.ID_AIR_BOMBARD, attacker.getNationUrl(), city.getName());
				News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), newsMessage);
				update(attacker, defender);
				return Responses.bombard();
			}
		}
	}

	public static String entrench(Nation attacker, Nation defender) throws SQLException
	{
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getArmy().getFortification() >= 10)
		{
			return Responses.alreadyFortified();
		}
		else
		{
			attacker.getArmy().setFortification(attacker.getArmy().getFortification() + 1);
			Log.createLog(attacker.getConn(), attacker.getId(), defender.getForeign().getRegion(), LogType.LAND, 0);
			String news = News.createMessage(News.ID_FORTIFICATION, attacker.getNationUrl());
			News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), news);
			update(attacker, defender);
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
