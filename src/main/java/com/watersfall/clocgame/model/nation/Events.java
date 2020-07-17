package com.watersfall.clocgame.model.nation;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Events
{
	private @Getter int id;
	private @Getter int owner;
	private @Getter Event event;
	private @Getter String description;
	private @Getter long month;
	private @Getter int cityId;

	public Events(ResultSet results) throws SQLException
	{
		this.id = results.getInt("id");
		this.owner = results.getInt("owner");
		this.event = Event.valueOf(results.getString("event_id"));
		this.description = results.getString("description");
		this.month = results.getLong("month");
		this.cityId = results.getInt("city_id");
	}
}
