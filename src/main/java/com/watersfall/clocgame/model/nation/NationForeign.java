package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.Region;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationForeign extends NationBase
{

	private @Getter Region region;
	private @Getter int alignment;

	public NationForeign(Region region, int alignment)
	{
		this.region = region;
		this.alignment = alignment;
	}

	public NationForeign(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.results = results;
		this.region = Region.getFromName(results.getString("region"));
		this.alignment = results.getInt("alignment");
	}

	public NationForeign(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT region, alignment, id " + "FROM cloc_foreign " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT region, alignment, id " + "FROM cloc_foreign " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.region = Region.getFromName(results.getString(1));
			this.alignment = results.getInt(2);
		}
	}

	public void setRegion(Region region) throws SQLException
	{
		this.region = region;
		results.updateString(1, region.getName());
	}

	public void setAlignment(int alignment) throws SQLException
	{
		this.alignment = alignment;
		results.updateInt(2, alignment);
	}
}
