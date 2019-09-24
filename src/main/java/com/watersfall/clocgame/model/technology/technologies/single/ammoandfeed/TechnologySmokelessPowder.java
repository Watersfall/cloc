package com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologySmokelessPowder extends SingleTechnology
{
	private static TechnologySmokelessPowder technologySmokelessPowder;
	public static final String NAME = "Ball & Smokeless Powder";
	public static final String DESC = "Ball & Smokeless Powder";
	public static final String COLUMN_NAME = "smokeless_powder_tech";
	public static final String FIELD_NAME = "SmokelessPowder";

	private TechnologySmokelessPowder()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BALL_AND_POWDER);
		costs.put("research", 50);
		costs.put("steel", 15);
		costs.put("nitrogen", 15);
	}

	public static TechnologySmokelessPowder getInstance()
	{
		if(technologySmokelessPowder == null)
		{
			technologySmokelessPowder = new TechnologySmokelessPowder();
		}
		return technologySmokelessPowder;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.SMOKELESS_POWDER;
	}
}

