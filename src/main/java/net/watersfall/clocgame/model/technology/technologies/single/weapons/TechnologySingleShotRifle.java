package net.watersfall.clocgame.model.technology.technologies.single.weapons;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologySingleShotRifle extends SingleTechnology
{
	private static TechnologySingleShotRifle technologySingleShotRifle;
	public static final String NAME = "Single Shot Rifles";
	public static final String DESC = "Single Shot Rifles";
	public static final String COLUMN_NAME = "single_shot_rifle_tech";
	public static final String FIELD_NAME = "SingleShot";
	private @Getter String productionName = "single_shot";

	private TechnologySingleShotRifle()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		costs.put("steel", 10);
		effects.add("Unlocks production: Single Shot Rifles");
		prerequisites.add(Technologies.RIFLED_MUSKET);
	}

	public static TechnologySingleShotRifle getInstance()
	{
		if(technologySingleShotRifle == null)
		{
			technologySingleShotRifle = new TechnologySingleShotRifle();
		}
		return technologySingleShotRifle;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.SINGLE_SHOT.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.SINGLE_SHOT_RIFLE;
	}
}
