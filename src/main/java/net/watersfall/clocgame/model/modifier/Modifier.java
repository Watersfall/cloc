package net.watersfall.clocgame.model.modifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public @AllArgsConstructor class Modifier
{
	private @Getter long id;
	private @Getter int user;
	private @Getter long city;
	private @Getter Modifiers type;
	private @Getter long start;

	public Modifier(ResultSet results) throws SQLException
	{
		this.id = results.getLong("id");
		this.user = results.getInt("user");
		this.city = results.getLong("city");
		this.type = Modifiers.valueOf(results.getString("type"));
		this.start = results.getLong("start");
	}
}
