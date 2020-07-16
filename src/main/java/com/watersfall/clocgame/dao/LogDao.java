package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.military.LogType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogDao extends Dao
{
	private static final String CREATE_LOG_SQL_STATEMENT =
					"INSERT INTO cloc_war_logs (attacker, region, type, amount)\n" +
					"VALUES (?,?,?,?)\n";
	private static final String CHECK_LOG_SQL_STATEMENT =
					"SELECT id\n" +
					"FROM cloc_war_logs\n" +
					"WHERE attacker=? AND region=? AND type=?\n";

	public LogDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public void createLog(int id, Region region, LogType type, int amount) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_LOG_SQL_STATEMENT);
		statement.setInt(1, id);
		statement.setString(2, region.getName());
		statement.setString(3, type.name());
		statement.setInt(4, amount);
		statement.execute();
	}

	public boolean checkLog(int id, Region region, LogType type) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CHECK_LOG_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		statement.setInt(1, id);
		statement.setString(2, region.getName());
		statement.setString(3, type.name());
		ResultSet results = statement.executeQuery();
		return results.first();
	}
}
