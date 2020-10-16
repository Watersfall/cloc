package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.EventDao;
import com.watersfall.clocgame.dao.ModifierDao;
import com.watersfall.clocgame.model.event.Event;
import com.watersfall.clocgame.model.modifier.Modifiers;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;

public class EventActions
{

	public static class Strike
	{
		public static String giveIn(Nation nation, Event event) throws SQLException
		{
			if(event.getOwner() != nation.getId())
			{
				return Responses.noEvent();
			}
			else
			{
				EventDao eventDao = new EventDao(nation.getConn(), true);
				eventDao.deleteEventById(event.getId());
				ModifierDao modifierDao = new ModifierDao(nation.getConn(), true);
				modifierDao.createModifier(nation.getId(), event.getCityId(), Modifiers.STRIKE_GAVE_IN);
				return Responses.strikeGiveIn();
			}
		}

		public static String ignore(Nation nation, Event event) throws SQLException
		{
			if(event.getOwner() != nation.getId())
			{
				return Responses.noEvent();
			}
			else
			{
				EventDao eventDao = new EventDao(nation.getConn(), true);
				eventDao.deleteEventById(event.getId());
				ModifierDao modifierDao = new ModifierDao(nation.getConn(), true);
				modifierDao.createModifier(nation.getId(), event.getCityId(), Modifiers.STRIKE_IGNORED);
				return Responses.strikeIgnore();
			}
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
