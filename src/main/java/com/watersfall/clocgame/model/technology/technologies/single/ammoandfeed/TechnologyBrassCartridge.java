package com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBrassCartridge extends SingleTechnology
{
	private static TechnologyBrassCartridge technologyBrassCartridge;
	public static final String NAME = "Brass Cartridge";
	public static final String DESC = "Brass Cartridge";
	public static final String COLUMN_NAME = "brass_cartridge_tech";
	public static final String FIELD_NAME = "BrassCartridge";

	private TechnologyBrassCartridge()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.PAPER_CARTRIDGE);
		costs.put("research", 50);
		costs.put("steel", 25);
		costs.put("nitrogen", 25);
	}

	public static TechnologyBrassCartridge getInstance()
	{
		if(technologyBrassCartridge == null)
		{
			technologyBrassCartridge = new TechnologyBrassCartridge();
		}
		return technologyBrassCartridge;
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
		return Technologies.BRASS_CARTRIDGE;
	}
}

