package com.watersfall.clocgame.model;

import lombok.Getter;

public enum CitySize
{
	BRUH_WHAT(0, 0, "Bruh What", "How"),
	VILLAGE(100, 2,"Village", "Village"),
	TOWN(1000, 4,"Town", "Town"),
	LARGE_TOWN(10000, 7,"Large Town", "Large Town"),
	CITY(100000, 11,"City", "City"),
	LARGE_CITY(300000, 16,"Large City", "Large City"),
	METROPOLIS(1000000, 22,"Metropolis", "Metropolis"),
	MEGALOPOLIS(10000000, 29,"Megalopolis", "Megalopolis"),
	ECUMENOPOLIS(1000000000, 37,"Ecumenoplis", "Ecumenoplis");

	private @Getter long minimum;
	private @Getter String name;
	private @Getter String description;
	private @Getter int buildSlots;
	CitySize(long minimum, int buildSlots, String name, String description)
	{
		this.minimum = minimum;
		this.name = name;
		this.description = description;
		this.buildSlots = buildSlots;
	}
}
