package net.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBombers extends SingleTechnology
{
	private static TechnologyBombers technologyBombers;
	public static final String NAME = "Bombers";
	public static final String DESC = "Bombers";
	public static final String COLUMN_NAME = "bomber_tech";
	public static final String FIELD_NAME = "Bomber";
	private @Getter String productionName = "bombers";

	private TechnologyBombers()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BIPLANE_FIGHTERS);
		prerequisites.add(Technologies.ZEPPELIN_BOMBERS);
		costs.put("research", 100);
		costs.put("steel", 50);
		costs.put("oil", 50);
		effects.add("Unlocks production: Bombers");
	}

	public static TechnologyBombers getInstance()
	{
		if(technologyBombers == null)
		{
			technologyBombers = new TechnologyBombers();
		}
		return technologyBombers;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.BOMBERS.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.BOMBERS;
	}
}
