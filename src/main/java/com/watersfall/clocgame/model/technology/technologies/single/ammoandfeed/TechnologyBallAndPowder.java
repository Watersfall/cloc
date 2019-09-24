package com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBallAndPowder extends SingleTechnology
{
	private static TechnologyBallAndPowder technologyBallAndPowder;
	public static final String NAME = "Ball & Gunpowder";
	public static final String DESC = "Ball & Gunpowder";
	public static final String COLUMN_NAME = "ball_and_powder_tech";
	public static final String FIELD_NAME = "BallAndPowder";

	private TechnologyBallAndPowder()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		costs.put("steel", 10);
		costs.put("nitrogen", 10);
	}

	public static TechnologyBallAndPowder getInstance()
	{
		if(technologyBallAndPowder == null)
		{
			technologyBallAndPowder = new TechnologyBallAndPowder();
		}
		return technologyBallAndPowder;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.BALL_AND_POWDER;
	}
}
