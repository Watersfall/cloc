package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyStraightPullRifle extends SingleTechnology
{
	private static TechnologyStraightPullRifle technologyStraightPullRifle;
	public static final String NAME = "Straight Pull Rifles";
	public static final String DESC = "Straight Pull Rifles";
	public static final String COLUMN_NAME = "straight_pull_rifle_tech";
	public static final String FIELD_NAME = "StraightPull";
	private @Getter String productionName = "straight_pull";

	private TechnologyStraightPullRifle()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BOLT_ACTION_CLIP);
		costs.put("research", 50);
		costs.put("steel", 10);
		effects.add("Unlocks production: Straight Pull Rifles");
	}

	public static TechnologyStraightPullRifle getInstance()
	{
		if(technologyStraightPullRifle == null)
		{
			technologyStraightPullRifle = new TechnologyStraightPullRifle();
		}
		return technologyStraightPullRifle;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 0.4;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 2);
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
		return Technologies.STRAIGHT_PULL_RIFLE;
	}
}
