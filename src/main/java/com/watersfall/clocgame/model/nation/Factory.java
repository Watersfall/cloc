package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Factory extends Updatable
{
	public static final String TABLE_NAME = "factories";
	private @Getter int id;
	private @Getter int owner;
	private @Getter int city;
	private @Getter @Setter int production;
	private @Getter @Setter int efficiency;

	public Factory(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id);
		this.id = id;
		this.owner = results.getInt("owner");
		this.city = results.getInt("city_id");
		this.production = results.getInt("production_id");
		this.efficiency = results.getInt("efficiency");
	}
}
