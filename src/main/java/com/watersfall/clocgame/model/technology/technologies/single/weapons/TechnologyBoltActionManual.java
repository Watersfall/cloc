package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.producible.Producible;
import com.watersfall.clocgame.model.producible.Producibles;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyBoltActionManual extends SingleTechnology
{
	private static TechnologyBoltActionManual technologyBoltActionManual;
	public static final String NAME = "Manually Loaded Bolt Action Rifles";
	public static final String DESC = "Manually Loaded Bolt Action Rifles";
	public static final String COLUMN_NAME = "bolt_action_manual_tech";
	public static final String FIELD_NAME = "BoltActionManual";
	private @Getter String productionName = "bolt_action_manual";

	private TechnologyBoltActionManual()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		costs.put("steel", 10);
		effects.add("Unlocks production: Clip Loaded Bolt Action Rifles");
		prerequisites.add(Technologies.SINGLE_SHOT_RIFLE);
	}

	public static TechnologyBoltActionManual getInstance()
	{
		if(technologyBoltActionManual == null)
		{
			technologyBoltActionManual = new TechnologyBoltActionManual();
		}
		return technologyBoltActionManual;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.BOLT_ACTION_MANUAL.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.BOLT_ACTION_MANUAL;
	}
}
