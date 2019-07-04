package com.watersfall.clocmath.math;

import com.watersfall.clocmath.constants.PopulationConstants;
import com.watersfall.clocmath.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.SortedMap;

public class PopGrowthMath
{

	public static double getPopGrowth(ResultSet... results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getPopGrowth(map);
	}

	public static double getPopGrowthFromEmployment(ResultSet... results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getPopGrowthFromEmployment(map);
	}

	public static double getPopGrowthFromFoodProduction(ResultSet... results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getPopGrowthFromEmployment(map);
	}

	public static double getPopGrowth(Map results)
	{
		return getPopGrowthFromEmployment(results) + getPopGrowthFromFoodProduction(results) + PopulationConstants.BASE_GROWTH;
	}

	public static double getPopGrowthFromEmployment(Map results)
	{
		int population = Integer.parseInt(results.get("population").toString());
		int jobs = Integer.parseInt(results.get("iron_mines").toString()) * PopulationConstants.MINE_POPULATION +
				Integer.parseInt(results.get("coal_mines").toString()) * PopulationConstants.MINE_POPULATION +
				Integer.parseInt(results.get("oil_wells").toString()) * PopulationConstants.WELL_POPULATION +
				Integer.parseInt(results.get("civilian_industry").toString()) * PopulationConstants.FACTORY_POPULATION +
				Integer.parseInt(results.get("military_industry").toString()) * PopulationConstants.FACTORY_POPULATION;
		return Math.sqrt(Math.log(jobs - population)) / 100;
	}

	public static double getPopGrowthFromFoodProduction(Map results)
	{
		double netFood = FoodMath.getNetFood(results);
		if(netFood > 0)
			return Math.log(Math.pow(netFood, 3)) / 10 / 100;
		else
			return Math.log(netFood) / 100d;
	}

}