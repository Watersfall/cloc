package com.watersfall.clocgame.model.producible.military.plane.recon;

import com.watersfall.clocgame.model.producible.IReconPower;
import com.watersfall.clocgame.model.producible.Producible;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class ReconPlanes implements Producible, IReconPower
{
	@Override public ProducibleCategory getCategory()
	{
		return ProducibleCategory.RECON_PLANE;
	}

	@Override public Technology getTechnology()
	{
		return Technologies.RECON_BIPLANE.getTechnology();
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
}
