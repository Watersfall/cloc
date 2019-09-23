package com.watersfall.clocgame.model.technology.technologies.single;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologySemiAutomatic extends Technology
{
	private static TechnologySemiAutomatic technologySemiAutomatic;
	public static final String NAME = "Semi Automatic Rifles";
	public static final String DESC = "Semi Automatic Rifles";
	public static final String COLUMN_NAME = "semi_automatic_tech";

	private TechnologySemiAutomatic()
	{
		super(NAME, DESC, COLUMN_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BOLT_ACTION);
		costs.put("research", 100);
		costs.put("steel", 50);
	}

	public static TechnologySemiAutomatic getInstance()
	{
		if(technologySemiAutomatic == null)
		{
			technologySemiAutomatic = new TechnologySemiAutomatic();
		}
		return technologySemiAutomatic;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.SEMI_AUTOMATIC;
	}
}