package net.watersfall.clocgame.model;

import lombok.Getter;

public enum Region
{
	NORTH_AMERICA("North America", "North American"), SOUTH_AMERICA("South America", "South American"), AFRICA("Africa", "African"), MIDDLE_EAST("Middle East", "Middle Eastern"), EUROPE("Europe", "European"), ASIA("Asia", "Asian"), OCEANIA("Oceania", "Oceanian"), SIBERIA("Siberia", "Siberian");

	private @Getter String name;
	private @Getter String adjective;

	Region(String name, String adjective)
	{
		this.name = name;
		this.adjective = adjective;
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
}


