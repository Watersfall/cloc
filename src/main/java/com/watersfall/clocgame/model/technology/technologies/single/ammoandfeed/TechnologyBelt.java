package com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBelt extends SingleTechnology
{
	private static TechnologyBelt technologyBelt;
	public static final String NAME = "Belt Feeding Mechanism";
	public static final String DESC = "Belt Feeding Mechanism";
	public static final String COLUMN_NAME = "belt_tech";
	public static final String FIELD_NAME = "Belt";

	private TechnologyBelt()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.RIFLE_CLIPS);
		costs.put("research", 50);
		costs.put("steel", 35);
		costs.put("nitrogen", 35);
	}

	public static TechnologyBelt getInstance()
	{
		if(technologyBelt == null)
		{
			technologyBelt = new TechnologyBelt();
		}
		return technologyBelt;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.BELT;
	}
}
