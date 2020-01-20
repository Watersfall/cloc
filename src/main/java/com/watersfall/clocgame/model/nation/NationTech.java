package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class NationTech extends Updatable
{
	public static final String TABLE_NAME = "cloc_tech";
	private @Getter HashSet<Technologies> researchedTechs;

	public NationTech(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
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

	public void setTechnology(Technologies tech, int value)
	{
		fields.put(tech.getTechnology().getTableName(), value);
	}

}
