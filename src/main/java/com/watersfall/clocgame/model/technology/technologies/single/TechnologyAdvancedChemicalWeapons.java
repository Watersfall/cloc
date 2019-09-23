package com.watersfall.clocgame.model.technology.technologies.single;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyAdvancedChemicalWeapons extends Technology
{
	private static TechnologyAdvancedChemicalWeapons technologyAdvancedChemicalWeapons;
	public static final String NAME = "Advanced Chemical Weapons";
	public static final String DESC = "Advanced Chemical Weapons";
	public static final String COLUMN_NAME = "chem_tech";

	private TechnologyAdvancedChemicalWeapons()
	{
		super(NAME, DESC, COLUMN_NAME, 5);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.CHEMICAL_WEAPONS);
		costs.put("research", 100);
	}

	public static TechnologyAdvancedChemicalWeapons getInstance()
	{
		if(technologyAdvancedChemicalWeapons == null)
		{
			technologyAdvancedChemicalWeapons = new TechnologyAdvancedChemicalWeapons();
		}
		return technologyAdvancedChemicalWeapons;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.ADVANCED_CHEMICAL_WEAPONS;
	}
}
