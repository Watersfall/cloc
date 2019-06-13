package com.watersfall.clocmath.math;

import com.watersfall.clocmath.constants.PopulationConstants;
import com.watersfall.clocmath.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.SortedMap;

public class PopulationMath
{

	public static int getTotalManpower(ResultSet results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getTotalManpower(map);
	}

	public static int getAvailableManpower(ResultSet results) throws SQLException
	{
		Map<String, Object> map = Util.createMap(results);
		return getAvailableManpower(map);
	}

	public static int getTotalManpower(Map results)
	{
		return (int) (Integer.parseInt(results.get("population").toString()) * PopulationConstants.BASE_MANPOWER);
	}

	public static int getAvailableManpower(Map results) throws SQLException
	{
		return getTotalManpower(results) - (Integer.parseInt(results.get("army_home").toString()) * 1000);
	}
}
