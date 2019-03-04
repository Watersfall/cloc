package com.watersfall.cloc.math;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class FoodCalc
{
    public static final double POP_FOOD_COST = 1/1000d;
    public static final double FOOD_PER_LAND = 1/200d;
    
    public static int getFoodProduction(ResultSet results) throws SQLException
    {
        return (int)(results.getInt("land") * FOOD_PER_LAND);
    }
    
    public static int getStandardFoodCost(ResultSet results) throws SQLException
    {
        int pop = results.getInt("population");
        return (int)(pop * POP_FOOD_COST);
    }
    
    public static int getFoodProduction(SortedMap results) throws SQLException
    {
        return (int)(Integer.parseInt(results.get("land").toString()) * FOOD_PER_LAND);
    }
    
    public static int getStandardFoodCost(SortedMap results) throws SQLException
    {
        return (int)(Integer.parseInt(results.get("population").toString()) * POP_FOOD_COST);
    }
}