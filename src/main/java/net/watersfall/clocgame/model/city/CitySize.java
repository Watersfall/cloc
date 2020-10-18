package net.watersfall.clocgame.model.city;

import lombok.Getter;

public enum CitySize
{
	BRUH_WHAT(0, 0, 100000, "Bruh What", "How"),
	VILLAGE(99, 2, 100, "Village", "Village"),
	TOWN(1000, 4, 45, "Town", "Town"),
	LARGE_TOWN(10000, 7, 20,"Large Town", "Large Town"),
	CITY(100000, 11, 6.5, "City", "City"),
	LARGE_CITY(300000, 16, 2.5, "Large City", "Large City"),
	METROPOLIS(1000000, 22, 0,"Metropolis", "Metropolis"),
	MEGALOPOLIS(10000000, 29, -0.25, "Megalopolis", "Megalopolis"),
	ECUMENOPOLIS(1000000000, 37, -0.5, "Ecumenoplis", "Ecumenoplis");

	private @Getter long minimum;
	private @Getter String name;
	private @Getter String description;
	private @Getter int buildSlots;
	private @Getter double popGrowthBonus;
	CitySize(long minimum, int buildSlots, double popGrowthBonus, String name, String description)
	{
		this.minimum = minimum;
		this.name = name;
		this.popGrowthBonus = popGrowthBonus;
		this.description = description;
		this.buildSlots = buildSlots;
	}
}
