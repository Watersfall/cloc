package net.watersfall.clocgame.model.treaty;

import lombok.Getter;
import lombok.Setter;
import net.watersfall.clocgame.dao.TreatyDao;
import net.watersfall.clocgame.exception.TreatyPermissionException;
import net.watersfall.clocgame.listeners.Startup;
import net.watersfall.clocgame.model.UpdatableIntId;
import net.watersfall.clocgame.model.nation.Nation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Treaty extends UpdatableIntId
{
	public static final String TABLE_NAME = "treaties";
	private @Getter @Setter Connection conn;
	private @Getter String name;
	private @Getter String flag;
	private @Getter String description;
	private @Getter int memberCount;
	private @Getter @Setter ArrayList<Nation> members = null;

	public Treaty(int id, String name, String flag, String description, int memberCount)
	{
		super(TABLE_NAME, id);
		this.name = name;
		this.flag = flag;
		this.description = description;
		this.memberCount = memberCount;
	}

	public Treaty(ResultSet results) throws SQLException
	{
		super(TABLE_NAME, results.getInt("treaty.id"));
		this.name = results.getString("treaty.name");
		this.flag = results.getString("treaty.flag");
		this.description = results.getString("treaty.description");
		try{this.memberCount = results.getInt("members");}catch(SQLException ignored){}
	}

	public String getTreatyUrl()
	{
		return "<a href=\"" + Startup.CONTEXT_PATH + "/treaty/" + id + "\"><b>" + this.name + "</b></a>";
	}

	public void setName(String name) throws SQLException
	{
		if(name.length() > 32)
		{
			throw new IllegalArgumentException("Name can not be longer than 32 characters!");
		}
		else
		{
			this.setField("name", name);
			this.name = name;
		}
	}

	public void setFlag(String flag) throws SQLException
	{
		if(flag.length() > 32)
		{
			throw new IllegalArgumentException("Flag can not be longer than 32 characters!");
		}
		else
		{
			this.setField("flag", flag);
			this.flag = flag;
		}
	}

	public void setDescription(String description) throws SQLException
	{
		this.setField("description", description);
		this.description = description;
	}

	/**
	 * Checks if a treaty be deleted by a user, and then deletes it if they have permission to do so
	 * @param member the member attempting to delete the Treaty
	 * @throws SQLException if a database error occurs
	 */
	public void delete(Nation member) throws SQLException
	{
		if(!member.getTreatyPermissions().isFounder())
		{
			throw new TreatyPermissionException("Only the founder can do this!");
		}
		else
		{
			TreatyDao dao = new TreatyDao(conn, true);
			dao.deleteTreaty(this.id);
		}
	}
}
