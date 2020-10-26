package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.EventDao;
import net.watersfall.clocgame.dao.ModifierDao;
import net.watersfall.clocgame.model.event.Event;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.modifier.Modifiers;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import org.json.JSONObject;

import java.sql.SQLException;

public class EventActions
{

	public static class Strike
	{
		public static String giveIn(Nation nation, Event event) throws SQLException
		{
			JSONObject object = new JSONObject();
			if(event.getOwner() != nation.getId())
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noEvent());
			}
			else
			{
				EventDao eventDao = new EventDao(nation.getConn(), true);
				eventDao.deleteEventById(event.getId());
				ModifierDao modifierDao = new ModifierDao(nation.getConn(), true);
				modifierDao.createModifier(nation.getId(), event.getCityId(), Modifiers.STRIKE_GAVE_IN);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.strikeGiveIn());
			}
			return object.toString();
		}

		public static String ignore(Nation nation, Event event) throws SQLException
		{
			JSONObject object = new JSONObject();
			if(event.getOwner() != nation.getId())
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noEvent());
			}
			else
			{
				EventDao eventDao = new EventDao(nation.getConn(), true);
				eventDao.deleteEventById(event.getId());
				ModifierDao modifierDao = new ModifierDao(nation.getConn(), true);
				modifierDao.createModifier(nation.getId(), event.getCityId(), Modifiers.STRIKE_IGNORED);
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.strikeIgnore());
			}
			return object.toString();
		}

		public static String sendArmy(Nation nation, Event event) throws SQLException
		{/*
			if(event.getOwner() != nation.getId())
			{
				return Responses.noEvent();
			}
			else if(nation.getStats().getArmySize() < 5)
			{
				return Responses.noTroopsForAttack();
			}
			else
			{
				EventDao eventDao = new EventDao(nation.getConn(), true);
				eventDao.deleteEventById(event.getId());
				ModifierDao modifierDao = new ModifierDao(nation.getConn(), true);
				modifierDao.createModifier(nation.getId(), event.getCityId(), Modifiers.STRIKE_SENT_ARMY);
				nation.getStats().setApproval(nation.getStats().getApproval() - 50);
				if(Math.random() > 0.5)
				{
					int casualties = (int)(1 + Math.random() * 5);
					nation.getStats().setArmySize(nation.getStats().getArmySize() - casualties);
					nation.getStats().setCasualties(nation.getStats().getCasualties() + casualties);
					return Responses.strikeSendArmyCasualties(casualties);
				}
				return Responses.strikeSendArmyNoCasualties();
			}*/
			return null;
		}
	}
}
