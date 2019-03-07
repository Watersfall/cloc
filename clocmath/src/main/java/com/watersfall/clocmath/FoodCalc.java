package com.watersfall.clocmath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class FoodCalc
{
    public static int getNetFood(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        return getFoodProduction(resultsMain) - getStandardFoodCost(resultsPopulation);
    }
    
    public static int getFoodProduction(ResultSet results) throws SQLException
    {
        return (int)(results.getInt("land") * Constants.FOOD_PER_LAND);
    }
    
    public static int getStandardFoodCost(ResultSet resultsPopulation) throws SQLException
    {
        int population = resultsPopulation.getInt("northAmerican") + 
                        resultsPopulation.getInt("southAmerican") + 
                        resultsPopulation.getInt("african") + 
                        resultsPopulation.getInt("european") + 
                        resultsPopulation.getInt("asian") + 
                        resultsPopulation.getInt("middleEastern") + 
                        resultsPopulation.getInt("oceanian");
        return (int)(population * Constants.POP_FOOD_COST);
    }
    
    public static int getNetFood(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        return getFoodProduction(resultsMain) - getStandardFoodCost(resultsPopulation);
    }
    
    public static int getFoodProduction(SortedMap results) throws SQLException
    {
        return (int)(Integer.parseInt(results.get("land").toString()) * Constants.FOOD_PER_LAND);
    }
    
    public static int getStandardFoodCost(SortedMap resultsPopulation) throws SQLException
    {
        int population = Integer.parseInt(resultsPopulation.get("northAmerican").toString()) + 
                        Integer.parseInt(resultsPopulation.get("southAmerican").toString()) + 
                        Integer.parseInt(resultsPopulation.get("african").toString()) + 
                        Integer.parseInt(resultsPopulation.get("european").toString()) + 
                        Integer.parseInt(resultsPopulation.get("asian").toString()) + 
                        Integer.parseInt(resultsPopulation.get("middleEastern").toString()) + 
                        Integer.parseInt(resultsPopulation.get("oceanian").toString());
        return (int)(population * Constants.POP_FOOD_COST);
    }
}