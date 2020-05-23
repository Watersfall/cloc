package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Key;
import lombok.Getter;

import java.util.HashMap;

public enum Modifiers
{
	STRIKE_IGNORED(6, Key.Modifiers.RESOURCE_PRODUCTION, -0.5, Key.Modifiers.STABILITY_PER_MONTH, -3.0),
	STRIKE_GAVE_IN(12, Key.Modifiers.RESOURCE_PRODUCTION, -0.25, Key.Modifiers.STABILITY_PER_MONTH, 2.0),
	STRIKE_SENT_ARMY(2, Key.Modifiers.RESOURCE_PRODUCTION, -1.0, Key.Modifiers.STABILITY_PER_MONTH, -10.0);

	private @Getter int length;
	private @Getter HashMap<Key, Double> effects;
	Modifiers(int length, Object... effects)
	{
		this.length = length;
		this.effects = new HashMap<>();
		for(int i = 0; i < effects.length; i= i + 2)
		{
			this.effects.put((Key)effects[i], (Double)effects[i + 1]);
		}
	}
}
