package com.watersfall.clocgame.model.producible.army.infantry;

import com.watersfall.clocgame.model.producible.IArmyPower;
import com.watersfall.clocgame.model.producible.Producible;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class RifledMusket implements Producible, IArmyPower
{
	@Override
	public ProducibleCategory getCategory()
	{
		return null;
	}

	@Override
	public Technology getTechnology()
	{
		return null;
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
		return 3.0;
	}
}
