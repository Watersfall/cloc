package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyMonoplaneFighter extends SingleTechnology
{
	private static TechnologyMonoplaneFighter technologyMonoplaneFighter;
	public static final String NAME = "Monoplane Fighter";
	public static final String DESC = "Monoplane Fighter";
	public static final String COLUMN_NAME = "monoplane_fighter_tech";
	public static final String FIELD_NAME = "MonoplaneFighter";
	private @Getter String productionName = "monoplane_fighters";

	private TechnologyMonoplaneFighter()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BIPLANE_FIGHTERS);
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
	}

	public static TechnologyMonoplaneFighter getInstance()
	{
		if(technologyMonoplaneFighter == null)
		{
			technologyMonoplaneFighter = new TechnologyMonoplaneFighter();
		}
		return technologyMonoplaneFighter;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 28.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		map.put("oil", 2);
		return map;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.MONOPLANE_FIGHTERS;
	}
}
