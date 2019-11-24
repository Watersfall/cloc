package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyReconBalloon extends SingleTechnology
{
	private static TechnologyReconBalloon technologyReconBalloon;
	public static final String NAME = "Recon Balloons";
	public static final String DESC = "Recon Balloons";
	public static final String COLUMN_NAME = "recon_balloon_tech";
	public static final String FIELD_NAME = "ReconBalloons";

	private TechnologyReconBalloon()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
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
