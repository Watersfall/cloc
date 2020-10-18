package net.watersfall.clocgame.model.modifier;

import lombok.Getter;
import net.watersfall.clocgame.model.TextKey;

import java.util.HashMap;

public enum Modifiers
{
	STRIKE_IGNORED("Strike Ignored", 6, TextKey.Modifiers.RESOURCE_PRODUCTION, -50.0, TextKey.Modifiers.STABILITY_PER_MONTH, -3.0),
	STRIKE_GAVE_IN("Strike Gave In", 12, TextKey.Modifiers.RESOURCE_PRODUCTION, -25.0, TextKey.Modifiers.STABILITY_PER_MONTH, 2.0),
	STRIKE_SENT_ARMY("Strike Sent Army", 2, TextKey.Modifiers.RESOURCE_PRODUCTION, -100.0, TextKey.Modifiers.STABILITY_PER_MONTH, -10.0);

	private @Getter String name;
	private @Getter int length;
	private @Getter HashMap<TextKey.Modifiers, Double> effects;
	Modifiers(String name, int length, Object... effects)
	{
		this.name = name;
		this.length = length;
		this.effects = new HashMap<>();
		for(int i = 0; i < effects.length; i= i + 2)
		{
			this.effects.put((TextKey.Modifiers)effects[i], (Double)effects[i + 1]);
		}
	}
}
