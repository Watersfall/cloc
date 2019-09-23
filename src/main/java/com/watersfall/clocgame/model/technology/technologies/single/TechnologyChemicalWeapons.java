package com.watersfall.clocgame.model.technology.technologies.single;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyChemicalWeapons extends Technology
{
	private static TechnologyChemicalWeapons technologyChemicalWeapons;
	public static final String NAME = "Chemical Weapons";
	public static final String DESC = "Chemical Weapons";
	public static final String COLUMN_NAME = "chem_tech";

	private TechnologyChemicalWeapons()
	{
		super(NAME, DESC, COLUMN_NAME, 5);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
	}

	public static TechnologyChemicalWeapons getInstance()
	{
		if(technologyChemicalWeapons == null)
		{
			technologyChemicalWeapons = new TechnologyChemicalWeapons();
		}
		return technologyChemicalWeapons;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.CHEMICAL_WEAPONS;
	}
}
