package net.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyZeppelinBombers extends SingleTechnology
{
	private static TechnologyZeppelinBombers technologyZeppelinBombers;
	public static final String NAME = "Zeppelin Bombers";
	public static final String DESC = "Zeppelin Bombers";
	public static final String COLUMN_NAME = "zeppelin_bombers_tech";
	public static final String FIELD_NAME = "ZeppelinBombers";
	private @Getter String productionName = "zeppelins";

	private TechnologyZeppelinBombers()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.RECON_BALLOONS);
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
		effects.add("Unlocks production: Zeppelin Bombers");
	}

	public static TechnologyZeppelinBombers getInstance()
	{
		if(technologyZeppelinBombers == null)
		{
			technologyZeppelinBombers = new TechnologyZeppelinBombers();
		}
		return technologyZeppelinBombers;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.ZEPPELINS.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.ZEPPELIN_BOMBERS;
	}
}
