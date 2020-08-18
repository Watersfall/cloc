package com.watersfall.clocgame.model.event;


import lombok.Getter;

public enum EventActions
{
	STRIKE_GIVE_IN(Events.STRIKE, "Give in to demands", "", ""),
	STRIKE_IGNORE(Events.STRIKE, "Ignore them", "", ""),
	STRIKE_SEND_ARMY(Events.STRIKE, "Send in the army", "", "");

	private @Getter Events parent;
	private @Getter String text;
	private @Getter String color;
	private @Getter String description;
	EventActions(Events parent, String text, String color, String description)
	{
		this.parent = parent;
		this.text = text;
		this.color = color;
		this.description = description;
	}
}
