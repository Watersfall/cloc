package com.watersfall.clocgame.model.policies;

import lombok.Getter;

public enum PolicyCategory
{
	ECONOMY("Economy"),
	MANPOWER("Manpower"),
	FOOD("Food"),
	FORTIFICATION("Fortification"),
	FARM_SUBSIDIZATION("Farm Subsidization");

	private @Getter String name;
	PolicyCategory(String name)
	{
		this.name = name;
	}
}
