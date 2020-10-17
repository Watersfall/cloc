package com.watersfall.clocgame.model.producible.army.infantry;

import com.watersfall.clocgame.model.producible.IArmyPower;
import com.watersfall.clocgame.model.producible.Producible;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public class MachineGun implements Producible, IArmyPower
{
	@Override public ProducibleCategory getCategory()
	{
		return ProducibleCategory.INFANTRY_EQUIPMENT;
	}

	@Override public Technology getTechnology()
	{
		return Technologies.MACHINE_GUN.getTechnology();
	}

	@Override
	public double getProductionICCost()
	{
		return 0.75;
	}

	@Override
	public LinkedHashMap<String, Integer> getProductionResourceCost()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("steel", 3);
		return map;
	}

	@Override
	public double getArmyPower()
	{
		return 2;
	}
}
