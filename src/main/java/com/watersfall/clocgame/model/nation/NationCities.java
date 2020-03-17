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
			read = conn.prepareStatement("SELECT cloc_cities.*, COALESCE(military_industry, 0) AS military_industry " +
					"FROM cloc_cities " +
					"LEFT JOIN ( " +
					"SELECT city_id, COUNT(id) " +
					"AS military_industry FROM factories GROUP BY city_id ) military_industry " +
					"ON military_industry.city_id=cloc_cities.id " +
					"WHERE cloc_cities.owner=? FOR UPDATE");
		}
		else
		{
			read = conn.prepareStatement("SELECT cloc_cities.*, COALESCE(military_industry, 0) AS military_industry " +
					"FROM cloc_cities " +
					"LEFT JOIN ( " +
					"SELECT city_id, COUNT(id) " +
					"AS military_industry FROM factories GROUP BY city_id ) military_industry " +
					"ON military_industry.city_id=cloc_cities.id " +
					"WHERE cloc_cities.owner=?");
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
				cities.put(results.getInt("cloc_cities.id"), new City(results.getInt("cloc_cities.id"), results));
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
