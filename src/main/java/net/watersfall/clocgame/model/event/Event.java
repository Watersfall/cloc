package net.watersfall.clocgame.model.event;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Event
{
	private @Getter int id;
	private @Getter int owner;
	private @Getter Events event;
	private @Getter long month;
	private @Getter long cityId;

	public Event(ResultSet results) throws SQLException
	{
		this.id = results.getInt("id");
		this.owner = results.getInt("owner");
		this.event = Events.valueOf(results.getString("event_id"));
		this.month = results.getLong("month");
		this.cityId = results.getLong("city_id");
	}
}
