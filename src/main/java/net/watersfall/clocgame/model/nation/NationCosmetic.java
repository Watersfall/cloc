package net.watersfall.clocgame.model.nation;

import lombok.Getter;
import net.watersfall.clocgame.model.UpdatableIntId;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationCosmetic extends UpdatableIntId
{
	public static final String TABLE_NAME = "nation_cosmetic";
	private @Getter String nationName;
	private @Getter String username;
	private @Getter String nationTitle;
	private @Getter String leaderTitle;
	private @Getter String portrait;
	private @Getter String flag;
	private @Getter String description;

	public NationCosmetic(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id);
		this.nationName = results.getString("nation_name");
		this.username = results.getString("username");
		this.nationTitle = results.getString("nation_title");
		this.leaderTitle = results.getString("leader_title");
		this.portrait = results.getString("portrait");
		this.flag = results.getString("flag");
		this.description = results.getString("description");
	}

	public void setNationName(String nationName)
	{
		this.setField("nation_name", nationName);
		this.nationName = nationName;
	}

	public void setUsername(String username)
	{
		this.setField("username", username);
		this.username = username;
	}

	public void setNationTitle(String nationTitle)
	{
		this.setField("nation_title", nationTitle);
		this.nationTitle = nationTitle;
	}

	public void setLeaderTitle(String leaderTitle)
	{
		this.setField("leader_title", leaderTitle);
		this.leaderTitle = leaderTitle;
	}

	public void setPortrait(String portrait)
	{
		this.setField("portrait", portrait);
		this.portrait = portrait;
	}

	public void setFlag(String flag)
	{
		this.setField("flag", flag);
		this.flag = flag;
	}

	public void setDescription(String description)
	{
		this.setField("description", description);
		this.description = description;
	}
}
