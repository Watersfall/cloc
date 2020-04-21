package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyBiplaneFighter extends SingleTechnology
{
	private static TechnologyBiplaneFighter technologyBiplaneFighter;
	public static final String NAME = "Biplane Fighter";
	public static final String DESC = "Biplane Fighter";
	public static final String COLUMN_NAME = "biplane_fighter_tech";
	public static final String FIELD_NAME = "BiplaneFighter";
	private @Getter String productionName = "biplane_fighters";

	private TechnologyBiplaneFighter()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.RECON_BIPLANE);
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
		effects.add("Unlocks production: Biplane Fighters");
	}

	public static TechnologyBiplaneFighter getInstance()
	{
		if(technologyBiplaneFighter == null)
		{
			technologyBiplaneFighter = new TechnologyBiplaneFighter();
		}
		return technologyBiplaneFighter;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 24.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		map.put("oil", 1);
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
		return Technologies.BIPLANE_FIGHTERS;
	}
}
