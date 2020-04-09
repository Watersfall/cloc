package com.watersfall.clocgame.model.military;

import lombok.Getter;

public enum ReconPlane implements Plane
{
	RECON_BALLOON("Recon Balloon", 1.25),
	RECON_PLANE("Recon Plane", 1.5);

	private @Getter String name;
	private @Getter double power;

	ReconPlane(String name, double power)
	{
		this.name = name;
		this.power = power;
	}
}
