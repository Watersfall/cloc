package com.watersfall.clocmath.math;

import com.watersfall.clocmath.constants.PopulationConstants;
import com.watersfall.clocmath.util.Util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FoodMath
{

	public static double getNetFood(ResultSet... results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getNetFood(map);
	}

	public static double getFoodProduction(ResultSet... results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getFoodProduction(map);
	}

	public static double getStandardFoodCost(ResultSet... results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getStandardFoodCost(map);
	}

	public static double getNetFood(Map results)
	{
		return getFoodProduction(results) - getStandardFoodCost(results);
	}

	public static double getFoodProduction(Map results)
	{
		return (Double.parseDouble(results.get("land").toString()) * PopulationConstants.FOOD_PER_LAND);
	}

	public static double getStandardFoodCost(Map results)
	{
		int population = Integer.parseInt(results.get("population").toString());
		return (population * PopulationConstants.POP_FOOD_COST);
	}
}