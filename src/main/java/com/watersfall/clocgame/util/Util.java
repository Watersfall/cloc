package com.watersfall.clocgame.util;

import com.watersfall.clocgame.database.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Util
{
	public static int turn = 0;

	public static Map<String, Object> createMap(ResultSet... resultSet) throws SQLException
	{
		Map<String, Object> map = new HashMap<>();
		for(int o = 0; o < resultSet.length; o++)
		{
			ResultSetMetaData md = resultSet[o].getMetaData();
			for(int i = 1; i <= md.getColumnCount(); i++)
			{
				map.put(md.getColumnName(i), resultSet[o].getObject(i));
			}
		}
		return map;
	}

	public static ArrayList<Map<String, Object>> createMapArray(ResultSet resultSet) throws SQLException
	{
		ArrayList<Map<String, Object>> array = new ArrayList<>();
		Map<String, Object> map;
		ResultSetMetaData md = resultSet.getMetaData();
		while(resultSet.next())
		{
			map = new HashMap<>();
			for(int i = 1; i <= md.getColumnCount(); i++)
			{
				map.put(md.getColumnName(i), resultSet.getObject(i));
			}
			array.add(map);
		}
		return array;
	}

	public static Map<String, Object> createTotalsMap(ResultSet resultSet) throws SQLException
	{
		HashMap<String, Object> map = new HashMap<>();
		ResultSetMetaData md = resultSet.getMetaData();
		resultSet.first();
		for(int i = 1; i <= md.getColumnCount(); i++)
		{
			map.put(md.getColumnName(i), resultSet.getObject(i));
		}
		while(resultSet.next())
		{
			for(int i = 1; i <= md.getColumnCount(); i++)
			{
				map.replace(md.getColumnName(i), Double.toString(Double.parseDouble(map.get(md.getColumnName(i)).toString()) + Double.parseDouble(resultSet.getObject(i).toString())));
			}
		}
		return map;
	}
}
