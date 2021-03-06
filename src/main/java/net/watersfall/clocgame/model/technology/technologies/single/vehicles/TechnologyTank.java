package net.watersfall.clocgame.model.technology.technologies.single.vehicles;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyTank extends SingleTechnology
{
	private static TechnologyTank technologyTank;
	public static final String NAME = "Tanks";
	public static final String DESC = "Tanks";
	public static final String COLUMN_NAME = "tank_tech";
	public static final String FIELD_NAME = "Tank";
	private @Getter String productionName = "tanks";

	private TechnologyTank()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
		effects.add("Unlocks production: Tanks");
	}

	public static TechnologyTank getInstance()
	{
		if(technologyTank == null)
		{
			technologyTank = new TechnologyTank();
		}
		return technologyTank;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.TANK.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.TANK;
	}
}
