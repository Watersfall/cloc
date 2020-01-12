package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

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
		prerequisites.add(Technologies.RIFLE_CLIPS);
		prerequisites.add(Technologies.BOLT_ACTION_CLIP);
		costs.put("research", 50);
		costs.put("steel", 10);
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
	public double getProductionCost()
	{
		return 0.12;
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
