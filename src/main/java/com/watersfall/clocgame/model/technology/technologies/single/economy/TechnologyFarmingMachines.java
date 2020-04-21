package com.watersfall.clocgame.model.technology.technologies.single.economy;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyFarmingMachines extends SingleTechnology
{
	private static TechnologyFarmingMachines technologyFarmingMachines;
	public static final String NAME = "Farming Machines";
	public static final String DESC = "Farming Machines";
	public static final String COLUMN_NAME = "farming_machines";
	public static final String FIELD_NAME = "";
	public static final double LAND_PER_STEEL = 5000.0;
	public static final double FOOD_GAIN = 1;

	private TechnologyFarmingMachines()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 50);
		prerequisites.add(Technologies.ARTIFICIAL_FERTILIZER);
		effects.add("+" + (FOOD_GAIN * 100) + "% food production");
		effects.add("+1 steel upkeep per " + LAND_PER_STEEL + "km<sup>2</sup> land");
	}

	public static TechnologyFarmingMachines getInstance()
	{
		if(technologyFarmingMachines == null)
		{
			technologyFarmingMachines = new TechnologyFarmingMachines();
		}
		return technologyFarmingMachines;
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
		return Technologies.FARMING_MACHINES;
	}
}
