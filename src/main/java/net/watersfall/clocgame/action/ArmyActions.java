package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.ArmyDao;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.military.army.Army;
import net.watersfall.clocgame.model.military.army.Battalion;
import net.watersfall.clocgame.model.military.army.BattalionType;
import net.watersfall.clocgame.model.military.army.Priority;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Security;
import net.watersfall.clocgame.util.Util;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;

public class ArmyActions
{
	public static String addBattalion(Nation nation, ArmyDao dao, Army army, BattalionType type) throws SQLException
	{
		long id = dao.createBattalion(army.getId(), type);
		JSONObject object = new JSONObject();
		object.put(JsonFields.SUCCESS.name(), true);
		object.put(JsonFields.MESSAGE.name(), Responses.created());
		object.put(JsonFields.ARMY_ID.name(), army.getId());
		object.put(JsonFields.BATTALION_MAX_SIZE.name(), Util.formatNumber(type.getSize()));
		object.put(JsonFields.BATTALION_TYPE.name(), type.name());
		object.put(JsonFields.BATTALION_ID.name(), id);
		army.getBattalions().add(Battalion.getDefaultBattalion(id, army.getId(), type));
		nation.getEquipmentUpgrades();
		nation.getArmyEquipmentChange();
		for(HashMap.Entry<ProducibleCategory, Integer> entry : army.getNeededEquipment().entrySet())
		{
			object.put(JsonFields.ARMY_EQUIPMENT_TOTAL.name() + "_" + entry.getKey().name(), Util.formatNumber(entry.getValue()));
			if(nation.getEquipmentUpgradesByCategory().get(army) != null)
			{
				object.put(JsonFields.ARMY_EQUIPMENT_UPGRADE.name() + "_" + entry.getKey().name(), Util.formatNumber(nation.getEquipmentUpgradesByCategory().get(army).getOrDefault(entry.getKey(), 0)));
			}
			else
			{
				object.put(JsonFields.ARMY_EQUIPMENT_UPGRADE.name() + "_" + entry.getKey().name(), 0);
			}
			if(nation.getArmyEquipmentChange().get(army) != null)
			{
				object.put(JsonFields.ARMY_EQUIPMENT_CHANGE.name() + "_" + entry.getKey().name(), Util.formatNumber(nation.getArmyEquipmentChange().get(army).getOrDefault(entry.getKey(), 0)));
			}
			else
			{
				object.put(JsonFields.ARMY_EQUIPMENT_CHANGE.name() + "_" + entry.getKey().name(), 0);
			}
		}
		object.put(JsonFields.ARMY_MAX_SIZE.name(), army.getMaxSize());
		object.put(JsonFields.ARMY_SIZE_CHANGE.name(), nation.getArmyManpowerChange().get(army));
		return object.toString();
	}

	public static String removeBattalion(Nation nation, ArmyDao dao, Army army, long id) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(army.getBattalions().size() <= 1)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else if(army.getBattalionById(id) == null)
		{
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else
		{
			dao.deleteBattalion(id);
			Battalion battalion = army.getBattalionById(id);
			army.getBattalions().remove(battalion);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.deleted());object.put(JsonFields.ARMY_ID.name(), army.getId());
			nation.getEquipmentUpgrades();
			nation.getArmyEquipmentChange();
			for(HashMap.Entry<ProducibleCategory, Integer> entry : army.getNeededEquipment().entrySet())
			{
				object.put(JsonFields.ARMY_EQUIPMENT_TOTAL.name() + "_" + entry.getKey().name(), Util.formatNumber(entry.getValue()));
				if(nation.getEquipmentUpgradesByCategory().get(army) != null)
				{
					object.put(JsonFields.ARMY_EQUIPMENT_UPGRADE.name() + "_" + entry.getKey().name(), Util.formatNumber(nation.getEquipmentUpgradesByCategory().get(army).getOrDefault(entry.getKey(), 0)));
				}
				else
				{
					object.put(JsonFields.ARMY_EQUIPMENT_UPGRADE.name() + "_" + entry.getKey().name(), 0);
				}
				if(nation.getArmyEquipmentChange().get(army) != null)
				{
					object.put(JsonFields.ARMY_EQUIPMENT_CHANGE.name() + "_" + entry.getKey().name(), Util.formatNumber(nation.getArmyEquipmentChange().get(army).getOrDefault(entry.getKey(), 0)));
				}
				else
				{
					object.put(JsonFields.ARMY_EQUIPMENT_CHANGE.name() + "_" + entry.getKey().name(), 0);
				}
			}
			object.put(JsonFields.ARMY_SIZE.name(), army.getSize());
			object.put(JsonFields.ARMY_MAX_SIZE.name(), army.getMaxSize());
			object.put(JsonFields.ARMY_SIZE_CHANGE.name(), nation.getArmyManpowerChange().get(army));
		}
		return object.toString();
	}

	public static String setPriority(Nation nation, Army army, Priority priority)
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
			long id = dao.createArmy(nation.getId());
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.created());
			object.put(JsonFields.ARMY_ID.name(), id);
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
			object.put(JsonFields.SUCCESS.name(), true);
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
