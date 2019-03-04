package com.watersfall.cloc.math;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class PopGrowthCalc
{
    public static final int MINE_POPULATION = 10000;
    public static final int WELL_POPULATION = 10000;
    public static final int FACTORY_POPULATION = 100000;
    public static final int FARM_POPULATION = 1000;
    
    public static final double BASE_GROWTH = 0.2d;
    
    public static double getPopGrowth(ResultSet results) throws SQLException
    {
        return getPopGrowthFromEmployment(results) + getPopGrowthFromFoodProduction(results) + BASE_GROWTH;
    }
    
    public static double getPopGrowthFromEmployment(ResultSet results) throws SQLException
    {
        int population = results.getInt("population");
        int jobs = results.getInt("mines") * MINE_POPULATION +
                   results.getInt("wells") * WELL_POPULATION + 
                   results.getInt("industry") * FACTORY_POPULATION +
                   results.getInt("milindustry") * FACTORY_POPULATION +
                   results.getInt("nitrogenplant") * FACTORY_POPULATION;
        return (jobs - population) / jobs;
    }
    
    public static double getPopGrowthFromFoodProduction(ResultSet results) throws SQLException
    {
        int netFood = (int)(results.getInt("land") * FoodCalc.FOOD_PER_LAND) - FoodCalc.getStandardFoodCost(results);
        return Math.log10(netFood) / 25;
    }
    
    public static double getPopGrowth(SortedMap results) throws SQLException
    {
        return getPopGrowthFromEmployment(results) + getPopGrowthFromFoodProduction(results) + BASE_GROWTH;
    }
    
    public static double getPopGrowthFromEmployment(SortedMap results) throws SQLException
    {
        int population = Integer.parseInt(results.get("population").toString());
        int jobs = Integer.parseInt(results.get("mines").toString()) * MINE_POPULATION +
                   Integer.parseInt(results.get("wells").toString()) * WELL_POPULATION +
                   Integer.parseInt(results.get("industry").toString()) * FACTORY_POPULATION +
                   Integer.parseInt(results.get("milindustry").toString()) * FACTORY_POPULATION +
                   Integer.parseInt(results.get("nitrogenplant").toString()) * FACTORY_POPULATION;
        return (jobs - population > 0) ? Math.log(Math.sqrt(jobs - population)) : -1 * Math.log(Math.sqrt(Math.abs(jobs - population)));
    }
    
    public static double getPopGrowthFromFoodProduction(SortedMap results) throws SQLException
    {
        int netFood = (int)(Integer.parseInt(results.get("land").toString()) * FoodCalc.FOOD_PER_LAND) - FoodCalc.getStandardFoodCost(results);
        return Math.log(netFood) / 10;
    }
}