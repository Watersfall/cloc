package net.watersfall.clocgame.model.producible.army.infantry;

import net.watersfall.clocgame.model.producible.IArmyPower;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class RifledMusket implements Producible, IArmyPower
{
	@Override
	public ProducibleCategory getCategory()
	{
		return ProducibleCategory.INFANTRY_EQUIPMENT;
	}

	@Override
	public Technology getTechnology()
	{
		return Technologies.RIFLED_MUSKET.getTechnology();
	}

	@Override
	public double getProductionICCost()
	{
		return 0.1;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 1);
		return map;
	}

	@Override
	public double getArmyPower()
	{
		return 1.1;
	}
}
