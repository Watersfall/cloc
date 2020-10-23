package net.watersfall.clocgame.model.producible.army.tank;

import net.watersfall.clocgame.model.producible.IArmyPower;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class Tank implements Producible, IArmyPower
{
	@Override
	public ProducibleCategory getCategory()
	{
		return ProducibleCategory.TANK;
	}

	@Override
	public Technology getTechnology()
	{
		return Technologies.TANK.getTechnology();
	}

	@Override
	public Producibles getEnumValue()
	{
		return Producibles.TANK;
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
		map.put("steel", 3);
		map.put("oil", 2);
		return map;
	}

	@Override
	public double getArmyPower()
	{
		return 1.0;
	}
}
