package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyBombers extends SingleTechnology
{
	private static TechnologyBombers technologyBombers;
	public static final String NAME = "Bombers";
	public static final String DESC = "Bombers";
	public static final String COLUMN_NAME = "bomber_tech";
	public static final String FIELD_NAME = "Bomber";
	private @Getter String productionName = "bombers";

	private TechnologyBombers()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BIPLANE_FIGHTERS);
		prerequisites.add(Technologies.ZEPPELIN_BOMBERS);
		costs.put("research", 100);
		costs.put("steel", 50);
		costs.put("oil", 50);
		effects.add("Unlocks production: Bombers");
	}

	public static TechnologyBombers getInstance()
	{
		if(technologyBombers == null)
		{
			technologyBombers = new TechnologyBombers();
		}
		return technologyBombers;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 45.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 2);
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
		return Technologies.BOMBERS;
	}
}
