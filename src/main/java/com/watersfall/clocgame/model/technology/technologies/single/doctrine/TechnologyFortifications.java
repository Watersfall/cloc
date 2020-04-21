package com.watersfall.clocgame.model.technology.technologies.single.doctrine;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyFortifications extends SingleTechnology
{
	private static TechnologyFortifications technologyFortifications;
	public static final String NAME = "Basic Fortifications";
	public static final String DESC = "Basic Fortifications";
	public static final String COLUMN_NAME = "basic_fortifications_tech";
	public static final String FIELD_NAME = "basicTrenchesTech";
	public static final int BONUS = 15;

	private TechnologyFortifications()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BASIC_TRENCHES);
		costs.put("research", 50);
		effects.add("+" + BONUS + "% to max entrenchment");
	}

	public static TechnologyFortifications getInstance()
	{
		if(technologyFortifications == null)
		{
			technologyFortifications = new TechnologyFortifications();
		}
		return technologyFortifications;
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
		return Technologies.BASIC_FORTIFICATIONS;
	}
}