package com.watersfall.clocgame.model.military;

import lombok.Getter;

public enum Bomber
{
	ZEPPELIN_BOMBER("Zeppelin Bombers", 5.0),
	BOMBER("Bombers", 10.0);

	private @Getter String name;
	private @Getter double power;
	Bomber(String name, double power)
	{
		this.name = name;
		this.power = power;
	}
}
