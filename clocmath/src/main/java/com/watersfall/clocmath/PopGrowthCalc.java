package com.watersfall.clocmath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class PopGrowthCalc
{
    
    public static double getPopGrowth(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        return getPopGrowthFromEmployment(resultsMain, resultsPopulation) + getPopGrowthFromFoodProduction(resultsMain, resultsPopulation) + Constants.BASE_GROWTH;
    }
    
    public static double getPopGrowthFromEmployment(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        int population = resultsPopulation.getInt("northAmerican") + 
                        resultsPopulation.getInt("southAmerican") + 
                        resultsPopulation.getInt("african") + 
                        resultsPopulation.getInt("european") + 
                        resultsPopulation.getInt("asian") + 
                        resultsPopulation.getInt("middleEastern") + 
                        resultsPopulation.getInt("oceanian");
        int jobs = resultsMain.getInt("mines") * Constants.MINE_POPULATION +
                   resultsMain.getInt("wells") * Constants.WELL_POPULATION + 
                   resultsMain.getInt("industry") * Constants.FACTORY_POPULATION +
                   resultsMain.getInt("milindustry") * Constants.FACTORY_POPULATION +
                   resultsMain.getInt("nitrogenplant") * Constants.FACTORY_POPULATION;
        return getPopGrowthFromEmployment(jobs, population);
    }
    
    public static double getPopGrowthFromFoodProduction(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        int netFood = FoodCalc.getNetFood(resultsMain, resultsPopulation);
        return getPopGrowthFromFoodProduction(netFood);
    }
    
    public static double getPopGrowth(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        return getPopGrowthFromEmployment(resultsMain, resultsPopulation) + getPopGrowthFromFoodProduction(resultsMain, resultsPopulation) + Constants.BASE_GROWTH;
    }
    
    public static double getPopGrowthFromEmployment(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        int population = Integer.parseInt(resultsPopulation.get("northAmerican").toString()) + 
                        Integer.parseInt(resultsPopulation.get("southAmerican").toString()) + 
                        Integer.parseInt(resultsPopulation.get("african").toString()) + 
                        Integer.parseInt(resultsPopulation.get("european").toString()) + 
                        Integer.parseInt(resultsPopulation.get("asian").toString()) + 
                        Integer.parseInt(resultsPopulation.get("middleEastern").toString()) + 
                        Integer.parseInt(resultsPopulation.get("oceanian").toString());
        int jobs = Integer.parseInt(resultsMain.get("mines").toString()) * Constants.MINE_POPULATION +
                   Integer.parseInt(resultsMain.get("wells").toString()) * Constants.WELL_POPULATION +
                   Integer.parseInt(resultsMain.get("industry").toString()) * Constants.FACTORY_POPULATION +
                   Integer.parseInt(resultsMain.get("milindustry").toString()) * Constants.FACTORY_POPULATION +
                   Integer.parseInt(resultsMain.get("nitrogenplant").toString()) * Constants.FACTORY_POPULATION;
        return getPopGrowthFromEmployment(jobs, population);
    }
    
    public static double getPopGrowthFromFoodProduction(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        int netFood = FoodCalc.getNetFood(resultsMain, resultsPopulation);
        return getPopGrowthFromFoodProduction(netFood);
    }
    
    public static double getPopGrowthFromFoodProduction(int netFood)
    {
        return Math.log(Math.pow(netFood, 2)) / 10 / 100;
    }
    
    public static double getPopGrowthFromEmployment(int jobs, int population)
    {
        return Math.log(Math.sqrt(jobs - population)) / 100;
    }
}