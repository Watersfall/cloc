package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBoltActionClip extends SingleTechnology
{
	private static TechnologyBoltActionClip technologyBoltActionClip;
	public static final String NAME = "Clip Loaded Bolt Action Rifles";
	public static final String DESC = "Clip Loaded Bolt Action Rifles";
	public static final String COLUMN_NAME = "bolt_action_clip_tech";
	public static final String FIELD_NAME = "BoltActionClip";

	private TechnologyBoltActionClip()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.RIFLE_CLIPS);
		prerequisites.add(Technologies.BOLT_ACTION_MANUAL);
		costs.put("research", 50);
		costs.put("steel", 10);
	}

	public static TechnologyBoltActionClip getInstance()
	{
		if(technologyBoltActionClip == null)
		{
			technologyBoltActionClip = new TechnologyBoltActionClip();
		}
		return technologyBoltActionClip;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.BOLT_ACTION_CLIP;
	}
}
