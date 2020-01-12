package com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyPaperCartridge extends SingleTechnology
{
	private static TechnologyPaperCartridge technologyPaperCartridge;
	public static final String NAME = "Paper Cartridge";
	public static final String DESC = "Paper Cartridge";
	public static final String COLUMN_NAME = "paper_cartridge_tech";
	public static final String FIELD_NAME = "PaperCartridge";

	private TechnologyPaperCartridge()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.SMOKELESS_POWDER);
		costs.put("research", 50);
		costs.put("steel", 20);
		costs.put("nitrogen", 20);
	}

	public static TechnologyPaperCartridge getInstance()
	{
		if(technologyPaperCartridge == null)
		{
			technologyPaperCartridge = new TechnologyPaperCartridge();
		}
		return technologyPaperCartridge;
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
		return Technologies.PAPER_CARTRIDGE;
	}
}

