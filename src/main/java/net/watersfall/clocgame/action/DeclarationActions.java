package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.DeclarationDao;
import net.watersfall.clocgame.model.SpamAction;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Util;

import java.sql.SQLException;

public class DeclarationActions
{
	public static String postDeclaration(Nation nation, String message) throws SQLException
	{
		int cost = 100;
		if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_DECLARATION, nation.getId(), nation.getConn()))
		{
			return Responses.noSpam();
		}
		else if(nation.getStats().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(message.length() > 2048)
		{
			return Responses.tooLong("Message", 2048);
		}
		else if(message.length() < 1)
		{
			return Responses.nullFields();
		}
		else
		{
			new DeclarationDao(nation.getConn(), true).createDeclaration(nation, message);
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.declaration();
		}
	}
}