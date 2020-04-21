package com.watersfall.clocgame.model.technology.technologies.single.economy;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBasicArtificialFertilizer extends SingleTechnology
{
	private static TechnologyBasicArtificialFertilizer technologyBasicArtificialFertilizer;
	public static final String NAME = "Basic Artificial Fertilizer";
	public static final String DESC = "Basic Artificial Fertilizer";
	public static final String COLUMN_NAME = "basic_artificial_fertilizer";
	public static final String FIELD_NAME = "";
	public static final double FOOD_GAIN = .50;

	private TechnologyBasicArtificialFertilizer()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		effects.add("+" + (FOOD_GAIN * 100) + "% food production");
	}

	public static TechnologyBasicArtificialFertilizer getInstance()
	{
		if(technologyBasicArtificialFertilizer == null)
		{
			technologyBasicArtificialFertilizer = new TechnologyBasicArtificialFertilizer();
		}
		return technologyBasicArtificialFertilizer;
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
		return Technologies.BASIC_ARTIFICIAL_FERTILIZER;
	}
}
