package com.watersfall.clocmath.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util
{
	public static Map<String, Object> createMap(ResultSet... resultSet) throws SQLException
	{
		Map<String, Object> map = new HashMap<>();
		for(int o = 0; o < resultSet.length; o++)
		{
			ResultSetMetaData md = resultSet[o].getMetaData();
			for(int i = 0; i < md.getColumnCount(); i++)
			{
				map.put(md.getColumnName(i), resultSet[o].getObject(i));
			}
		}
		return map;

	}
}
