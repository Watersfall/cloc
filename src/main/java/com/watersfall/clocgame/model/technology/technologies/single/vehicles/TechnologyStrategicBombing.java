package com.watersfall.clocgame.model.technology.technologies.single.vehicles;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyStrategicBombing extends SingleTechnology
{
	private static TechnologyStrategicBombing technologyStrategicBombing;
	public static final String NAME = "Strategic Bombing";
	public static final String DESC = "Strategic Bombing";
	public static final String COLUMN_NAME = "strategic_bombing_tech";
	public static final String FIELD_NAME = "StratBombing";

	private TechnologyStrategicBombing()
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

	public static TechnologyStrategicBombing getInstance()
	{
		if(technologyStrategicBombing == null)
		{
			technologyStrategicBombing = new TechnologyStrategicBombing();
		}
		return technologyStrategicBombing;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.STRATEGIC_BOMBING;
	}
}
