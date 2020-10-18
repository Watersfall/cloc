package net.watersfall.clocgame.model.effects;

import lombok.Getter;

public class KillTroops implements Effect
{
	private @Getter int min, max;
	private @Getter double chance;
	public KillTroops(int min, int max, double chance)
	{
		this.min = min;
		this.max = max;
		this.chance = chance;
	}

	public String getDisplay()
	{
		return "Has a " + ((int)(chance * 100)) + "% chance of killing between " + min + "k and " + max + "k troops";
	}
}
