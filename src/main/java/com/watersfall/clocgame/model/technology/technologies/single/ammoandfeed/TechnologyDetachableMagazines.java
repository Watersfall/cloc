package com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyDetachableMagazines extends SingleTechnology
{
	private static TechnologyDetachableMagazines technologyDetachableMagazines;
	public static final String NAME = "Detachable Magazines";
	public static final String DESC = "Detachable Magazines";
	public static final String COLUMN_NAME = "detachable_magazines_tech";
	public static final String FIELD_NAME = "DetachedMagazines";

	private TechnologyDetachableMagazines()
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

	public static TechnologyDetachableMagazines getInstance()
	{
		if(technologyDetachableMagazines == null)
		{
			technologyDetachableMagazines = new TechnologyDetachableMagazines();
		}
		return technologyDetachableMagazines;
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
		return Technologies.DETACHABLE_MAGAZINES;
	}
}
