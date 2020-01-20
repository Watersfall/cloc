package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class NationCities
{
	private @Getter Connection conn;
	private @Getter int id;
	private @Getter ResultSet results;
	private @Getter boolean safe;
	private @Getter HashMap<Integer, City> cities;

	public NationCities(Connection conn, int id, boolean safe) throws SQLException
	{
		this.conn = conn;
		PreparedStatement read;
		if(safe)
		{
			read = conn.prepareStatement("SELECT * FROM cloc_cities WHERE owner=? FOR UPDATE ");
		}
		else
		{
			read = conn.prepareStatement("SELECT * FROM cloc_cities WHERE owner=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			results.beforeFirst();
			this.cities = new HashMap<>();
			this.safe = safe;
			this.id = id;
			while(results.next())
			{
				cities.put(results.getInt("id"), new City(results.getInt("id"), results));
			}
		}
	}

	public void update() throws SQLException
	{
		for(City city : cities.values())
		{
			if(!city.getFields().isEmpty())
			{
				city.update(conn);
			}
		}
	}
}
