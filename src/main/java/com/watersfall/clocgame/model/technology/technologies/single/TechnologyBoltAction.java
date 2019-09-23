package com.watersfall.clocgame.model.technology.technologies.single;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBoltAction extends Technology
{
	private static TechnologyBoltAction technologyBoltAction;
	public static final String NAME = "Bolt Action Rifles";
	public static final String DESC = "Bolt Action Rifles";
	public static final String COLUMN_NAME = "bolt_action_tech";

	private TechnologyBoltAction()
	{
		super(NAME, DESC, COLUMN_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		costs.put("steel", 10);
	}

	public static TechnologyBoltAction getInstance()
	{
		if(technologyBoltAction == null)
		{
			technologyBoltAction = new TechnologyBoltAction();
		}
		return technologyBoltAction;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.BOLT_ACTION;
	}
}
