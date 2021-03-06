package net.watersfall.clocgame.model.technology.technologies;

import lombok.Getter;

public enum Category
{
	ECONOMY("Economy"),
	WEAPONS("Weapons"),
	ARTILLERY("Artillery"),
	LAND("Land Vehicles"),
	AIR("Aircraft"),
	SEA("Naval"),
	DOCTRINE("Doctrine"),
	WMD("Weapons of Mass Destruction");

	private @Getter String name;

	Category(String name)
	{
		this.name = name;
	}
}
