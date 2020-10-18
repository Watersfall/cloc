package net.watersfall.clocgame.model.technology.technologies.single.doctrine;

import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyMobileDefense extends SingleTechnology
{
	private static TechnologyMobileDefense technologyMobileDefense;
	public static final String NAME = "Mobile Defense";
	public static final String DESC = "Mobile Defense";
	public static final String COLUMN_NAME = "mobile_defense_tech";
	public static final String FIELD_NAME = "mobileDefenseTech";
	public static final int BONUS = 20;

	private TechnologyMobileDefense()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.ADVANCED_FORTIFICATIONS);
		costs.put("research", 50);
		effects.add("+" + BONUS + "% to max entrenchment");
	}

	public static TechnologyMobileDefense getInstance()
	{
		if(technologyMobileDefense == null)
		{
			technologyMobileDefense = new TechnologyMobileDefense();
		}
		return technologyMobileDefense;
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
		return Technologies.MOBILE_DEFENSE;
	}
}