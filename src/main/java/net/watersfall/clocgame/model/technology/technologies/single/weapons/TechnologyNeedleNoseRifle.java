package net.watersfall.clocgame.model.technology.technologies.single.weapons;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyNeedleNoseRifle extends SingleTechnology
{
	private static TechnologyNeedleNoseRifle technologyNeedleNoseRifle;
	public static final String NAME = "Needle Nosed Rifles";
	public static final String DESC = "Needle Nosed Rifles";
	public static final String COLUMN_NAME = "needle_nose_rifle_tech";
	public static final String FIELD_NAME = "NeedleNose";
	private @Getter String productionName = "needle_nose";

	private TechnologyNeedleNoseRifle()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.SINGLE_SHOT_RIFLE);
		costs.put("research", 50);
		costs.put("steel", 10);
		effects.add("Unlocks production: Needle Nose Rifles");
	}

	public static TechnologyNeedleNoseRifle getInstance()
	{
		if(technologyNeedleNoseRifle == null)
		{
			technologyNeedleNoseRifle = new TechnologyNeedleNoseRifle();
		}
		return technologyNeedleNoseRifle;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.NEEDLE_NOSE.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.NEEDLE_NOSE_RIFLE;
	}
}
