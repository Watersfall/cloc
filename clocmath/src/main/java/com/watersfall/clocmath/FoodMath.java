package com.watersfall.clocmath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class FoodMath
{
    public static int getNetFood(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        return getFoodProduction(resultsMain) - getStandardFoodCost(resultsPopulation);
    }
    
    public static int getFoodProduction(ResultSet results) throws SQLException
    {
        return (int)(results.getInt("land") * PopulationConstants.FOOD_PER_LAND);
    }
    
    public static int getStandardFoodCost(ResultSet resultsPopulation) throws SQLException
    {
        int population = PopulationMath.getPopulation(resultsPopulation);
        return (int)(population * PopulationConstants.POP_FOOD_COST);
    }
    
    public static int getNetFood(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        return getFoodProduction(resultsMain) - getStandardFoodCost(resultsPopulation);
    }
    
    public static int getFoodProduction(SortedMap results) throws SQLException
    {
        return (int)(Integer.parseInt(results.get("land").toString()) * PopulationConstants.FOOD_PER_LAND);
    }
    
    public static int getStandardFoodCost(SortedMap resultsPopulation) throws SQLException
    {
        int population = PopulationMath.getPopulation(resultsPopulation);
        return (int)(population * PopulationConstants.POP_FOOD_COST);
    }
}