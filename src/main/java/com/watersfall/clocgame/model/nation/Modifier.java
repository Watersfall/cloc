package com.watersfall.clocgame.model.nation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public @AllArgsConstructor class Modifier
{
	private @Getter long id;
	private @Getter int user;
	private @Getter int city;
	private @Getter Modifiers type;
	private @Getter long start;

	public Modifier(ResultSet results) throws SQLException
	{
		this.id = results.getLong("id");
		this.user = results.getInt("user");
		this.city = results.getInt("city");
		this.type = Modifiers.valueOf(results.getString("type"));
		this.start = results.getLong("start");
	}

	public static void createModifier(Connection conn, int user, Modifiers type, long start) throws SQLException
	{
		createModifier(conn, user, 0, type, start);
	}

	public static void createModifier(Connection conn, int user, int city, Modifiers type, long start) throws SQLException
	{
		PreparedStatement insert = conn.prepareStatement("INSERT INTO modifiers (user, city, type, start) VALUES (?,?,?,?)");
		insert.setInt(1, user);
		insert.setInt(2, city);
		insert.setString(3, type.name());
		insert.setLong(4, start);
		insert.execute();
	}

	public static void deleteModifier(Connection conn, long id) throws SQLException
	{
		PreparedStatement delete = conn.prepareStatement("DELETE FROM modifiers WHERE id=?");
		delete.setLong(1, id);
		delete.execute();
	}
}
