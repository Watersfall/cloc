package com.watersfall.clocgame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InviteDao extends Dao
{
	private static final String CREATE_INVITE_SQL_STATEMENT =
					"INSERT INTO cloc_treaty_invites (alliance_id, nation_id)\n" +
					"VALUES (?,?)\n";
	private static final String DELETE_INVITE_SQL_STATEMENT =
					"DELETE FROM cloc_treaty_invites\n" +
					"WHERE nation_id=? AND alliance_id=?\n";

	public InviteDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public void createInvite(int nation, int treaty) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_INVITE_SQL_STATEMENT);
		statement.setInt(1, treaty);
		statement.setInt(2, nation);
		statement.execute();
	}

	public void deleteInvite(int nation, int treaty) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(DELETE_INVITE_SQL_STATEMENT);
		statement.setInt(1, nation);
		statement.setInt(2, treaty);
		statement.execute();
	}
}
