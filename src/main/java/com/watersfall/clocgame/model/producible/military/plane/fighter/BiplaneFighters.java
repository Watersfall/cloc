package com.watersfall.clocgame.model.producible.military.plane.fighter;

import com.watersfall.clocgame.model.producible.IFighterPower;
import com.watersfall.clocgame.model.producible.Producible;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class BiplaneFighters implements Producible, IFighterPower
{
	@Override public ProducibleCategory getCategory()
	{
		return ProducibleCategory.FIGHTER_PLANE;
	}

	@Override public Technology getTechnology()
	{
		return Technologies.BIPLANE_FIGHTERS.getTechnology();
	}

	@Override
	public double getProductionICCost()
	{
		return 24.0;
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
