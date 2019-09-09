package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class NationArmies extends NationBase
{

	private @Getter HashMap<Integer, Army> armies;

	public NationArmies(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT owner, id " + "FROM cloc_armies " + "WHERE owner=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT owner, id " + "FROM cloc_armies " + "WHERE owner=?");
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
			this.armies = new HashMap<>();
			this.connection = connection;
			this.safe = safe;
			this.id = id;
			while(results.next())
			{
				armies.put(results.getInt(2), new Army(connection, results.getInt(2), safe));
			}
		}
	}
}
