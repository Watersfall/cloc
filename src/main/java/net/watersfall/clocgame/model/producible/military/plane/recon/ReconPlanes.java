package net.watersfall.clocgame.model.producible.military.plane.recon;

import net.watersfall.clocgame.model.producible.IReconPower;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.model.technology.Technology;

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
