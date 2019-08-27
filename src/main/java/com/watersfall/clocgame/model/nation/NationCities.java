package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NationCities extends NationBase
{

	private @Getter
	ArrayList<City> cities;

	public NationCities(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT owner, id " + "FROM cloc_cities " + "WHERE owner=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT owner, id " + "FROM cloc_cities " + "WHERE owner=?");
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
			this.cities = new ArrayList<>();
			this.connection = connection;
			this.safe = safe;
			this.id = id;
			while(results.next())
			{
				cities.add(new City(connection, results.getInt(2), safe));
			}
		}
	}

	public void updateAll() throws SQLException
	{
		results.beforeFirst();
		while(results.next())
		{
			results.updateRow();
		}
	}

}
