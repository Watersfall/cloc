package net.watersfall.clocgame.model.producible.military.plane.bomber;

import net.watersfall.clocgame.model.producible.*;
import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class Bombers implements Producible, IFighterPower, IBomberPower
{
	@Override
	public ProducibleCategory getCategory()
	{
		return ProducibleCategory.BOMBER_PLANE;
	}

	@Override
	public Technology getTechnology()
	{
		return Technologies.BOMBERS.getTechnology();
	}

	@Override
	public Producibles getEnumValue()
	{
		return Producibles.BOMBERS;
	}

	@Override
	public double getProductionICCost()
	{
		return 45.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 2);
		map.put("oil", 2);
		return map;
	}
}
