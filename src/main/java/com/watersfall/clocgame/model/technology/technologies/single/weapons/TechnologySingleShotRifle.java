package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologySingleShotRifle extends SingleTechnology
{
	private static TechnologySingleShotRifle technologySingleShotRifle;
	public static final String NAME = "Single Shot Rifles";
	public static final String DESC = "Single Shot Rifles";
	public static final String COLUMN_NAME = "single_shot_rifle_tech";
	public static final String FIELD_NAME = "SingleShot";

	private TechnologySingleShotRifle()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.PAPER_CARTRIDGE);
		costs.put("research", 50);
		costs.put("steel", 10);
	}

	public static TechnologySingleShotRifle getInstance()
	{
		if(technologySingleShotRifle == null)
		{
			technologySingleShotRifle = new TechnologySingleShotRifle();
		}
		return technologySingleShotRifle;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.SINGLE_SHOT_RIFLE;
	}
}