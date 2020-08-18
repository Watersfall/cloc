package com.watersfall.clocgame.model.event;

import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;

import java.util.ArrayList;

public enum Events
{
	STRIKE("Strike!", "Workers unions across the country have organized strikes and have ground the economy of " +
			"${city.name} to a halt. We can either negotiate with the union leaders, ignore the situation or issue an " +
			"injunction and enforce it with the army.");

	private @Getter String title;
	private @Getter String description;
	Events(String title, String description)
	{
		this.title = title;
		this.description = description;
	}

	public ArrayList<EventActions> getPossibleResponses(Nation nation)
	{
		ArrayList<EventActions> list = new ArrayList<>();
		for(EventActions actions : EventActions.values())
		{
			if(actions.getParent() == this)
			{
				list.add(actions);
			}
		}
		return list;
	}

	public String getDescription(Nation nation, Event event)
	{
		String temp = description;
		temp = temp.replace("${city.name}", nation.getCities().get(event.getCityId()).getName());
		return temp;
	}
}
