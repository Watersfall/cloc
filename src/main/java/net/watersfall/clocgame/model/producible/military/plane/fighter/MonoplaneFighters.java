package net.watersfall.clocgame.model.producible.military.plane.fighter;

import net.watersfall.clocgame.model.producible.IFighterPower;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class MonoplaneFighters implements Producible, IFighterPower
{
	@Override public ProducibleCategory getCategory()
	{
		return ProducibleCategory.FIGHTER_PLANE;
	}

	@Override public Technology getTechnology()
	{
		return Technologies.MONOPLANE_FIGHTERS.getTechnology();
	}

	@Override
	public Producibles getEnumValue()
	{
		return Producibles.MONOPLANE_FIGHTERS;
	}

	@Override
	public double getProductionICCost()
	{
		return 28.0;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		map.put("oil", 2);
		return map;
	}
}
