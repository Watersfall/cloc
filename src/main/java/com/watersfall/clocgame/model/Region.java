package com.watersfall.clocgame.model;

import lombok.Getter;

public enum Region
{
	NORTH_AMERICA("North America"), SOUTH_AMERICA("South America"), AFRICA("Africa"), MIDDLE_EAST("Middle East"), EUROPE("Europe"), ASIA("Asia"), OCEANIA("Oceania"), SIBERIA("Siberia");

	private @Getter
	String name;

	Region(String name)
	{
		this.name = name;
	}

	public static boolean borders(Region region1, Region region2)
	{
		switch(region1)
		{
			case NORTH_AMERICA:
				return region2 == SOUTH_AMERICA || region2 == SIBERIA || region2 == NORTH_AMERICA;
			case SOUTH_AMERICA:
				return region2 == NORTH_AMERICA || region2 == SOUTH_AMERICA;
			case AFRICA:
				return region2 == MIDDLE_EAST || region2 == EUROPE || region2 == AFRICA;
			case MIDDLE_EAST:
				return region2 == AFRICA || region2 == EUROPE || region2 == ASIA || region2 == MIDDLE_EAST;
			case EUROPE:
				return region2 == AFRICA || region2 == MIDDLE_EAST || region2 == SIBERIA || region2 == EUROPE;
			case ASIA:
				return region2 == MIDDLE_EAST || region2 == OCEANIA || region2 == SIBERIA;
			case OCEANIA:
				return region2 == ASIA || region2 == OCEANIA;
			case SIBERIA:
				return region2 == EUROPE || region2 == NORTH_AMERICA || region2 == SIBERIA;
			default:
				return false;
		}
	}

	public static Region getFromName(String name)
	{
		switch(name)
		{
			case "North America":
				return NORTH_AMERICA;
			case "South America":
				return SOUTH_AMERICA;
			case "Africa":
				return AFRICA;
			case "Middle East":
				return MIDDLE_EAST;
			case "Europe":
				return EUROPE;
			case "Asia":
				return ASIA;
			case "Oceania":
				return OCEANIA;
			case "Siberia":
				return SIBERIA;
			default:
				return null;

		}
	}


}


