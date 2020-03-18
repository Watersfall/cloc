package com.watersfall.clocgame.model.technology.technologies.single.artillery;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyArtillery extends SingleTechnology
{
	private static TechnologyArtillery technologyArtillery;
	public static final String NAME = "Artillery";
	public static final String DESC = "Artillery";
	public static final String COLUMN_NAME = "artillery_tech";
	public static final String FIELD_NAME = "Artillery";
	private @Getter String productionName = "artillery";


	private TechnologyArtillery()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		costs.put("steel", 50);
		costs.put("nitrogen", 50);
	}

	public static TechnologyArtillery getInstance()
	{
		if(technologyArtillery == null)
		{
			technologyArtillery = new TechnologyArtillery();
		}
		return technologyArtillery;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionCost()
	{
		return 2;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.ARTILLERY;
	}
}
