package com.watersfall.clocmath;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImmigrationMath
{
	/**
	 * @param resultsMain       ResultSet containing all cloc rows
	 * @param resultsPopulation ResultSet containing all cloc_population rows
	 * @return An Array containing the total immigrants from each region
	 * @throws SQLException if something is wrong with either ResultSet
	 */
	public static int[] getTotalEmigration(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
	{
		int[] total = {0, 0, 0, 0, 0, 0, 0};
		while(resultsMain.next() && resultsPopulation.next())
		{
			double leavingPercent = PopGrowthMath.getPopGrowthFromEmployment(resultsMain, resultsPopulation);
			if(leavingPercent <= 0)
			{
				for(int i = 0; i < total.length; i++)
				{
					total[i] += (int) Math.abs((leavingPercent * resultsPopulation.getInt(i)));
				}
			}
		}
		return total;
	}

	/**
	 * @param conn Connection to pull results from
	 * @return An Array containing the total immigrants from each region
	 * @throws SQLException if something is wrong with either ResultSet
	 */
	public static int[] getTotalEmigration(Connection conn) throws SQLException
	{
		int[] total = {0, 0, 0, 0, 0, 0, 0};
		ResultSet resultsMain = conn.prepareStatement(
				"SELECT mines, wells, industry, milindustry, universities, nitrogenplants FROM cloc"
		).executeQuery();
		ResultSet resultsPopulation = conn.prepareStatement("SELECT * FROM cloc_population").executeQuery();
		return getTotalEmigration(resultsMain, resultsPopulation);
	}

	/**
	 * @param resultsMain       ResultSet containing all cloc rows
	 * @param resultsPopulation ResultSet containing all cloc_population rows
	 * @return An Array containing the total percentages of incoming immigration
	 * @throws SQLException if something is wrong with either ResultSet
	 */
	public static double[] getTotalImmigration(ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
	{
		double[] total = {0, 0, 0, 0, 0, 0, 0};
		while(resultsMain.next() && resultsPopulation.next())
		{
			double comingPercent = PopGrowthMath.getPopGrowthFromEmployment(resultsMain, resultsPopulation);
			if(comingPercent > 0)
			{
				for(int i = 0; i < total.length; i++)
				{
					total[i] += comingPercent;
				}
			}
		}
		return total;
	}

	/**
	 * @param resultsMainNation       ResultSet set to the nation to be calculated
	 * @param resultsPopulationNation ResultSet set to the nation population to be calculated
	 * @param resultsMain             ResultSet containing all cloc rows
	 * @param resultsPopulation       ResultSet containing all cloc_population rows
	 * @return An Array containing the immigrants entering a nation from each region
	 * @throws SQLException if something is wrong with either ResultSet
	 */
	public static int[] getNationImmigration(ResultSet resultsMainNation, ResultSet resultsPopulationNation, ResultSet resultsMain, ResultSet resultsPopulation) throws SQLException
	{
		double growth = PopGrowthMath.getPopGrowthFromEmployment(resultsMainNation, resultsPopulationNation);
		int[] total = {0, 0, 0, 0, 0, 0, 0};
		if(growth == 0) return total;
		int[] global = getTotalEmigration(resultsMain, resultsPopulation);
		double[] globalImmigration = getTotalImmigration(resultsMain, resultsPopulation);
		for(int i = 0; i < total.length; i++)
		{
			total[i] = (int) (global[i] * (globalImmigration[i] / growth));
		}
		return total;
	}
}
