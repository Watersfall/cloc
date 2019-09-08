package com.watersfall.clocgame.model;

import lombok.Getter;

public enum CityType
{
	MINING("MINING"), DRILLING("DRILLING"), INDUSTRY("INDUSTRY"), FARMING("FARMING");

	private @Getter String name;

	CityType(String name)
	{
		this.name = name;
	}

	public static CityType getByName(String name)
	{
		switch(name)
		{
			case "MINING":
				return MINING;
			case "DRILLING":
				return DRILLING;
			case "INDUSTRY":
				return INDUSTRY;
			case "FARMING":
				return FARMING;
			default:
				return null;
		}
	}

}
