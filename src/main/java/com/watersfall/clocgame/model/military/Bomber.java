package com.watersfall.clocgame.model.military;

import lombok.Getter;

public enum Bomber implements Plane
{
	ZEPPELIN_BOMBER("Zeppelin Bombers", 1.5, 5.0),
	BOMBER("Bombers", 3.0, 10.0);

	private @Getter String name;
	private @Getter  double defense;
	private @Getter double bombingPower;
	Bomber(String name, double defense, double bombingPower)
	{
		this.name = name;
		this.defense = defense;
		this.bombingPower = bombingPower;
	}
}
