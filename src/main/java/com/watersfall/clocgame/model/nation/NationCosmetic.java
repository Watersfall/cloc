package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationCosmetic extends NationBase
{
	private @Getter String nationName;
	private @Getter String username;
	private @Getter String nationTitle;
	private @Getter String leaderTitle;
	private @Getter String portrait;
	private @Getter String flag;
	private @Getter String description;

	public NationCosmetic(String nationName, String username, String nationTitle, String leaderTitle, String portrait, String flag, String description)
	{
		this.nationName = nationName;
		this.username = username;
		this.nationTitle = nationTitle;
		this.leaderTitle = leaderTitle;
		this.portrait = portrait;
		this.flag = flag;
		this.description = description;
	}

	public NationCosmetic(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.id = results.getInt("id");
		this.nationName = results.getString("nation_name");
		this.username = results.getString("username");
		this.nationTitle = results.getString("nation_title");
		this.leaderTitle = results.getString("leader_title");
		this.portrait = results.getString("portrait");
		this.flag = results.getString("flag");
		this.description = results.getString("description");
		this.results = results;
	}

	public NationCosmetic(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT nation_name, username, nation_title, leader_title, portrait, flag, description, id " + "FROM cloc_cosmetic " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT nation_name, username, nation_title, leader_title, portrait, flag, description, id " + "FROM cloc_cosmetic " + "WHERE id=?");
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
			this.nationName = results.getString(1);
			this.username = results.getString(2);
			this.nationTitle = results.getString(3);
			this.leaderTitle = results.getString(4);
			this.portrait = results.getString(5);
			this.flag = results.getString(6);
			this.description = results.getString(7);
		}
	}

	public void setNationName(String nationName) throws SQLException
	{
		if(nationName.length() > 32)
		{
			throw new IllegalArgumentException("Can not be more than 32 characters!");
		}
		else
		{
			this.nationName = nationName;
			results.updateString("nation_name", nationName);
		}
	}

	public void setUsername(String username) throws SQLException
	{
		if(nationName.length() > 32)
		{
			throw new IllegalArgumentException("Can not be more than 32 characters!");
		}
		else
		{
			this.username = username;
			results.updateString("username", username);
		}
	}

	public void setNationTitle(String nationTitle) throws SQLException
	{
		if(nationName.length() > 128)
		{
			throw new IllegalArgumentException("Can not be more than 128 characters!");
		}
		else
		{
			this.nationTitle = nationTitle;
			results.updateString("nation_title", nationTitle);
		}
	}

	public void setLeaderTitle(String leaderTitle) throws SQLException
	{
		if(nationName.length() > 128)
		{
			throw new IllegalArgumentException("Can not be more than 128 characters!");
		}
		else
		{
			this.leaderTitle = leaderTitle;
			results.updateString("leader_title", leaderTitle);
		}
	}

	public void setPortrait(String portrait) throws SQLException
	{
		if(nationName.length() > 128)
		{
			throw new IllegalArgumentException("Can not be more than 128 characters!");
		}
		else
		{
			this.portrait = portrait;
			results.updateString("portrait", portrait);
		}
	}

	public void setFlag(String flag) throws SQLException
	{
		if(nationName.length() > 128)
		{
			throw new IllegalArgumentException("Can not be more than 128 characters!");
		}
		else
		{
			this.flag = flag;
			results.updateString("flag", flag);
		}
	}

	public void setDescription(String description) throws SQLException
	{
		if(nationName.length() > 32767)
		{
			throw new IllegalArgumentException("Can not be more than 32,767 characters!");
		}
		else
		{
			this.description = description;
			results.updateString("description", description);
		}
	}
}
