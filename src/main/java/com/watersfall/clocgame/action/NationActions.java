package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.LogType;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.war.Log;
import com.watersfall.clocgame.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NationActions
{
	public static String sendCoal(Nation sender, Nation receiver, HttpServletRequest req) throws SQLException, NumberFormatException
	{
		if(req.getParameter("amount") == null)
		{
			return Responses.genericError();
		}
		int amount = Integer.parseInt(req.getParameter("amount"));
		if(amount <= 0)
		{
			return Responses.negative();
		}
		else if(amount > sender.getEconomy().getCoal())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setCoal(sender.getEconomy().getCoal() - amount);
			receiver.getEconomy().setCoal(receiver.getEconomy().getCoal() + amount);
			sender.update();
			receiver.update();
			return Responses.sent();
		}
	}

	public static String sendIron(Nation sender, Nation receiver, HttpServletRequest req) throws SQLException, NumberFormatException
	{
		if(req.getParameter("amount") == null)
		{
			return Responses.genericError();
		}
		int amount = Integer.parseInt(req.getParameter("amount"));
		if(amount <= 0)
		{
			return Responses.negative();
		}
		else if(amount > sender.getEconomy().getIron())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setIron(sender.getEconomy().getIron() - amount);
			receiver.getEconomy().setIron(receiver.getEconomy().getIron() + amount);
			sender.update();
			receiver.update();
			return Responses.sent();
		}
	}

	public static String sendOil(Nation sender, Nation receiver, HttpServletRequest req) throws SQLException, NumberFormatException
	{
		if(req.getParameter("amount") == null)
		{
			return Responses.genericError();
		}
		int amount = Integer.parseInt(req.getParameter("amount"));
		if(amount <= 0)
		{
			return Responses.negative();
		}
		else if(amount > sender.getEconomy().getOil())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setOil(sender.getEconomy().getOil() - amount);
			receiver.getEconomy().setOil(receiver.getEconomy().getOil() + amount);
			sender.update();
			receiver.update();
			return Responses.sent();
		}
	}

	public static String sendSteel(Nation sender, Nation receiver, HttpServletRequest req) throws SQLException, NumberFormatException
	{
		if(req.getParameter("amount") == null)
		{
			return Responses.genericError();
		}
		int amount = Integer.parseInt(req.getParameter("amount"));
		if(amount <= 0)
		{
			return Responses.negative();
		}
		else if(amount > sender.getEconomy().getSteel())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setSteel(sender.getEconomy().getSteel() - amount);
			receiver.getEconomy().setSteel(receiver.getEconomy().getSteel() + amount);
			sender.update();
			receiver.update();
			return Responses.sent();
		}
	}

	public static String sendNitrogen(Nation sender, Nation receiver, HttpServletRequest req) throws SQLException, NumberFormatException
	{
		if(req.getParameter("amount") == null)
		{
			return Responses.genericError();
		}
		int amount = Integer.parseInt(req.getParameter("amount"));
		if(amount <= 0)
		{
			return Responses.negative();
		}
		else if(amount > sender.getEconomy().getNitrogen())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setNitrogen(sender.getEconomy().getNitrogen() - amount);
			receiver.getEconomy().setNitrogen(receiver.getEconomy().getNitrogen() + amount);
			sender.update();
			receiver.update();
			return Responses.sent();
		}
	}

	public static String sendMoney(Nation sender, Nation receiver, HttpServletRequest req) throws SQLException, NumberFormatException
	{
		if(req.getParameter("amount") == null)
		{
			return Responses.genericError();
		}
		int amount = Integer.parseInt(req.getParameter("amount"));
		if(amount <= 0)
		{
			return Responses.negative();
		}
		else if(amount > sender.getEconomy().getBudget())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setBudget(sender.getEconomy().getBudget() - amount);
			receiver.getEconomy().setBudget(receiver.getEconomy().getBudget() + amount);
			sender.update();
			receiver.update();
			return Responses.sent();
		}
	}

	public static String declareWar(Nation sender, Nation receiver, HttpServletRequest req) throws SQLException
	{
		String message = sender.canDeclareWar(receiver);
		if(message != null)
		{
			return message;
		}
		else
		{
			sender.declareWar(receiver);
			return Responses.war();
		}
	}

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
				updateWar.setInt(1, Util.turn);
				updateWar.setInt(2, attacker.getId());
				updateWar.setInt(3, attacker.getId());
				updateWar.setInt(4, defender.getId());
				updateWar.execute();
				defender.getMilitary().setWarProtection(4);
				return Responses.warWon();
			}
			else
			{
				String losses;
				int attackLosses = attacker.getAttackingCasualties(defender);
				int defenderLosses = defender.getDefendingCasualties(attacker);
				attacker.getArmy().setSize(attacker.getArmy().getSize() - attackLosses);
				attacker.getDomestic().setManpowerLost(attacker.getDomestic().getManpowerLost() + attackLosses * 1000);
				defender.getArmy().setSize(defender.getArmy().getSize() - defenderLosses);
				defender.getDomestic().setManpowerLost(defender.getDomestic().getManpowerLost() + defenderLosses * 1000);
				attacker.update();
				defender.update();
				if(attacker.getPower() > defender.getPower())
				{
					losses =  Responses.offensiveVictory(attackLosses, defenderLosses);
				}
				else
				{
					losses = Responses.offensiveDefeat(attackLosses, defenderLosses);
				}
				Log.createLog(connection, attacker.getId(), defender.getForeign().getRegion(), LogType.LAND, 0);
				return losses;
			}
		}
	}
}
