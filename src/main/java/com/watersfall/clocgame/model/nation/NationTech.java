package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class NationTech extends Updatable
{
	public static final String TABLE_NAME = "cloc_tech";
	private HashMap<String, Integer> technologies;
	private @Getter HashSet<Technologies> researchedTechs;

	public NationTech(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id);
		technologies = new HashMap<>();
		for(Technologies tech : Technologies.values())
		{
			technologies.put(tech.getTechnology().getTableName(), results.getInt(tech.getTechnology().getTableName()));
		}
		loadTechnologies();
	}

	private void loadTechnologies() throws SQLException
	{
		researchedTechs = new HashSet<>();
		for(Technologies tech : Technologies.values())
		{
			if(technologies.get(tech.getTechnology().getTableName()) >= tech.getTechnology().getRequiredSuccesses())
			{
				researchedTechs.add(tech);
			}
		}
	}

	public int getTechnology(Technologies tech) throws SQLException
	{
		return technologies.get(tech.getTechnology().getTableName());
	}

	public void setTechnology(Technologies tech, int value)
	{
		fields.put(tech.getTechnology().getTableName(), value);
	}

}
