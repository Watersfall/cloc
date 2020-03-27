package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyStrategicBombers extends SingleTechnology
{
	private static TechnologyStrategicBombers technologyStrategicBombers;
	public static final String NAME = "Strategic Bombing";
	public static final String DESC = "Strategic Bombing";
	public static final String COLUMN_NAME = "strategic_bombing_tech";
	public static final String FIELD_NAME = "StratBombing";

	private TechnologyStrategicBombers()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BOMBERS);
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
	}

	public static TechnologyStrategicBombers getInstance()
	{
		if(technologyStrategicBombers == null)
		{
			technologyStrategicBombers = new TechnologyStrategicBombers();
		}
		return technologyStrategicBombers;
	}

	@Override
	public boolean isProducible()
	{
		return false;
	}

	@Override
	public double getProductionICCost()
	{
		return 30.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 2);
		map.put("oil", 3);
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
		return Technologies.STRATEGIC_BOMBERS;
	}
}
