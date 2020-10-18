package net.watersfall.clocgame.model.technology.technologies.single.economy;

import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyAdvancedFarmingMachines extends SingleTechnology
{
	private static TechnologyAdvancedFarmingMachines technologyAdvancedFarmingMachines;
	public static final String NAME = "Advanced Farming Machines";
	public static final String DESC = "Advanced Farming Machines";
	public static final String COLUMN_NAME = "advanced_farming_machines";
	public static final String FIELD_NAME = "";
	public static final double LAND_PER_STEEL = 5000.0;
	public static final double FOOD_GAIN = 2;

	private TechnologyAdvancedFarmingMachines()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 150);
		costs.put("steel", 75);
		prerequisites.add(Technologies.FARMING_MACHINES);
		effects.add("+" + (FOOD_GAIN * 100) + "% food production");
		effects.add("+1 steel upkeep per " + LAND_PER_STEEL + "km<sup>2</sup> land");
	}

	public static TechnologyAdvancedFarmingMachines getInstance()
	{
		if(technologyAdvancedFarmingMachines == null)
		{
			technologyAdvancedFarmingMachines = new TechnologyAdvancedFarmingMachines();
		}
		return technologyAdvancedFarmingMachines;
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
		return Technologies.ADVANCED_FARMING_MACHINES;
	}
}
