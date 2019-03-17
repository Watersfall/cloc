package com.watersfall.clocmath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class PopGrowthMath
{
    
    public static double getPopGrowth(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        return getPopGrowthFromEmployment(resultsMain, resultsPopulation) + getPopGrowthFromFoodProduction(resultsMain, resultsPopulation) + PopulationConstants.BASE_GROWTH;
    }
    
    public static double getPopGrowthFromEmployment(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        int population = PopulationMath.getPopulation(resultsPopulation);
        int jobs = resultsMain.getInt("mines") * PopulationConstants.MINE_POPULATION +
                   resultsMain.getInt("wells") * PopulationConstants.WELL_POPULATION +
                   resultsMain.getInt("industry") * PopulationConstants.FACTORY_POPULATION +
                   resultsMain.getInt("milindustry") * PopulationConstants.FACTORY_POPULATION +
                   resultsMain.getInt("nitrogenplant") * PopulationConstants.FACTORY_POPULATION;
        return getPopGrowthFromEmployment(jobs, population);
    }
    
    public static double getPopGrowthFromFoodProduction(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        int netFood = FoodMath.getNetFood(resultsMain, resultsPopulation);
        return getPopGrowthFromFoodProduction(netFood);
    }
    
    public static double getPopGrowth(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        return getPopGrowthFromEmployment(resultsMain, resultsPopulation) + getPopGrowthFromFoodProduction(resultsMain, resultsPopulation) + PopulationConstants.BASE_GROWTH;
    }
    
    public static double getPopGrowthFromEmployment(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        int population = PopulationMath.getPopulation(resultsPopulation);
        int jobs = Integer.parseInt(resultsMain.get("mines").toString()) * PopulationConstants.MINE_POPULATION +
                   Integer.parseInt(resultsMain.get("wells").toString()) * PopulationConstants.WELL_POPULATION +
                   Integer.parseInt(resultsMain.get("industry").toString()) * PopulationConstants.FACTORY_POPULATION +
                   Integer.parseInt(resultsMain.get("milindustry").toString()) * PopulationConstants.FACTORY_POPULATION +
                   Integer.parseInt(resultsMain.get("nitrogenplant").toString()) * PopulationConstants.FACTORY_POPULATION;
        return getPopGrowthFromEmployment(jobs, population);
    }
    
    public static double getPopGrowthFromFoodProduction(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        int netFood = FoodMath.getNetFood(resultsMain, resultsPopulation);
        return getPopGrowthFromFoodProduction(netFood);
    }
    
    public static double getPopGrowthFromFoodProduction(int netFood)
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