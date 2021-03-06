package net.watersfall.clocgame.model.technology.technologies.single.weapons;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyRifledMusket extends SingleTechnology
{
	private static TechnologyRifledMusket technologyRifledMusket;
	public static final String NAME = "Rifled Muskets";
	public static final String DESC = "Rifled Muskets";
	public static final String COLUMN_NAME = "rifled_musket_tech";
	public static final String FIELD_NAME = "RifledMusket";
	private @Getter String productionName = "rifled_musket";

	private TechnologyRifledMusket()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 50);
		costs.put("steel", 10);
		prerequisites.add(Technologies.MUSKET);
		effects.add("Unlocks production: Rifled Muskets");
	}

	public static TechnologyRifledMusket getInstance()
	{
		if(technologyRifledMusket == null)
		{
			technologyRifledMusket = new TechnologyRifledMusket();
		}
		return technologyRifledMusket;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.RIFLED_MUSKET.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.RIFLED_MUSKET;
	}
}
