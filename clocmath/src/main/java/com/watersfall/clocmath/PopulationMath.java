package com.watersfall.clocmath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class PopulationMath
{

    public static int getPopulation(ResultSet resultsPopulation) throws SQLException
    {
        return resultsPopulation.getInt("northAmerican")
                + resultsPopulation.getInt("southAmerican")
                + resultsPopulation.getInt("african")
                + resultsPopulation.getInt("european")
                + resultsPopulation.getInt("asian")
                + resultsPopulation.getInt("middleEastern")
                + resultsPopulation.getInt("oceanian");
    }

    public static int getTotalManpower(ResultSet resultsPopulation) throws SQLException
    {
        return (int)(getPopulation(resultsPopulation) * PopulationConstants.BASE_MANPOWER);
    }

    public static int getAvailableManpower(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
    {
        return getTotalManpower(resultsPopulation) - resultsMain.getInt("army") * 1000;
    }

    public static int getPopulation(SortedMap resultsPopulation)
    {
        return Integer.parseInt(resultsPopulation.get("northAmerican").toString())
                + Integer.parseInt(resultsPopulation.get("southAmerican").toString())
                + Integer.parseInt(resultsPopulation.get("african").toString())
                + Integer.parseInt(resultsPopulation.get("european").toString())
                + Integer.parseInt(resultsPopulation.get("asian").toString())
                + Integer.parseInt(resultsPopulation.get("middleEastern").toString())
                + Integer.parseInt(resultsPopulation.get("oceanian").toString());
    }

    public static int getTotalManpower(SortedMap resultsPopulation)
    {
        return (int)(getPopulation(resultsPopulation) * PopulationConstants.BASE_MANPOWER);
    }

    public static int getAvailableManpower(SortedMap resultsMain, SortedMap resultsPopulation) throws SQLException
    {
        return getTotalManpower(resultsPopulation) - (Integer.parseInt(resultsMain.get("army").toString()) * 1000);
    }
}
