package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyZeppelinBombers extends SingleTechnology
{
	private static TechnologyZeppelinBombers technologyZeppelinBombers;
	public static final String NAME = "Zeppelin Bombers";
	public static final String DESC = "Zeppelin Bombers";
	public static final String COLUMN_NAME = "zeppelin_bombers_tech";
	public static final String FIELD_NAME = "ZeppelinBombers";
	private @Getter String productionName = "zeppelins";

	private TechnologyZeppelinBombers()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.RECON_BALLOONS);
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
	}

	public static TechnologyZeppelinBombers getInstance()
	{
		if(technologyZeppelinBombers == null)
		{
			technologyZeppelinBombers = new TechnologyZeppelinBombers();
		}
		return technologyZeppelinBombers;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 20.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		map.put("oil", 2);
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
		return Technologies.ZEPPELIN_BOMBERS;
	}
}
