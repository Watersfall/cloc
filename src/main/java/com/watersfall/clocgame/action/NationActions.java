package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.NewsDao;
import com.watersfall.clocgame.model.SpamAction;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.news.News;
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
		else if(amount > sender.getStats().getIron())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getStats().setIron(sender.getStats().getIron() - amount);
			receiver.getStats().setIron(receiver.getStats().getIron() + amount);
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
		else if(amount > sender.getStats().getCoal())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getStats().setCoal(sender.getStats().getCoal() - amount);
			receiver.getStats().setCoal(receiver.getStats().getCoal() + amount);
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
		else if(amount > sender.getStats().getSteel())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getStats().setSteel(sender.getStats().getSteel() - amount);
			receiver.getStats().setSteel(receiver.getStats().getSteel() + amount);
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
		else if(amount > sender.getStats().getOil())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getStats().setOil(sender.getStats().getOil() - amount);
			receiver.getStats().setOil(receiver.getStats().getOil() + amount);
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
		else if(amount > sender.getStats().getNitrogen())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getStats().setNitrogen(sender.getStats().getNitrogen() - amount);
			receiver.getStats().setNitrogen(receiver.getStats().getNitrogen() + amount);
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
		else if(amount > sender.getStats().getBudget())
		{
			return Responses.notEnough();
		}
		else
		{
			sender.getStats().setBudget(sender.getStats().getBudget() - amount);
			receiver.getStats().setBudget(receiver.getStats().getBudget() + amount);
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
