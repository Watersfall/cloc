package com.watersfall.clocgame.math;

import com.watersfall.clocgame.model.nation.Army;
import com.watersfall.clocgame.model.nation.NationMilitary;

import java.util.HashMap;

public class Costs
{
	public static HashMap<String, Integer> getShipCost(String type, NationMilitary military)
	{
		HashMap<String, Integer> map = new HashMap<>();
		type = type.toLowerCase();
		switch(type)
		{
			case "submarine":
				map.put("oil", com.watersfall.clocgame.constants.Costs.SHIP_SUBMARINE_COAL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_SUBMARINE_COAL * military.getSubmarines());
				map.put("steel", com.watersfall.clocgame.constants.Costs.SHIP_SUBMARINE_STEEL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_SUBMARINE_STEEL * military.getSubmarines());
				break;
			case "destroyer":
				map.put("oil", com.watersfall.clocgame.constants.Costs.SHIP_DESTROYER_COAL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_DESTROYER_COAL * military.getSubmarines());
				map.put("steel", com.watersfall.clocgame.constants.Costs.SHIP_DESTROYER_STEEL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_DESTROYER_STEEL * military.getSubmarines());
				break;
			case "cruiser":
				map.put("oil", com.watersfall.clocgame.constants.Costs.SHIP_CRUISER_COAL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_CRUISER_COAL * military.getSubmarines());
				map.put("steel", com.watersfall.clocgame.constants.Costs.SHIP_CRUISER_STEEL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_CRUISER_STEEL * military.getSubmarines());
				break;
			case "pre_battleship":
				map.put("oil", com.watersfall.clocgame.constants.Costs.SHIP_PRE_BATTLESHIP_COAL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_PRE_BATTLESHIP_COAL * military.getSubmarines());
				map.put("steel", com.watersfall.clocgame.constants.Costs.SHIP_PRE_BATTLESHIP_STEEL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_PRE_BATTLESHIP_STEEL * military.getSubmarines());
				break;
			case "battleship":
				map.put("oil", com.watersfall.clocgame.constants.Costs.SHIP_BATTLESHIP_COAL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_BATTLESHIP_COAL * military.getSubmarines());
				map.put("steel", com.watersfall.clocgame.constants.Costs.SHIP_BATTLESHIP_STEEL + com.watersfall.clocgame.constants.Costs.MULT_SHIP_BATTLESHIP_STEEL * military.getSubmarines());
				break;
		}
		return map;
	}

	public static int getTrainingCost(Army army)
	{
		return 0;
	}
}
