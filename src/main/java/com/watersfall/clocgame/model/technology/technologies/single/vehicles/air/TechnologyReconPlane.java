package com.watersfall.clocgame.model.technology.technologies.single.vehicles.air;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TechnologyReconPlane extends SingleTechnology
{
	private static TechnologyReconPlane technologyReconPlane;
	public static final String NAME = "Recon Plane";
	public static final String DESC = "Recon Plane";
	public static final String COLUMN_NAME = "recon_plane_tech";
	public static final String FIELD_NAME = "ReconPlane";
	private @Getter String productionName = "recon_planes";

	private TechnologyReconPlane()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.RECON_BALLOONS);
		costs.put("research", 100);
		costs.put("steel", 100);
		costs.put("oil", 100);
	}

	public static TechnologyReconPlane getInstance()
	{
		if(technologyReconPlane == null)
		{
			technologyReconPlane = new TechnologyReconPlane();
		}
		return technologyReconPlane;
	}

	@Override
	public boolean isProducible()
	{
		return true;
	}

	@Override
	public double getProductionICCost()
	{
		return 30.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		map.put("oil", 1);
		return map;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.RECON_BIPLANE;
	}
}
