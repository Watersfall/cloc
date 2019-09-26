package com.watersfall.clocgame.model.technology.technologies.single.wmd;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyChemicalWeapons extends SingleTechnology
{
	private static TechnologyChemicalWeapons technologyChemicalWeapons;
	public static final String NAME = "Chemical Weapons";
	public static final String DESC = "Chemical Weapons";
	public static final String COLUMN_NAME = "chem_tech";
	public static final String FIELD_NAME = "Chem";

	private TechnologyChemicalWeapons()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 5);
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