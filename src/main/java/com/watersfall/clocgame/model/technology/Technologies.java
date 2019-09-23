package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.technology.technologies.TechnologyAdvancedChemicalWeapons;
import com.watersfall.clocgame.model.technology.technologies.TechnologyBoltAction;
import com.watersfall.clocgame.model.technology.technologies.TechnologyBombers;
import com.watersfall.clocgame.model.technology.technologies.TechnologyChemicalWeapons;
import lombok.Getter;

public enum Technologies
{
	BOLT_ACTION(TechnologyBoltAction.getInstance()),
	BOMBERS(TechnologyBombers.getInstance()),
	CHEMICAL_WEAPONS(TechnologyChemicalWeapons.getInstance()),
	ADVANCED_CHEMICAL_WEAPONS(TechnologyAdvancedChemicalWeapons.getInstance());

	private @Getter Technology technology;

	Technologies(Technology technology)
	{
		this.technology = technology;
	}
}
