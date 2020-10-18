package net.watersfall.clocgame.dao;

import net.watersfall.clocgame.model.war.LogType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogDao extends Dao
{
	private static final String CREATE_LOG_SQL_STATEMENT =
					"INSERT INTO war_logs (attacker, type)\n" +
					"VALUES (?,?)\n";
	private static final String CHECK_LOG_SQL_STATEMENT =
					"SELECT id\n" +
					"FROM war_logs\n" +
					"WHERE attacker=? AND type=?\n";

	public LogDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public void createLog(int id, LogType type) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_LOG_SQL_STATEMENT);
		statement.setInt(1, id);
		statement.setString(2, type.name());
		statement.execute();
	}

	public boolean checkLog(int id, LogType type) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CHECK_LOG_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		statement.setInt(1, id);
		statement.setString(2, type.name());
		ResultSet results = statement.executeQuery();
		return results.first();
	}
}
