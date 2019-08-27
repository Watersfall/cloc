package com.watersfall.clocgame.turn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class Turn
{
	protected Connection connection;
	protected int offset;

	private void connect()
	{
		try
		{
			Properties connectionProps = new Properties();
			connectionProps.setProperty("user", "root");
			connectionProps.setProperty("password", System.getenv("CLOC_PASS"));
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloc", connectionProps);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param offset Time in seconds to delay running
	 */
	public Turn(int offset)
	{
		this.offset = offset;
		connect();
	}

	public abstract void doTurn();

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public int getOffset()
	{
		return this.offset;
	}
}
