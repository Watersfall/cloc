package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyNeedleNoseRifle extends SingleTechnology
{
	private static TechnologyNeedleNoseRifle technologyNeedleNoseRifle;
	public static final String NAME = "Needle Nosed Rifles";
	public static final String DESC = "Needle Nosed Rifles";
	public static final String COLUMN_NAME = "needle_nose_rifle_tech";
	public static final String FIELD_NAME = "NeedleNose";
	private @Getter String productionName = "needle_nose";

	private TechnologyNeedleNoseRifle()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.PAPER_CARTRIDGE);
		prerequisites.add(Technologies.SINGLE_SHOT_RIFLE);
		costs.put("research", 50);
		costs.put("steel", 10);
	}

	public static TechnologyNeedleNoseRifle getInstance()
	{
		if(technologyNeedleNoseRifle == null)
		{
			technologyNeedleNoseRifle = new TechnologyNeedleNoseRifle();
		}
		return technologyNeedleNoseRifle;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 0.06;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		return map;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.NEEDLE_NOSE_RIFLE;
	}
}
