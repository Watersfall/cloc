package com.watersfall.clocgame.action;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.News;
import com.watersfall.clocgame.text.Responses;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class NationActions
{
	public static String sendResource(String resource, double amount, Nation sender, Nation receiver) throws SQLException
	{
		if(amount <= 0)
		{
			return Responses.negative();
		}
		if(amount > 999999999998.00)
		{
			throw new NumberFormatException();
		}
		else if((Double)sender.getEconomy().getByName(resource) < amount)
		{
			return Responses.notEnough();
		}
		else if(sender.getId() == receiver.getId())
		{
			return Responses.genericError();
		}
		else
		{
			sender.getEconomy().setByName(resource, (Double)sender.getEconomy().getByName(resource) - amount);
			receiver.getEconomy().setByName(resource, (Double)receiver.getEconomy().getByName(resource) + amount);
			if(resource.equalsIgnoreCase("budget"))
			{
				News.sendNews(sender.getConn(), sender.getId(), receiver.getId(),
						News.createMessage(News.ID_SEND_MONEY, sender.getNationUrl(), Double.toString(amount)));
			}
			else
			{
				News.sendNews(sender.getConn(), sender.getId(), receiver.getId(),
						News.createMessage(News.ID_SEND_RESOURCE, sender.getNationUrl(), Double.toString(amount), resource));
			}
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
			News.sendNews(sender.getConn(), sender.getId(), receiver.getId(),
					News.createMessage(News.ID_DECLARE_WAR, sender.getNationUrl()));
			sender.declareWar(receiver);
			return Responses.war();
		}
	}
}
