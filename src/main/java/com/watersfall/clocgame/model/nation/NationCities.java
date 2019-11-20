package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class NationCities extends NationBase
{

	private @Getter HashMap<Integer, City> cities;

	public NationCities(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT id " + "FROM cloc_cities " + "WHERE owner=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT id " + "FROM cloc_cities " + "WHERE owner=?");
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
			this.connection = connection;
			this.safe = safe;
			this.id = id;
			while(results.next())
			{
				cities.put(results.getInt("id"), new City(connection, results.getInt("id"), safe));
			}
		}
	}
}
