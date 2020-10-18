package net.watersfall.clocgame.model.producible.army.artillery;

import net.watersfall.clocgame.model.producible.IArmyPower;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class Artillery implements Producible, IArmyPower
{
	@Override
	public ProducibleCategory getCategory()
	{
		return ProducibleCategory.ARTILLERY;
	}

	@Override
	public Technology getTechnology()
	{
		return Technologies.ARTILLERY.getTechnology();
	}

	@Override
	public double getProductionICCost()
	{
		return 4;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		map.put("nitrogen", 1);
		return map;
	}

	@Override
	public double getArmyPower()
	{
		return 1.0;
	}
}
