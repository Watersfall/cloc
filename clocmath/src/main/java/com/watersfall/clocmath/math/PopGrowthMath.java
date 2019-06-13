package com.watersfall.clocmath.math;

import com.watersfall.clocmath.constants.PopulationConstants;
import com.watersfall.clocmath.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.SortedMap;

public class PopGrowthMath
{

	public static double getPopGrowth(ResultSet resultsMain, ResultSet resultsDomestic) throws SQLException
	{
		Map<String, Object> map = Util.createMap(resultsMain, resultsDomestic);
		return getPopGrowth(map);
	}

	public static double getPopGrowthFromEmployment(ResultSet resultsMain, ResultSet resultsDomestic) throws SQLException
	{
		int population = resultsDomestic.getInt("population");
		int jobs = resultsMain.getInt("iron_mines") * PopulationConstants.MINE_POPULATION +
				resultsMain.getInt("coal_mines") * PopulationConstants.MINE_POPULATION +
				resultsMain.getInt("wells") * PopulationConstants.WELL_POPULATION +
				resultsMain.getInt("civilian_industry") * PopulationConstants.FACTORY_POPULATION +
				resultsMain.getInt("military_industry") * PopulationConstants.FACTORY_POPULATION;
		return getPopGrowthFromEmployment(jobs, population);
	}

	public static double getPopGrowthFromFoodProduction(ResultSet resultsMain) throws SQLException
	{
		double netFood = FoodMath.getNetFood(resultsMain);
		return getPopGrowthFromFoodProduction(netFood);
	}

	public static double getPopGrowth(Map results) throws SQLException
	{
		return getPopGrowthFromEmployment(results) + getPopGrowthFromFoodProduction(results) + PopulationConstants.BASE_GROWTH;
	}

	public static double getPopGrowthFromEmployment(Map results) throws SQLException
	{
		int population = Integer.parseInt(results.get("population").toString());
		int jobs = Integer.parseInt(results.get("iron_mines").toString()) * PopulationConstants.MINE_POPULATION +
				Integer.parseInt(results.get("coal_mines").toString()) * PopulationConstants.MINE_POPULATION +
				Integer.parseInt(results.get("oil_wells").toString()) * PopulationConstants.WELL_POPULATION +
				Integer.parseInt(results.get("civilian_industry").toString()) * PopulationConstants.FACTORY_POPULATION +
				Integer.parseInt(results.get("military_industry").toString()) * PopulationConstants.FACTORY_POPULATION;
		return getPopGrowthFromEmployment(jobs, population);
	}

	public static double getPopGrowthFromFoodProduction(Map results) throws SQLException
	{
		double netFood = FoodMath.getNetFood(results);
		return getPopGrowthFromFoodProduction(netFood);
	}

	public static double getPopGrowthFromFoodProduction(double netFood)
	{
		if(netFood > 0)
			return Math.log(Math.pow(netFood, 3)) / 10 / 100;
		else
			return Math.log(netFood) / 100d;
	}

	public static double getPopGrowthFromEmployment(int jobs, int population)
	{
		return Math.sqrt(Math.log(jobs - population)) / 100;
	}
}