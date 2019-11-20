package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class NationTech extends NationBase
{
	private @Getter HashSet<Technologies> researchedTechs;

	/**
	 * Constructs the technologies of a Nation1
	 * @param connection the SQL connection to use
	 * @param id 		 The Nation ID
	 * @param safe 		 Whether the results should be safe to write to
	 * @throws SQLException on an SQL error
	 */
	public NationTech(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT * " + "FROM cloc_tech " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT * " + "FROM cloc_tech " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.connection = connection;
			this.id = id;
			this.safe = safe;

			loadTechnologies();
		}
	}

	public NationTech(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.results = results;
		loadTechnologies();
	}

	private void loadTechnologies() throws SQLException
	{
		researchedTechs = new HashSet<>();
		for(Technologies tech : Technologies.values())
		{
			if(results.getInt(tech.getTechnology().getTableName()) >= tech.getTechnology().getRequiredSuccesses())
			{
				researchedTechs.add(tech);
			}
		}
	}

	public int getTechnology(Technologies tech) throws SQLException
	{
		return results.getInt(tech.getTechnology().getTableName());
	}

	public void setTechnology(Technologies tech, int value) throws SQLException
	{
		results.updateInt(tech.getTechnology().getTableName(), value);
	}

}
