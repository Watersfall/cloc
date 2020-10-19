package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.ArmyDao;
import net.watersfall.clocgame.model.military.army.Army;
import net.watersfall.clocgame.model.military.army.BattalionType;
import net.watersfall.clocgame.model.military.army.Priority;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Security;

import java.sql.SQLException;

public class ArmyActions
{
	public static String addBattalion(ArmyDao dao, Army army, BattalionType type) throws SQLException
	{
		dao.createBattalion(army.getId(), type);
		return Responses.created();
	}

	public static String removeBattalion(ArmyDao dao, Army army, long id) throws SQLException
	{
		if(army.getBattalions().size() <= 1)
		{
			return Responses.notEnough();
		}
		else if(army.getBattalionById(id) == null)
		{
			return Responses.notEnough();
		}
		else
		{
			dao.deleteBattalion(id);
			return Responses.deleted();
		}
	}

	public static String setPriority(Army army, Priority priority)
	{
		army.setField("priority", priority.name());
		return Responses.updated();
	}

	public static String createArmy(Nation nation) throws SQLException
	{
		if(!nation.canCreateNewArmy())
		{
			return Responses.genericError();
		}
		else
		{
			ArmyDao dao = new ArmyDao(nation.getConn(), true);
			dao.createArmy(nation.getId());
			return Responses.deleted();
		}
	}

	public static String deleteArmy(Nation nation, Army army) throws SQLException
	{
		if(nation.getArmies().size() <= 1)
		{
			return Responses.genericError();
		}
		else
		{
			ArmyDao dao = new ArmyDao(nation.getConn(), true);
			dao.deleteArmy(army.getId());
			return Responses.deleted();
		}
	}

	public static String rename(Army army, String name)
	{
		if(name.length() > 64)
		{
			return Responses.tooLong("name", 64);
		}
		else
		{
			name = Security.sanitize(name);
			army.setField("name", name);
			return Responses.updated();
		}
	}
}
