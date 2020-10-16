package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.modifier.Modifiers;
import com.watersfall.clocgame.util.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifierDao extends Dao
{
	private static final String CREATE_MODIFIER_SQL_STATEMENT =
					"INSERT INTO modifiers (user, city, type, start)\n" +
					"VALUES (?,?,?,?)\n";
	private static final String DELETE_MODIFIER_SQL_STATEMENT =
					"DELETE FROM modifiers\n" +
					"WHERE id=?\n";

	public ModifierDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public void createModifier(int receiver, Modifiers type) throws SQLException
	{
		createModifier(receiver, 0, type);
	}

	public void createModifier(int receiver, long cityId, Modifiers type) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_MODIFIER_SQL_STATEMENT);
		statement.setInt(1, receiver);
		statement.setLong(2, cityId);
		statement.setString(3, type.name());
		statement.setLong(4, Time.month);
		statement.execute();
	}

	public void deleteModifierById(int id) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(DELETE_MODIFIER_SQL_STATEMENT);
		statement.setInt(1, id);
		statement.execute();
	}
}
