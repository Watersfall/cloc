package com.watersfall.clocgame.model.technology.technologies.single.doctrine;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyTrenches extends SingleTechnology
{
	private static TechnologyTrenches technologyTrenches;
	public static final String NAME = "Basic Trench Digging";
	public static final String DESC = "Basic Trench Digging";
	public static final String COLUMN_NAME = "basic_trenches_tech";
	public static final String FIELD_NAME = "basicTrenchesTech";
	public static final int BONUS = 15;

	private TechnologyTrenches()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		effects.add("+" + BONUS + "% to max entrenchment");
	}

	public static TechnologyTrenches getInstance()
	{
		if(technologyTrenches == null)
		{
			technologyTrenches = new TechnologyTrenches();
		}
		return technologyTrenches;
	}

	@Override
	public boolean isProducible()
	{
		return false;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.BASIC_TRENCHES;
	}
}