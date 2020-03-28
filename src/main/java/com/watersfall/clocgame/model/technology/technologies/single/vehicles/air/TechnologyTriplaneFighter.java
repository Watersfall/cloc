package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyTriplaneFighter extends SingleTechnology
{
	private static TechnologyTriplaneFighter technologyTriplaneFighter;
	public static final String NAME = "Triplane Fighter";
	public static final String DESC = "Triplane Fighter";
	public static final String COLUMN_NAME = "triplane_fighter_tech";
	public static final String FIELD_NAME = "TriplaneFighter";
	private @Getter String productionName = "triplane_fighters";

	private TechnologyTriplaneFighter()
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

	public static TechnologyTriplaneFighter getInstance()
	{
		if(technologyTriplaneFighter == null)
		{
			technologyTriplaneFighter = new TechnologyTriplaneFighter();
		}
		return technologyTriplaneFighter;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 25.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		map.put("oil", 1);
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
		return Technologies.TRIPLANE_FIGHTERS;
	}
}
