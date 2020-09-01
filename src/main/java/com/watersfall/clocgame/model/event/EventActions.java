package com.watersfall.clocgame.model.event;


import com.watersfall.clocgame.model.effects.KillTroops;
import com.watersfall.clocgame.model.nation.Modifiers;
import lombok.Getter;

public enum EventActions
{
	STRIKE_GIVE_IN(Events.STRIKE, "Give in to demands", "", "", Modifiers.STRIKE_GAVE_IN),
	STRIKE_IGNORE(Events.STRIKE, "Ignore them", "", "", Modifiers.STRIKE_IGNORED),
	STRIKE_SEND_ARMY(Events.STRIKE, "Send in the army", "", "", new KillTroops(1, 5, 0.5), Modifiers.STRIKE_SENT_ARMY);

	private @Getter Events parent;
	private @Getter String text;
	private @Getter String color;
	private @Getter String description;
	private @Getter Object[] effects;
	EventActions(Events parent, String text, String color, String description, Object... effects)
	{
		this.parent = parent;
		this.text = text;
		this.color = color;
		this.description = description;
		this.effects = effects;
	}
}
