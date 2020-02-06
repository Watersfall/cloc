package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationCosmetic extends Updatable
{
	public static final String TABLE_NAME = "cloc_cosmetic";
	private @Getter String nationName;
	private @Getter String username;
	private @Getter String nationTitle;
	private @Getter String leaderTitle;
	private @Getter String portrait;
	private @Getter String flag;
	private @Getter String description;

	public static NationCosmetic getNationCosmetic(int id, Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM cloc_cosmetic WHERE id=?");
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		return new NationCosmetic(id, results);
	}

	public NationCosmetic(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.nationName = results.getString("nation_name");
		this.username = results.getString("username");
		this.nationTitle = results.getString("nation_title");
		this.leaderTitle = results.getString("leader_title");
		this.portrait = results.getString("portrait");
		this.flag = results.getString("flag");
		this.description = results.getString("description");
	}

	public String getNationUrl()
	{
		return "<a href=\"/nation/" + id + "\"><b>" + this.getNationName() + "</b></a>";
	}

	public void setNationName(String nationName)
	{
		this.addField("nation_name", nationName);
		this.nationName = nationName;
	}

	public void setUsername(String username)
	{
		this.addField("username", username);
		this.username = username;
	}

	public void setNationTitle(String nationTitle)
	{
		this.addField("nation_title", nationTitle);
		this.nationTitle = nationTitle;
	}

	public void setLeaderTitle(String leaderTitle)
	{
		this.addField("leader_title", leaderTitle);
		this.leaderTitle = leaderTitle;
	}

	public void setPortrait(String portrait)
	{
		this.addField("portrait", portrait);
		this.portrait = portrait;
	}

	public void setFlag(String flag)
	{
		this.addField("flag", flag);
		this.flag = flag;
	}

	public void setDescription(String description)
	{
		this.addField("description", description);
		this.description = description;
	}
}
