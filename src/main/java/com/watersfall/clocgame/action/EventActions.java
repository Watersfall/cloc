package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.EventDao;
import com.watersfall.clocgame.dao.ModifierDao;
import com.watersfall.clocgame.model.nation.Events;
import com.watersfall.clocgame.model.nation.Modifiers;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;

public class EventActions
{
	public enum Action
	{
		STRIKE_GIVE_IN,
		STRIKE_IGNORE,
		STRIKE_SEND_ARMY
	}

	public static class Strike
	{
		public static String giveIn(Nation nation, Events event) throws SQLException
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

		public static String ignore(Nation nation, Events event) throws SQLException
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

		public static String sendArmy(Nation nation, Events event) throws SQLException
		{
			if(event.getOwner() != nation.getId())
			{
				return Responses.noEvent();
			}
			else if(nation.getArmy().getSize() < 5)
			{
				return Responses.noTroopsForAttack();
			}
			else
			{
				EventDao eventDao = new EventDao(nation.getConn(), true);
				eventDao.deleteEventById(event.getId());
				ModifierDao modifierDao = new ModifierDao(nation.getConn(), true);
				modifierDao.createModifier(nation.getId(), event.getCityId(), Modifiers.STRIKE_SENT_ARMY);
				nation.getDomestic().setApproval(nation.getDomestic().getApproval() - 50);
				if(Math.random() > 0.5)
				{
					int casualties = (int)(1 + Math.random() * 5);
					nation.getArmy().setSize(nation.getArmy().getSize() - casualties);
					return Responses.strikeSendArmyCasualties(casualties);
				}
				return Responses.strikeSendArmyNoCasualties();
			}
		}
	}
}
