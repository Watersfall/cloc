package com.watersfall.clocgame.model.technology.technologies.single;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyTank extends Technology
{
	private static TechnologyTank technologyTank;
	public static final String NAME = "Tanks";
	public static final String DESC = "Tanks";
	public static final String COLUMN_NAME = "tank_tech";

	private TechnologyTank()
	{
		super(NAME, DESC, COLUMN_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
	}

	public static TechnologyTank getInstance()
	{
		if(technologyTank == null)
		{
			technologyTank = new TechnologyTank();
		}
		return technologyTank;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.TANK;
	}
}
