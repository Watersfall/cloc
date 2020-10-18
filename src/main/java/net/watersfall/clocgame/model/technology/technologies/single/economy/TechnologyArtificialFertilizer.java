package net.watersfall.clocgame.model.technology.technologies.single.economy;

import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyArtificialFertilizer extends SingleTechnology
{
	private static TechnologyArtificialFertilizer technologyArtificialFertilizer;
	public static final String NAME = "Artificial Fertilizer";
	public static final String DESC = "Artificial Fertilizer";
	public static final String COLUMN_NAME = "artificial_fertilizer";
	public static final String FIELD_NAME = "";
	public static final double LAND_PER_NITROGEN = 5000.0;
	public static final double FOOD_GAIN = 1;

	private TechnologyArtificialFertilizer()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 75);
		costs.put("nitrogen", 25);
		prerequisites.add(Technologies.BASIC_ARTIFICIAL_FERTILIZER);
		effects.add("+" + (FOOD_GAIN * 100) + "% food production");
		effects.add("+1 nitrogen upkeep per " + LAND_PER_NITROGEN + "km<sup>2</sup> land");
	}

	public static TechnologyArtificialFertilizer getInstance()
	{
		if(technologyArtificialFertilizer == null)
		{
			technologyArtificialFertilizer = new TechnologyArtificialFertilizer();
		}
		return technologyArtificialFertilizer;
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
		return Technologies.ARTIFICIAL_FERTILIZER;
	}
}
