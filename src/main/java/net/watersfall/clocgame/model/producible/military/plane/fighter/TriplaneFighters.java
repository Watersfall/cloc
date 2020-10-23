package net.watersfall.clocgame.model.producible.military.plane.fighter;

import net.watersfall.clocgame.model.producible.IFighterPower;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class TriplaneFighters implements Producible, IFighterPower
{
	@Override public ProducibleCategory getCategory()
	{
		return ProducibleCategory.FIGHTER_PLANE;
	}

	@Override public Technology getTechnology()
	{
		return Technologies.TRIPLANE_FIGHTERS.getTechnology();
	}

	@Override
	public Producibles getEnumValue()
	{
		return Producibles.TRIPLANE_FIGHTERS;
	}

	@Override
	public double getProductionICCost()
	{
		return 25.0;
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
