package com.watersfall.clocgame.action;

import com.watersfall.clocgame.model.LogType;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.News;
import com.watersfall.clocgame.model.war.Log;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WarActions
{
	public static String landOffensive(Connection connection, Nation attacker, Nation defender) throws SQLException
	{
		if(attacker.getOffensive() != defender.getId() && attacker.getDefensive() != defender.getId())
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(connection, attacker.getId(), defender.getForeign().getRegion(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else
		{
			if(attacker.getArmy().getSize() <= 5)
			{
				return Responses.noTroopsForAttack();
			}
			else if(defender.getArmy().getSize() <= 5)
			{
				PreparedStatement updateWar = connection.prepareStatement("UPDATE cloc_war SET end=?, winner=? WHERE attacker=? AND defender=?");
				updateWar.setLong(1, Util.week);
				updateWar.setInt(2, attacker.getId());
				if(attacker.getOffensive() == defender.getId())
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
				attacker.update();
				defender.update();
				return Responses.warWon();
			}
			else
			{
				String losses;
				int attackLosses = (int)attacker.getAttackingCasualties(defender);
				int defenderLosses = (int)defender.getDefendingCasualties(attacker);
				if(attackLosses > attacker.getArmy().getSize())
				{
					attackLosses = attacker.getArmy().getSize();
				}
				if(defenderLosses > defender.getArmy().getSize())
				{
					defenderLosses = defender.getArmy().getSize();
				}
				attacker.getArmy().setSize(attacker.getArmy().getSize() - attackLosses);
				attacker.getDomestic().setManpowerLost(attacker.getDomestic().getManpowerLost() + attackLosses * 1000);
				attacker.getDomestic().setPopulation(attacker.getDomestic().getPopulation() - attackLosses * 1000);
				defender.getArmy().setSize(defender.getArmy().getSize() - defenderLosses);
				defender.getDomestic().setManpowerLost(defender.getDomestic().getManpowerLost() + defenderLosses * 1000);
				defender.getDomestic().setPopulation(defender.getDomestic().getPopulation() - defenderLosses * 1000);
				attacker.getArmy().setFortification(0);
				defender.getArmy().setFortification(0);
				attacker.update();
				defender.update();
				if(attacker.getPower() > defender.getPower())
				{
					String message = News.createMessage(News.ID_DEFENSIVE_LOST
							, attacker.getNationUrl(), Integer.toString(defenderLosses), Integer.toString(attackLosses));
					News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
					losses =  Responses.offensiveVictory(attackLosses, defenderLosses);
				}
				else
				{
					String message = News.createMessage(News.ID_DEFENSIVE_WON
							, attacker.getNationUrl(), Integer.toString(defenderLosses), Integer.toString(attackLosses));
					News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
					losses = Responses.offensiveDefeat(attackLosses, defenderLosses);
				}
				Log.createLog(connection, attacker.getId(), defender.getForeign().getRegion(), LogType.LAND, 0);
				return losses;
			}
		}
	}

	public static String cityBattle(Connection connection, Nation attacker, Nation defender) throws SQLException
	{
		if(attacker.getOffensive() != defender.getId() && attacker.getDefensive() != defender.getId())
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(connection, attacker.getId(), defender.getForeign().getRegion(), LogType.LAND))
		{
			return Responses.alreadyAttacked();
		}
		else
		{
			if(attacker.getArmy().getSize() <= 5)
			{
				return Responses.noTroopsForAttack();
			}
			else if(defender.getArmy().getSize() <= 5)
			{
				return landOffensive(connection, attacker, defender);
			}
			else
			{
				Set<Map.Entry<Integer, City>> cities = defender.getCities().getCities().entrySet();
				cities.removeIf((city) -> city.getValue().getDevastation() >= 100);
				if(cities.isEmpty())
				{
					return Responses.defenderAlreadyDevastated();
				}
				else
				{
					String losses;
					int attackLosses = (int)(attacker.getAttackingCasualties(defender) * 1.5);
					int defenderLosses = (int)defender.getDefendingCasualties(attacker);
					int damage = (int)(Math.random() * 10) + 5;
					if(attackLosses > attacker.getArmy().getSize())
					{
						attackLosses = attacker.getArmy().getSize();
					}
					if(defenderLosses > defender.getArmy().getSize())
					{
						defenderLosses = defender.getArmy().getSize();
					}
					attacker.getArmy().setSize(attacker.getArmy().getSize() - attackLosses);
					attacker.getDomestic().setManpowerLost(attacker.getDomestic().getManpowerLost() + attackLosses * 1000);
					attacker.getDomestic().setPopulation(attacker.getDomestic().getPopulation() - attackLosses * 1000);
					defender.getArmy().setSize(defender.getArmy().getSize() - defenderLosses);
					defender.getDomestic().setManpowerLost(defender.getDomestic().getManpowerLost() + defenderLosses * 1000);
					defender.getDomestic().setPopulation(defender.getDomestic().getPopulation() - defenderLosses * 1000);
					HashMap.Entry entry = (HashMap.Entry)cities.toArray()[(int)(Math.random() * cities.size())];
					City city = (City)entry.getValue();
					if(attacker.getPower() > defender.getPower())
					{
						String message = News.createMessage(News.ID_DEFENSIVE_CITY_LOST
								, attacker.getNationUrl(), city.getName(), Integer.toString(defenderLosses), Integer.toString(attackLosses));
						News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
						losses =  Responses.offensiveCityVictory(city, attackLosses, defenderLosses);
						city.setDevastation(city.getDevastation() + damage);
					}
					else
					{
						String message = News.createMessage(News.ID_DEFENSIVE_CITY_WON
								, attacker.getNationUrl(), city.getName(), Integer.toString(defenderLosses), Integer.toString(attackLosses));
						News.sendNews(attacker.getConn(), attacker.getId(), defender.getId(), message);
						losses = Responses.offensiveCityDefeat(city, attackLosses, defenderLosses);
						city.setDevastation(city.getDevastation() + damage / 2);
					}
					Log.createLog(connection, attacker.getId(), defender.getForeign().getRegion(), LogType.LAND, 0);
					attacker.getArmy().setFortification(0);
					defender.getArmy().setFortification(0);
					attacker.update();
					defender.update();
					return losses;
				}
			}
		}
	}

	public static String entrench(Connection conn, Nation attacker, Nation defender) throws SQLException
	{
		if(!attacker.isAtWarWith(defender))
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.LAND))
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
			Log.createLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.LAND, 0);
			attacker.update();
			String news = News.createMessage(News.ID_FORTIFICATION, attacker.getNationUrl());
			News.sendNews(conn, attacker.getId(), defender.getId(), news);
			return Responses.fortified();
		}
	}

	public static String navyBattle(Connection conn, Nation attacker, Nation defender) throws SQLException
	{
		if(attacker.getOffensive() != defender.getId() && attacker.getDefensive() != defender.getId())
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.NAVY))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getNavalPower() <= 0)
		{
			return Responses.attackerNoNavy();
		}
		else if(defender.getNavalPower() <= 0)
		{
			return Responses.defenderNoNavy();
		}
		else
		{
			final double attackPower = attacker.getNavalPower();
			final double defensePower = defender.getNavalPower();
			int attackerBBLosses = (int)Math.ceil(defensePower * ((double)defender.getMilitary().getBattleships() / defender.getTotalShipCount()));
			int attackerPBLosses = (int)Math.ceil(defensePower * ((double)defender.getMilitary().getPreBattleships() / defender.getTotalShipCount()));
			int attackerCLLosses = (int)Math.ceil(defensePower * ((double)defender.getMilitary().getCruisers() / defender.getTotalShipCount()));
			int attackerDDLosses = (int)Math.ceil(defensePower * ((double)defender.getMilitary().getDestroyers() / defender.getTotalShipCount()));
			int attackerSSLosses = (int)Math.ceil(defensePower * ((double)defender.getMilitary().getSubmarines() / defender.getTotalShipCount()));
			int defenderBBLosses = (int)Math.ceil(attackPower * ((double)attacker.getMilitary().getBattleships() / attacker.getTotalShipCount()));
			int defenderPBLosses = (int)Math.ceil(attackPower * ((double)attacker.getMilitary().getPreBattleships() / attacker.getTotalShipCount()));
			int defenderCLLosses = (int)Math.ceil(attackPower * ((double)attacker.getMilitary().getCruisers() / attacker.getTotalShipCount()));
			int defenderDDLosses = (int)Math.ceil(attackPower * ((double)attacker.getMilitary().getDestroyers() / attacker.getTotalShipCount()));
			int defenderSSLosses = (int)Math.ceil(attackPower * ((double)attacker.getMilitary().getSubmarines() / attacker.getTotalShipCount()));
			for(int i = 0; i < 2; i++)
			{
				if(attackerBBLosses > attacker.getMilitary().getBattleships())
				{
					attackerPBLosses += attackerBBLosses - attacker.getMilitary().getBattleships();
					attackerBBLosses = attacker.getMilitary().getBattleships();
				}
				if(attackerPBLosses > attacker.getMilitary().getPreBattleships())
				{
					attackerCLLosses += attackerPBLosses - attacker.getMilitary().getPreBattleships();
					attackerPBLosses = attacker.getMilitary().getPreBattleships();
				}
				if(attackerCLLosses > attacker.getMilitary().getCruisers())
				{
					attackerDDLosses += attackerCLLosses - attacker.getMilitary().getCruisers();
					attackerCLLosses = attacker.getMilitary().getCruisers();
				}
				if(attackerDDLosses > attacker.getMilitary().getDestroyers())
				{
					attackerSSLosses += attackerDDLosses - attacker.getMilitary().getDestroyers();
					attackerDDLosses = attacker.getMilitary().getDestroyers();
				}
				if(attackerSSLosses > attacker.getMilitary().getSubmarines())
				{
					attackerBBLosses += attackerSSLosses - attacker.getMilitary().getSubmarines();
					attackerSSLosses = attacker.getMilitary().getSubmarines();
				}
				if(defenderBBLosses > defender.getMilitary().getBattleships())
				{
					defenderPBLosses += defenderBBLosses - defender.getMilitary().getBattleships();
					defenderBBLosses = defender.getMilitary().getBattleships();
				}
				if(defenderPBLosses > defender.getMilitary().getPreBattleships())
				{
					defenderCLLosses += defenderPBLosses - defender.getMilitary().getPreBattleships();
					defenderPBLosses = defender.getMilitary().getPreBattleships();
				}
				if(defenderCLLosses > defender.getMilitary().getCruisers())
				{
					defenderDDLosses += defenderCLLosses - defender.getMilitary().getCruisers();
					defenderCLLosses = defender.getMilitary().getCruisers();
				}
				if(defenderDDLosses > defender.getMilitary().getDestroyers())
				{
					defenderSSLosses += defenderDDLosses - defender.getMilitary().getDestroyers();
					defenderDDLosses = defender.getMilitary().getDestroyers();
				}
				if(defenderSSLosses > defender.getMilitary().getSubmarines())
				{
					defenderBBLosses += defenderSSLosses - defender.getMilitary().getSubmarines();
					defenderSSLosses = defender.getMilitary().getSubmarines();
				}
			}
			if(attackerBBLosses > attacker.getMilitary().getBattleships())
			{
				attackerBBLosses = attacker.getMilitary().getBattleships();
			}
			if(defenderBBLosses > defender.getMilitary().getBattleships())
			{
				defenderBBLosses = defender.getMilitary().getBattleships();
			}
			attacker.getMilitary().setBattleships(attacker.getMilitary().getBattleships() - attackerBBLosses);
			attacker.getMilitary().setPreBattleships(attacker.getMilitary().getPreBattleships() - attackerPBLosses);
			attacker.getMilitary().setCruisers(attacker.getMilitary().getCruisers() - attackerCLLosses);
			attacker.getMilitary().setDestroyers(attacker.getMilitary().getDestroyers() - attackerDDLosses);
			attacker.getMilitary().setSubmarines(attacker.getMilitary().getSubmarines() - attackerSSLosses);
			defender.getMilitary().setBattleships(defender.getMilitary().getBattleships() - defenderBBLosses);
			defender.getMilitary().setPreBattleships(defender.getMilitary().getPreBattleships() - defenderPBLosses);
			defender.getMilitary().setCruisers(defender.getMilitary().getCruisers() - defenderCLLosses);
			defender.getMilitary().setDestroyers(defender.getMilitary().getDestroyers() - defenderDDLosses);
			defender.getMilitary().setSubmarines(defender.getMilitary().getSubmarines() - defenderSSLosses);
			attacker.update();
			defender.update();
			String losses = Responses.navalBattle(attackerBBLosses, attackerPBLosses, attackerCLLosses, attackerDDLosses, attackerSSLosses,
					defenderBBLosses, defenderPBLosses, defenderCLLosses, defenderDDLosses, defenderSSLosses);
			String news = News.createMessage(News.ID_NAVAL_BATTLE, attackerBBLosses, attackerPBLosses, attackerCLLosses, attackerDDLosses,
					attackerSSLosses, defenderBBLosses, defenderPBLosses, defenderCLLosses, defenderDDLosses, defenderSSLosses);
			News.sendNews(conn, attacker.getId(), defender.getId(), news);
			Log.createLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.NAVY, 0);
			return losses;
		}
	}

	public static String navyBombard(Connection conn, Nation attacker, Nation defender) throws SQLException
	{
		if(attacker.getOffensive() != defender.getId() && attacker.getDefensive() != defender.getId())
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.NAVY))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getNavalPower() <= 0)
		{
			return Responses.attackerNoNavy();
		}
		else if (attacker.getNavalPower() <= defender.getNavalPower() / 2)
		{
			return navyBattle(conn, attacker, defender);
		}
		else
		{
			Set<Map.Entry<Integer, City>> cities = defender.getCities().getCities().entrySet();
			cities.removeIf((city) -> city.getValue().getDevastation() >= 100 && !city.getValue().isCoastal());
			if(cities.isEmpty())
			{
				return Responses.defenderAlreadyDevastated();
			}
			else
			{
				int damage = (int)(Math.random() * attacker.getNavalPower()) + 1;
				HashMap.Entry entry = (HashMap.Entry)cities.toArray()[(int)(Math.random() * cities.size())];
				City city = (City)entry.getValue();
				city.setDevastation(city.getDevastation() + damage);
				String news = News.createMessage(News.ID_NAVAL_BOMBARD, attacker.getNationUrl(), city.getName());
				News.sendNews(conn, attacker.getId(), defender.getId(), news);
				Log.createLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.NAVY, 0);
				attacker.update();
				defender.update();
				return Responses.bombard();
			}
		}
	}

	public static String airBattle(Connection conn, Nation attacker, Nation defender) throws SQLException
	{
		if(attacker.getOffensive() != defender.getId() && attacker.getDefensive() != defender.getId())
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getFighterPower() <= 0)
		{
			return Responses.attackerNoAirforce();
		}
		else if(defender.getFighterPower() <= 0)
		{
			return Responses.defenderNoAirforce();
		}
		else
		{
			int attackTotalLosses = (int)Math.sqrt(defender.getFighterPower());
			int defenseTotalLosses = (int)Math.sqrt(attacker.getFighterPower());
			HashMap<String, Integer> attackLosses = new HashMap<>();
			HashMap<String, Integer> defenseLosses = new HashMap<>();

			String[] planes = {"biplane_fighters", "triplane_fighters", "monoplane_fighters"};
			for(String string : planes)
			{
				int losses = (int)(Math.random() * (attackTotalLosses / 2));
				if(losses > (int)attacker.getMilitary().getByName(string))
				{
					losses = (Integer)attacker.getMilitary().getByName(string);
				}
				attackTotalLosses -= losses;
				attackLosses.put(string.substring(0, string.indexOf("_")).concat("s"), losses);
				attacker.getMilitary().setByName(string, (Integer) attacker.getMilitary().getByName(string) - losses);
				losses = (int)(Math.random() * (defenseTotalLosses / 2)) + (defenseTotalLosses / 2);
				if((int)defender.getMilitary().getByName(string) < losses)
				{
					losses = (Integer)defender.getMilitary().getByName(string);
				}
				defenseTotalLosses -= losses;
				defenseLosses.put(string.substring(0, string.indexOf("_")).concat("s"), losses);
				defender.getMilitary().setByName(string, (Integer) attacker.getMilitary().getByName(string) - losses);
			}
			attacker.update();
			defender.update();
			String attack = "";
			String defense = "";
			int i = 0;
			for(Map.Entry<String, Integer> entry : attackLosses.entrySet())
			{
				if(attackLosses.size() <= 2 && i == 0)
				{
					attack = entry.getValue() + " " + entry.getKey();
				}
				else if(attackLosses.size() >= 2 && i == attackLosses.size() - 1)
				{
					attack += " and " + entry.getValue() + " " + entry.getKey();
				}
				else
				{
					attack += entry.getValue() + " " + entry.getKey() + ", ";
				}
				i++;
			}
			for(Map.Entry<String, Integer> entry : defenseLosses.entrySet())
			{
				if(attackLosses.size() <= 2 && i == 0)
				{
					defense = entry.getValue() + " " + entry.getKey();
				}
				else if(attackLosses.size() >= 2 && i == attackLosses.size() - 1)
				{
					defense += " and " + entry.getValue() + " " + entry.getKey();
				}
				else
				{
					defense += entry.getValue() + " " + entry.getKey() + ", ";
				}
				i++;
			}
			String news = String.format(News.createMessage(News.ID_AIR_BATTLE),
					"<a href=\"nation.jsp?id=" + attacker.getId() + "\">" + attacker.getCosmetic().getNationName() + "</a>", attack, defense);
			News.sendNews(conn, attacker.getId(), defender.getId(), news);
			Log.createLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.NAVY, 0);
			return Responses.airBattle(attack, defense);
		}
	}

	public static String airBombard(Connection conn, Nation attacker, Nation defender) throws SQLException
	{
		if(attacker.getOffensive() != defender.getId() && attacker.getDefensive() != defender.getId())
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getBomberPower() <= 0)
		{
			return Responses.attackerNoAirforce();
		}
		else if(defender.getFighterPower() > 0 && defender.getFighterPower() / 2 >= attacker.getFighterPower())
		{
			return airBattle(conn, attacker, defender);
		}
		else
		{
			Set<Map.Entry<Integer, City>> cities = defender.getCities().getCities().entrySet();
			cities.removeIf((city) -> city.getValue().getDevastation() >= 100);
			if(cities.isEmpty())
			{
				return Responses.defenderAlreadyDevastated();
			}
			else
			{
				int damage = (int)(Math.random() * attacker.getBomberPower()) + 1;
				HashMap.Entry<Integer, City> entry = (HashMap.Entry<Integer, City>)cities.toArray()[(int)(Math.random() * cities.size())];
				City city = entry.getValue();
				city.setDevastation(city.getDevastation() + damage);
				String news = News.createMessage(News.ID_AIR_BOMBARD, attacker.getNationUrl(), city.getName());
				News.sendNews(conn, attacker.getId(), defender.getId(), news);
				Log.createLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.AIR, 0);
				attacker.update();
				defender.update();
				return Responses.bombard();
			}
		}
	}

	public static String airBombTroops(Connection conn, Nation attacker, Nation defender) throws SQLException
	{
		if(attacker.getOffensive() != defender.getId() && attacker.getDefensive() != defender.getId())
		{
			return Responses.noWar();
		}
		else if(Log.checkLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.AIR))
		{
			return Responses.alreadyAttacked();
		}
		else if(attacker.getBomberPower() <= 0)
		{
			return Responses.attackerNoAirforce();
		}
		else if(defender.getFighterPower() > 0 && defender.getFighterPower() / 2 >= attacker.getFighterPower())
		{
			return airBattle(conn, attacker, defender);
		}
		else
		{
			int damage = (int)Math.sqrt(attacker.getBomberPower());
			damage = Math.max(damage, 2);
			defender.getArmy().setSize(defender.getArmy().getSize() - damage);
			String news = News.createMessage(News.ID_AIR_BOMB_TROOPS, attacker.getNationUrl(), damage);
			News.sendNews(conn, attacker.getId(), defender.getId(), news);
			Log.createLog(conn, attacker.getId(), defender.getForeign().getRegion(), LogType.AIR, 0);
			attacker.update();
			defender.update();
			return Responses.bombTroops(damage);
		}
	}

	public static String sendPeace(Nation sender, Nation receiver) throws SQLException
	{
		if(sender.getOffensive() != receiver.getId() && sender.getDefensive() != receiver.getId())
		{
			return Responses.noWar();
		}
		else
		{
			return sender.sendPeace(receiver);
		}
	}
}
