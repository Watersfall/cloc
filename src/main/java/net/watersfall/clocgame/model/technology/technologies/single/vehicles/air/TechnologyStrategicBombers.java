package net.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyStrategicBombers extends SingleTechnology
{
	private static TechnologyStrategicBombers technologyStrategicBombers;
	public static final String NAME = "Strategic Bombing";
	public static final String DESC = "Strategic Bombing";
	public static final String COLUMN_NAME = "strategic_bombing_tech";
	public static final String FIELD_NAME = "StratBombing";

	private TechnologyStrategicBombers()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BOMBERS);
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
		effects.add("Unlocks production: Strategic Bombers");
	}

	public static TechnologyStrategicBombers getInstance()
	{
		if(technologyStrategicBombers == null)
		{
			technologyStrategicBombers = new TechnologyStrategicBombers();
		}
		return technologyStrategicBombers;
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
		return Technologies.STRATEGIC_BOMBERS;
	}
}
