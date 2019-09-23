package com.watersfall.clocgame.model.technology.technologies.single;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBombers extends Technology
{
	private static TechnologyBombers technologyBombers;
	public static final String NAME = "Bombers";
	public static final String DESC = "Bombers";
	public static final String COLUMN_NAME = "bomber_tech";

	private TechnologyBombers()
	{
		super(NAME, DESC, COLUMN_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 50);
		costs.put("oil", 50);
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
