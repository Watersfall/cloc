package com.watersfall.clocgame.model.technology.technologies.single.doctrine;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyReinforcedConcrete extends SingleTechnology
{
	private static TechnologyReinforcedConcrete technologyReinforcedConcrete;
	public static final String NAME = "Reinforced Concrete Bunkers";
	public static final String DESC = "Reinforced Concrete Bunkers";
	public static final String COLUMN_NAME = "reinforced_concrete_tech";
	public static final String FIELD_NAME = "reinforcedConcreteTech";
	public static final int BONUS = 15;

	private TechnologyReinforcedConcrete()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.ADVANCED_TRENCHES);
		costs.put("research", 50);
		effects.add("+" + BONUS + "% to max entrenchment");
	}

	public static TechnologyReinforcedConcrete getInstance()
	{
		if(technologyReinforcedConcrete == null)
		{
			technologyReinforcedConcrete = new TechnologyReinforcedConcrete();
		}
		return technologyReinforcedConcrete;
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
		return Technologies.ADVANCED_FORTIFICATIONS;
	}
}