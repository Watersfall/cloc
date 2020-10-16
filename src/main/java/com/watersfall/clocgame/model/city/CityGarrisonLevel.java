package com.watersfall.clocgame.model.city;

import lombok.Getter;

public enum CityGarrisonLevel
{
	DECREASED(-1000),
	NORMAL(0),
	INCREASED(1000);

	private @Getter int modifier;
	CityGarrisonLevel(int modifier)
	{
		this.modifier = modifier;
	}
}
