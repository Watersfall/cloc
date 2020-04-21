package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyMusket extends SingleTechnology
{
	private static TechnologyMusket technologyMusket;
	public static final String NAME = "Muskets";
	public static final String DESC = "Muskets";
	public static final String COLUMN_NAME = "musket_tech";
	public static final String FIELD_NAME = "Musket";
	private @Getter String productionName = "musket";

	private TechnologyMusket()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		costs.put("steel", 10);
		effects.add("Unlocks production: Muskets");
	}

	public static TechnologyMusket getInstance()
	{
		if(technologyMusket == null)
		{
			technologyMusket = new TechnologyMusket();
		}
		return technologyMusket;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 0.05;
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
		return Technologies.MUSKET;
	}
}
