package com.watersfall.clocgame.model.technology.technologies.single.doctrine;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyAdvancedTrenches extends SingleTechnology
{
	private static TechnologyAdvancedTrenches technologyAdvancedTrenches;
	public static final String NAME = "Advanced Trench Construction";
	public static final String DESC = "Advanced Trench Construction";
	public static final String COLUMN_NAME = "advanced_trenches_tech";
	public static final String FIELD_NAME = "advancedTrenchesTech";
	public static final int BONUS = 15;

	private TechnologyAdvancedTrenches()
{
	super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
	this.prerequisites = new ArrayList<>();
	this.costs = new HashMap<>();
	this.requirements = new HashMap<>();
	prerequisites.add(Technologies.BASIC_FORTIFICATIONS);
	costs.put("research", 50);
	effects.add("+" + BONUS + "% to max entrenchment");
}

	public static TechnologyAdvancedTrenches getInstance()
	{
		if(technologyAdvancedTrenches == null)
		{
			technologyAdvancedTrenches = new TechnologyAdvancedTrenches();
		}
		return technologyAdvancedTrenches;
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
		return Technologies.ADVANCED_TRENCHES;
	}
}