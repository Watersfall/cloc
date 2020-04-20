package com.watersfall.clocgame.action;

import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Events;
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
				Events.deleteEventById(nation.getConn(), event.getId());
				City city = nation.getCities().getCities().get(event.getCityId());
				city.setStrikeLength(8 + (int)(Math.random() * 4));
				city.setStrikeLevel(2);
				nation.getDomestic().setApproval(nation.getDomestic().getApproval() + 5);
				nation.update();
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
				Events.deleteEventById(nation.getConn(), event.getId());
				City city = nation.getCities().getCities().get(event.getCityId());
				city.setStrikeLength(4 + (int)(Math.random() * 6));
				city.setStrikeLevel(3 + (int)(Math.random() * 3));
				nation.getDomestic().setStability(nation.getDomestic().getStability() + 5);
				nation.update();
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
				Events.deleteEventById(nation.getConn(), event.getId());
				City city = nation.getCities().getCities().get(event.getCityId());
				city.setStrikeLength(1);
				city.setStrikeLevel(10);
				nation.getDomestic().setApproval(nation.getDomestic().getApproval() - 50);
				if(Math.random() > 0.5)
				{
					int casualties = (int)(1 + Math.random() * 5);
					nation.getArmy().setSize(nation.getArmy().getSize() - casualties);
					nation.update();
					return Responses.strikeSendArmyCasualties(casualties);
				}
				nation.update();
				return Responses.strikeSendArmyNoCasualties();
			}
		}
	}
}
