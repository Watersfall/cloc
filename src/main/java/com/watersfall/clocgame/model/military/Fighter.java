package com.watersfall.clocgame.model.military;

import lombok.Getter;

public enum Fighter
{
	BIPLANE_FIGHTER("Biplane Fighter", 2.0),
	TRIPLANE_FIGHTER("Triplane Fighter", 3.0),
	MONOPLANE_FIGHTER("Fighter", 4.0);

	private @Getter String name;
	private @Getter double power;
	Fighter(String name, double power)
	{
		this.name = name;
		this.power = power;
	}
}
