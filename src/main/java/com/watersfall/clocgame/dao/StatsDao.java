package com.watersfall.clocgame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsDao extends Dao
{
	private static final String GRAPH_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM global_stats_history\n" +
					"WHERE month>?\n" +
					"ORDER BY month ASC\n" +
					"LIMIT 20";

	public StatsDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public ResultSet getGraphData(long month) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GRAPH_SQL_STATEMENT);
		statement.setLong(1, month);
		return statement.executeQuery();
	}
}
