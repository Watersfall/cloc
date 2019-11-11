package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.News;

import javax.servlet.http.HttpServletRequest;
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
			String message = News.createMessage(News.ID_SEND_COAL)
					.replace("%SENDER%", "<a href=\"nation.jsp?id=" + sender.getId() + "\">" + sender.getCosmetic().getNationName() + "</a>")
					.replace("%AMOUNT%", Integer.toString(amount));
			News.sendNews(sender.getConnection(), sender.getId(), receiver.getId(), message);
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
			String message = News.createMessage(News.ID_SEND_IRON)
					.replace("%SENDER%", "<a href=\"nation.jsp?id=" + sender.getId() + "\">" + sender.getCosmetic().getNationName() + "</a>")
					.replace("%AMOUNT%", Integer.toString(amount));
			News.sendNews(sender.getConnection(), sender.getId(), receiver.getId(), message);
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
			String message = News.createMessage(News.ID_SEND_OIL)
					.replace("%SENDER%", "<a href=\"nation.jsp?id=" + sender.getId() + "\">" + sender.getCosmetic().getNationName() + "</a>")
					.replace("%AMOUNT%", Integer.toString(amount));
			News.sendNews(sender.getConnection(), sender.getId(), receiver.getId(), message);
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
			String message = News.createMessage(News.ID_SEND_STEEL)
					.replace("%SENDER%", "<a href=\"nation.jsp?id=" + sender.getId() + "\">" + sender.getCosmetic().getNationName() + "</a>")
					.replace("%AMOUNT%", Integer.toString(amount));
			News.sendNews(sender.getConnection(), sender.getId(), receiver.getId(), message);
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
			String message = News.createMessage(News.ID_SEND_NITROGEN)
					.replace("%SENDER%", "<a href=\"nation.jsp?id=" + sender.getId() + "\">" + sender.getCosmetic().getNationName() + "</a>")
					.replace("%AMOUNT%", Integer.toString(amount));
			News.sendNews(sender.getConnection(), sender.getId(), receiver.getId(), message);
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
			String message = News.createMessage(News.ID_SEND_MONEY)
					.replace("%SENDER%", "<a href=\"nation.jsp?id=" + sender.getId() + "\">" + sender.getCosmetic().getNationName() + "</a>")
					.replace("%AMOUNT%", Integer.toString(amount));
			News.sendNews(sender.getConnection(), sender.getId(), receiver.getId(), message);
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
			String newsMessage = News.createMessage(News.ID_DECLARE_WAR)
					.replace("%SENDER%", "<a href=\"nation.jsp?id=" + sender.getId() + "\">" + sender.getCosmetic().getNationName() + "</a>");
			News.sendNews(sender.getConnection(), sender.getId(), receiver.getId(), newsMessage);
			sender.declareWar(receiver);
			return Responses.war();
		}
	}
}
