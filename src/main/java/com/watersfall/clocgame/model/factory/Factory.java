package com.watersfall.clocgame.model.factory;

import com.watersfall.clocgame.model.UpdatableLongId;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Factory extends UpdatableLongId
{
	public static final String TABLE_NAME = "factories";
	private @Getter int owner;
	private @Getter int city;
	private @Getter @Setter int production;
	private @Getter @Setter int efficiency;

	public Factory(long id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id);
		this.owner = results.getInt("owner");
		this.city = results.getInt("city_id");
		this.production = results.getInt("production_id");
		this.efficiency = results.getInt("efficiency");
	}
}
