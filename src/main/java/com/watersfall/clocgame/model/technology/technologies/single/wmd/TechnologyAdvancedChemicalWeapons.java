package com.watersfall.clocgame.model.technology.technologies.single.wmd;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyAdvancedChemicalWeapons extends SingleTechnology
{
	private static TechnologyAdvancedChemicalWeapons technologyAdvancedChemicalWeapons;
	public static final String NAME = "Advanced Chemical Weapons";
	public static final String DESC = "Advanced Chemical Weapons";
	public static final String COLUMN_NAME = "chem_tech";
	public static final String FIELD_NAME = "AdvancedChemTech";

	private TechnologyAdvancedChemicalWeapons()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 5);
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
	public boolean isProducible()
	{
		return false;
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
