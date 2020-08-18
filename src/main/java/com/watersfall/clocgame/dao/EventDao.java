package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.event.Event;
import com.watersfall.clocgame.model.event.Events;
import com.watersfall.clocgame.util.Time;

import java.sql.*;
import java.util.ArrayList;

public class EventDao extends Dao
{
	private static final String DELETE_EVENT_SQL_STATEMENT =
					"DELETE FROM events\n" +
					"WHERE id=?\n";
	private static final String CREATE_EVENT_SQL_STATEMENT =
					"INSERT INTO events (owner, event_id, month, city_id)\n" +
					"VALUES (?,?,?,?)\n";
	private static final String GET_EVENT_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM events\n" +
					"WHERE id=?\n";
	private static final String GET_EVENTS_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM events\n" +
					"WHERE owner=?\n";

	public EventDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public void deleteEventById(int id) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(DELETE_EVENT_SQL_STATEMENT);
		statement.setInt(1, id);
		statement.execute();
	}

	public void createEvent(int receiver, Events type, String message) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_EVENT_SQL_STATEMENT);
		statement.setInt(1, receiver);
		statement.setString(2, type.name());
		statement.setString(3, message);
		statement.setLong(4, Time.month);
		statement.setNull(5, Types.INTEGER);
		statement.execute();
	}

	public void createEvent(int receiver, Events type, int city) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_EVENT_SQL_STATEMENT);
		statement.setInt(1, receiver);
		statement.setString(2, type.name());
		statement.setLong(3, Time.month);
		statement.setInt(4, city);
		statement.execute();
	}

	public Event getEventById(int id) throws SQLException
	{
		PreparedStatement statement;
		if(allowWriteAccess)
		{
			statement = connection.prepareStatement(GET_EVENT_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			statement = connection.prepareStatement(GET_EVENT_SQL_STATEMENT);
		}
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		return new Event(results);
	}

	public ArrayList<Event> getEventsByNationId(int id) throws SQLException
	{
		ArrayList<Event> list = new ArrayList<>();
		PreparedStatement statement;
		if(allowWriteAccess)
		{
			statement = connection.prepareStatement(GET_EVENTS_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			statement = connection.prepareStatement(GET_EVENTS_SQL_STATEMENT);
		}
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			list.add(new Event(results));
		}
		return list;
	}
}
