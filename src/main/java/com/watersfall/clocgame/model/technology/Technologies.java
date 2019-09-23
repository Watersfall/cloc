package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.technology.technologies.single.*;
import lombok.Getter;

public enum Technologies
{
	BOLT_ACTION(TechnologyBoltAction.getInstance()),
	SEMI_AUTOMATIC(TechnologySemiAutomatic.getInstance()),
	MACHINE_GUN(TechnologyMachineGun.getInstance()),
	TANK(TechnologyTank.getInstance()),
	SHIP_OIL(TechnologyShipOil.getInstance()),
	BOMBERS(TechnologyBombers.getInstance()),
	CHEMICAL_WEAPONS(TechnologyChemicalWeapons.getInstance()),
	ADVANCED_CHEMICAL_WEAPONS(TechnologyAdvancedChemicalWeapons.getInstance()),
	STRATEGIC_BOMBING(TechnologyStrategicBombing.getInstance());

	private @Getter Technology technology;

	Technologies(Technology technology)
	{
		this.technology = technology;
	}
}
