package com.watersfall.clocgame.model.event;

import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;

import java.util.ArrayList;

public enum Events
{
	STRIKE("Strike!", "Workers unions across the country have organized strikes and have ground the economy of " +
			"${city.name} to a halt. We can either negotiate with the union leaders, ignore the situation or issue an " +
			"injunction and enforce it with the army.", "red");

	private @Getter String title;
	private @Getter String description;
	private @Getter String color;
	Events(String title, String description, String color)
	{
		this.title = title;
		this.description = description;
		this.color  = color;
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
		temp = temp.replace("${city.name}", nation.getCities().get(event.getCityId()).getUrl());
		return temp;
	}
}
