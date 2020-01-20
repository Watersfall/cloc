package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationForeign extends Updatable
{
	public static final String TABLE_NAME = "cloc_foreign";
	private @Getter Region region;
	private @Getter int alignment;

	public NationForeign(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.region = Region.getFromName(results.getString("region"));
		this.alignment = results.getInt("alignment");
	}

	public void setRegion(Region region)
	{
		this.addField("region", region);
		this.region = region;
	}

	public void setAlignment(int alignment)
	{
		this.addField("alignment", alignment);
		this.alignment = alignment;
	}
}
