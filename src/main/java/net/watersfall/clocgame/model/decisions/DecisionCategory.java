package net.watersfall.clocgame.model.decisions;

import lombok.Getter;

public enum DecisionCategory
{
	ECONOMY("Economy"),
	DOMESTIC("Domestic"),
	FOREIGN("Foreign"),
	MILITARY("Military"),
	ALL("All");

	private @Getter String name;
	DecisionCategory(String name)
	{
		this.name = name;
	}
}
