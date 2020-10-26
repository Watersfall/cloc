package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.ArmyDao;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.military.army.Army;
import net.watersfall.clocgame.model.military.army.BattalionType;
import net.watersfall.clocgame.model.military.army.Priority;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Security;
import org.json.JSONObject;

import java.sql.SQLException;

public class ArmyActions
{
	public static String addBattalion(ArmyDao dao, Army army, BattalionType type) throws SQLException
	{
		dao.createBattalion(army.getId(), type);
		JSONObject object = new JSONObject();
		object.put(JsonFields.SUCCESS.name(), true);
		object.put(JsonFields.MESSAGE.name(), Responses.created());
		return object.toString();
	}

	public static String removeBattalion(ArmyDao dao, Army army, long id) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(army.getBattalions().size() <= 1)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else if(army.getBattalionById(id) == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else
		{
			dao.deleteBattalion(id);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.deleted());
		}
		return object.toString();
	}

	public static String setPriority(Army army, Priority priority)
	{
		army.setField("priority", priority.name());
		JSONObject object = new JSONObject();
		object.put(JsonFields.SUCCESS.name(), true);
		object.put(JsonFields.MESSAGE.name(), Responses.updated());
		return object.toString();
	}

	public static String createArmy(Nation nation) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(!nation.canCreateNewArmy())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else
		{
			ArmyDao dao = new ArmyDao(nation.getConn(), true);
			dao.createArmy(nation.getId());
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.created());
		}
		return object.toString();
	}

	public static String deleteArmy(Nation nation, Army army) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(nation.getArmies().size() <= 1)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else
		{
			ArmyDao dao = new ArmyDao(nation.getConn(), true);
			dao.deleteArmy(army.getId());
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.deleted());
		}
		return object.toString();
	}

	public static String rename(Army army, String name)
	{
		JSONObject object = new JSONObject();
		if(name.length() > 64)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.tooLong("name", 64));
		}
		else
		{
			name = Security.sanitize(name);
			army.setField("name", name);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.updated());
			object.put(JsonFields.ARMY_NAME.name() + "_" + army.getId(), name);
		}
		return object.toString();
	}
}
