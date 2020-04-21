package com.watersfall.clocgame.model.technology.technologies.single.economy;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyAdvancedArtificialFertilizer extends SingleTechnology
{
	private static TechnologyAdvancedArtificialFertilizer technologyAdvancedArtificialFertilizer;
	public static final String NAME = "Advanced Artificial Fertilizer";
	public static final String DESC = "Advanced Artificial Fertilizer";
	public static final String COLUMN_NAME = "advanced_artificial_fertilizer";
	public static final String FIELD_NAME = "";
	public static final double LAND_PER_NITROGEN = 5000.0;
	public static final double FOOD_GAIN = 2;

	private TechnologyAdvancedArtificialFertilizer()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("nitrogen", 50);
		prerequisites.add(Technologies.ARTIFICIAL_FERTILIZER);
		effects.add("+" + (FOOD_GAIN * 100) + "% food production");
		effects.add("+1 nitrogen upkeep per " + LAND_PER_NITROGEN + "km<sup>2</sup> land");
	}

	public static TechnologyAdvancedArtificialFertilizer getInstance()
	{
		if(technologyAdvancedArtificialFertilizer == null)
		{
			technologyAdvancedArtificialFertilizer = new TechnologyAdvancedArtificialFertilizer();
		}
		return technologyAdvancedArtificialFertilizer;
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
		return Technologies.ADVANCED_ARTIFICIAL_FERTILIZER;
	}
}
