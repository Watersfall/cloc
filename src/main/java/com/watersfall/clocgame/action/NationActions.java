package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.NewsDao;
import com.watersfall.clocgame.model.SpamAction;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.News;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Util;

import java.sql.SQLException;

public class NationActions
{
	public static String defaultResourceCheck(double amount, Nation sender, Nation receiver) throws SQLException
	{
		if(sender.getId() == receiver.getId())
		{
			return Responses.genericError();
		}
		else if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_RESOURCE, sender.getId(), sender.getConn()))
		{
			return Responses.noSpam();
		}
		else if(amount <= 0)
		{
			return Responses.negative();
		}
		else if(amount > 999999999998.00)
		{
			return Responses.negative();
		}
		else
		{
			return null;
		}
	}

	public static String sendIron(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getEconomy().getIron())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setIron(sender.getEconomy().getIron() - amount);
			receiver.getEconomy().setIron(receiver.getEconomy().getIron() + amount);
			return Responses.sent();
		}
	}

	public static String sendCoal(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getEconomy().getCoal())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setCoal(sender.getEconomy().getCoal() - amount);
			receiver.getEconomy().setCoal(receiver.getEconomy().getCoal() + amount);
			return Responses.sent();
		}
	}

	public static String sendSteel(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getEconomy().getSteel())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setSteel(sender.getEconomy().getSteel() - amount);
			receiver.getEconomy().setSteel(receiver.getEconomy().getSteel() + amount);
			return Responses.sent();
		}
	}

	public static String sendOil(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getEconomy().getOil())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setOil(sender.getEconomy().getOil() - amount);
			receiver.getEconomy().setOil(receiver.getEconomy().getOil() + amount);
			return Responses.sent();
		}
	}

	public static String sendNitrogen(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getEconomy().getNitrogen())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setNitrogen(sender.getEconomy().getNitrogen() - amount);
			receiver.getEconomy().setNitrogen(receiver.getEconomy().getNitrogen() + amount);
			return Responses.sent();
		}
	}

	public static String sendMoney(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getEconomy().getBudget())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getEconomy().setBudget(sender.getEconomy().getBudget() - amount);
			receiver.getEconomy().setBudget(receiver.getEconomy().getBudget() + amount);
			return Responses.sent();
		}
	}

	public static String declareWar(Nation sender, Nation receiver, String name) throws SQLException
	{
		String message = sender.canDeclareWar(receiver);
		if(message != null)
		{
			return message;
		}
		else
		{
			NewsDao dao = new NewsDao(sender.getConn(), true);
			dao.createNews(sender.getId(), receiver.getId(), News.createMessage(News.ID_DECLARE_WAR, sender.getNationUrl()));
			sender.declareWar(receiver, name);
			return Responses.war();
		}
	}
}
