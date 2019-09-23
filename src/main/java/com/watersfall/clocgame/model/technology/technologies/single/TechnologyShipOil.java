package com.watersfall.clocgame.model.technology.technologies.single;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyShipOil extends Technology
{
	private static TechnologyShipOil technologyShipOil;
	public static final String NAME = "Oil Fueled Ships";
	public static final String DESC = "Oil Fueled Ships";
	public static final String COLUMN_NAME = "ship_oil_tech";

	private TechnologyShipOil()
	{
		super(NAME, DESC, COLUMN_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
	}

	public static TechnologyShipOil getInstance()
	{
		if(technologyShipOil == null)
		{
			technologyShipOil = new TechnologyShipOil();
		}
		return technologyShipOil;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.SHIP_OIL;
	}
}