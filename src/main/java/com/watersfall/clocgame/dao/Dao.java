package com.watersfall.clocgame.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Dao
{
	static final String WRITE_ACCESS_SQL_STATEMENT = "FOR UPDATE\n";

	protected Connection connection;
	protected boolean allowWriteAccess;

	protected Dao(Connection connection, boolean allowWriteAccess)
	{
		this.connection = connection;
		this.allowWriteAccess = allowWriteAccess;
	}

	protected void requireWriteAccess() throws SQLException
	{
		if(!allowWriteAccess)
		{
			throw new SQLException("Write access required!");
		}
	}
}
