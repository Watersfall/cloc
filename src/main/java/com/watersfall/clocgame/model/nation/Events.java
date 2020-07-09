package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.util.Time;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Events
{
	private @Getter Connection conn;
	private @Getter int id;
	private @Getter int owner;
	private @Getter Event event;
	private @Getter String description;
	private @Getter long month;
	private @Getter int cityId;

	public Events(int id, int owner, Event event, String description, long month)
	{
		this.id = id;
		this.owner = owner;
		this.event = event;
		this.description = description;
		this.month = month;
	}

	public Events(int id, int owner, Event event, String description, long month, int cityId)
	{
		this.id = id;
		this.owner = owner;
		this.event = event;
		this.description = description;
		this.month = month;
		this.cityId = cityId;
	}

	public Events(ResultSet results) throws SQLException
	{
		this.id = results.getInt("id");
		this.owner = results.getInt("owner");
		this.event = Event.valueOf(results.getString("event_id"));
		this.description = results.getString("description");
		this.month = results.getLong("month");
		this.cityId = results.getInt("city_id");
	}

	public static Events getEventById(Connection conn, int eventId) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM events WHERE id=?");
		statement.setInt(1, eventId);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.first())
		{
			return new Events(resultSet);
		}
		else
		{
			return null;
		}
	}

	public static void deleteEventById(Connection conn, int eventId) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("DELETE FROM events WHERE id=?");
		statement.setInt(1, eventId);
		statement.execute();
	}

	public static void sendEvent(Connection conn, Nation nation, Event event, String description, int cityId) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("INSERT INTO events (owner, event_id, description, month, city_id) VALUES (?,?,?,?,?)");
		statement.setInt(1, nation.getId());
		statement.setString(2, event.name());
		statement.setString(3, description);
		statement.setLong(4, Time.month);
		statement.setInt(5, cityId);
		statement.execute();
	}

	public static void sendEvent(Connection conn, Nation nation, Event event, String description) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("INSERT INTO events (owner, event_id, description, month) VALUES (?,?,?,?)");
		statement.setInt(1, nation.getId());
		statement.setString(2, event.name());
		statement.setString(3, description);
		statement.setLong(4, Time.month);
		statement.execute();
	}
}
