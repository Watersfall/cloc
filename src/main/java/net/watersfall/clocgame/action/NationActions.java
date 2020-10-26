package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.MessageDao;
import net.watersfall.clocgame.dao.NewsDao;
import net.watersfall.clocgame.model.SpamAction;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.news.News;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Util;
import org.json.JSONObject;

import java.sql.SQLException;

public class NationActions
{
	public static String defaultResourceCheck(double amount, Nation sender, Nation receiver) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(sender.getId() == receiver.getId())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
			return object.toString();
		}
		else if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_RESOURCE, sender.getId(), sender.getConn()))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noSpam());
			return object.toString();
		}
		else if(amount <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.negative());
			return object.toString();
		}
		else if(amount > 999999999998.00)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.negative());
			return object.toString();
		}
		else
		{
			return null;
		}
	}

	public static String sendIron(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		JSONObject object = new JSONObject();
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getStats().getIron())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else
		{
			sender.getStats().setIron(sender.getStats().getIron() - amount);
			receiver.getStats().setIron(receiver.getStats().getIron() + amount);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
			object.put(JsonFields.IRON_SHORT.name(), Util.formatDisplayNumber(sender.getStats().getIron()));
			object.put(JsonFields.IRON.name(), Util.formatNumber(sender.getStats().getIron()));
		}
		return object.toString();
	}

	public static String sendCoal(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		JSONObject object = new JSONObject();
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getStats().getCoal())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else
		{
			sender.getStats().setCoal(sender.getStats().getCoal() - amount);
			receiver.getStats().setCoal(receiver.getStats().getCoal() + amount);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
			object.put(JsonFields.COAL_SHORT.name(), Util.formatDisplayNumber(sender.getStats().getCoal()));
			object.put(JsonFields.COAL.name(), Util.formatNumber(sender.getStats().getCoal()));
		}
		return object.toString();
	}

	public static String sendSteel(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		JSONObject object = new JSONObject();
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getStats().getSteel())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else
		{
			sender.getStats().setSteel(sender.getStats().getSteel() - amount);
			receiver.getStats().setSteel(receiver.getStats().getSteel() + amount);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
			object.put(JsonFields.STEEL_SHORT.name(), Util.formatDisplayNumber(sender.getStats().getSteel()));
			object.put(JsonFields.STEEL.name(), Util.formatNumber(sender.getStats().getSteel()));
		}
		return object.toString();
	}

	public static String sendOil(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		JSONObject object = new JSONObject();
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getStats().getOil())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else
		{
			sender.getStats().setOil(sender.getStats().getOil() - amount);
			receiver.getStats().setOil(receiver.getStats().getOil() + amount);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
			object.put(JsonFields.OIL_SHORT.name(), Util.formatDisplayNumber(sender.getStats().getOil()));
			object.put(JsonFields.OIL.name(), Util.formatNumber(sender.getStats().getOil()));
		}
		return object.toString();
	}

	public static String sendNitrogen(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		JSONObject object = new JSONObject();
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getStats().getNitrogen())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else
		{
			sender.getStats().setNitrogen(sender.getStats().getNitrogen() - amount);
			receiver.getStats().setNitrogen(receiver.getStats().getNitrogen() + amount);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
			object.put(JsonFields.NITROGEN_SHORT.name(), Util.formatDisplayNumber(sender.getStats().getNitrogen()));
			object.put(JsonFields.NITROGEN.name(), Util.formatNumber(sender.getStats().getNitrogen()));
		}
		return object.toString();
	}

	public static String sendMoney(double amount, Nation sender, Nation receiver) throws SQLException
	{
		String check = defaultResourceCheck(amount, sender, receiver);
		JSONObject object = new JSONObject();
		if(check != null)
		{
			return check;
		}
		else if(amount > sender.getStats().getBudget())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else
		{
			sender.getStats().setBudget(sender.getStats().getBudget() - amount);
			receiver.getStats().setBudget(receiver.getStats().getBudget() + amount);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(sender.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(sender.getStats().getBudget()));
		}
		return object.toString();
	}

	public static String declareWar(Nation sender, Nation receiver, String name) throws SQLException
	{
		JSONObject object = new JSONObject();
		String message = sender.canDeclareWar(receiver);
		if(message != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), message);
		}
		else
		{
			NewsDao dao = new NewsDao(sender.getConn(), true);
			dao.createNews(sender.getId(), receiver.getId(), News.createMessage(News.ID_DECLARE_WAR, sender.getNationUrl()));
			sender.declareWar(receiver, name);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.war());
		}
		return object.toString();
	}

	public static String sendMessage(Nation sender, Nation receiver, String message) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(sender.getId() == receiver.getId())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(message.length() > 2048)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.tooLong());
		}
		else if(message.length() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(Util.checkSpamAndInsertIfNot(SpamAction.MESSAGE, sender.getId(), sender.getConn()))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noSpam());
		}
		else
		{
			MessageDao dao = new MessageDao(sender.getConn(), true);
			dao.createMessage(sender.getId(), receiver.getId(), message);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
		}
		return object.toString();
	}

	public static String sendEquipment(Nation sender, Nation receiver, Producibles equipment, int amount) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(sender.getId() == receiver.getId())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(amount <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.negative());
		}
		else if(sender.getProducibles().getProducible(equipment) < amount)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_RESOURCE, sender.getId(), sender.getConn()))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noSpam());
		}
		else
		{
			sender.getProducibles().setProducible(equipment, sender.getProducibles().getProducible(equipment) - amount);
			receiver.getProducibles().setProducible(equipment, receiver.getProducibles().getProducible(equipment) + amount);
			sender.fixCurrentPlanes();
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
		}
		return object.toString();
	}
}
