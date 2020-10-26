package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.DeclarationDao;
import net.watersfall.clocgame.model.SpamAction;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Util;
import org.json.JSONObject;

import java.sql.SQLException;

public class DeclarationActions
{
	public static String postDeclaration(Nation nation, String message) throws SQLException
	{
		JSONObject object = new JSONObject();
		int cost = 100;
		if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_DECLARATION, nation.getId(), nation.getConn()))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noSpam());
		}
		else if(nation.getStats().getBudget() < cost)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
		}
		else if(message.length() > 2048)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.tooLong("message", 2048));
		}
		else if(message.length() < 1)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.nullFields());
		}
		else
		{
			new DeclarationDao(nation.getConn(), true).createDeclaration(nation, message);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.declaration());
			object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
			object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
		}
		return object.toString();
	}
}
