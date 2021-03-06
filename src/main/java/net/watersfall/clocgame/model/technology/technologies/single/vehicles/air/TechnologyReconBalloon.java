package net.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.SingleTechnology;
import net.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyReconBalloon extends SingleTechnology
{
	private static TechnologyReconBalloon technologyReconBalloon;
	public static final String NAME = "Recon Balloons";
	public static final String DESC = "Recon Balloons";
	public static final String COLUMN_NAME = "recon_balloon_tech";
	public static final String FIELD_NAME = "ReconBalloons";
	private @Getter String productionName = "recon_balloons";

	private TechnologyReconBalloon()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
		effects.add("Unlocks production: Recon Balloons");
	}

	public static TechnologyReconBalloon getInstance()
	{
		if(technologyReconBalloon == null)
		{
			technologyReconBalloon = new TechnologyReconBalloon();
		}
		return technologyReconBalloon;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.RECON_BALLOONS.getProducible();
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.RECON_BALLOONS;
	}
}
