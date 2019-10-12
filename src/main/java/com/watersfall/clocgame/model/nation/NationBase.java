package com.watersfall.clocgame.model.nation;

import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class NationBase
{
	protected @Getter int id;
	protected @Getter Connection connection;
	protected @Getter boolean safe;
	protected @Getter ResultSet results;

	public NationBase(Connection connection, int id, boolean safe) throws SQLException
	{
		this.id = id;
		this.connection = connection;
		this.safe = safe;
	}

	/**
	 * Updates the database with all changes made
	 * @throws SQLException if a database error occurs
	 */
	public void update() throws SQLException
	{
		results.updateRow();
	}
}
