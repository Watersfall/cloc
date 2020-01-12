package com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyRifleClips extends SingleTechnology
{
	private static TechnologyRifleClips technologyRifleClips;
	public static final String NAME = "Rifle Clips";
	public static final String DESC = "Rifle Clips";
	public static final String COLUMN_NAME = "rifle_clips_tech";
	public static final String FIELD_NAME = "RifleClips";

	private TechnologyRifleClips()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BRASS_CARTRIDGE);
		costs.put("research", 50);
		costs.put("steel", 30);
		costs.put("nitrogen", 30);
	}

	public static TechnologyRifleClips getInstance()
	{
		if(technologyRifleClips == null)
		{
			technologyRifleClips = new TechnologyRifleClips();
		}
		return technologyRifleClips;
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
		return Technologies.RIFLE_CLIPS;
	}
}

