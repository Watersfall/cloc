package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.UpdatableIntId;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TreatyPermissions extends UpdatableIntId
{
	private static final String TABLE_NAME = "treaty_members";
	private @Getter boolean founder;
	private @Getter boolean manage;
	private @Getter boolean kick;
	private @Getter boolean invite;
	private @Getter boolean edit;

	public TreatyPermissions(ResultSet results) throws SQLException
	{
		super(TABLE_NAME, results.getInt("treaty_member.nation_id"));
		this.founder = results.getBoolean("treaty_member.founder");
		this.manage = results.getBoolean("treaty_member.manage");
		this.kick = results.getBoolean("treaty_member.kick");
		this.invite = results.getBoolean("treaty_member.invite");
		this.edit = results.getBoolean("treaty_member.edit");
	}

	public void setFounder(boolean founder)
	{
		this.founder = founder;
		this.setField("founder", founder);
	}

	public void setManage(boolean manage)
	{
		this.manage = manage;
		this.setField("manage", manage);
	}

	public void setKick(boolean kick)
	{
		this.kick = kick;
		this.setField("kick", kick);
	}

	public void setInvite(boolean invite)
	{
		this.invite = invite;
		this.setField("invite", invite);
	}

	public void setEdit(boolean edit)
	{
		this.edit = edit;
		this.setField("edit", edit);
	}

	public String getRoles()
	{
		String roles = "";
		if(founder)
		{
			return "Founder";
		}
		else if(manage)
		{
			return "Manage";
		}
		else if(kick && !invite && !edit)
		{
			return "Kick";
		}
		else if(invite || edit)
		{
			if(kick)
			{
				roles += "Kick, ";
			}
			if(invite && !edit)
			{
				roles += "Invite";
			}
			else if(!invite)
			{
				roles += "Edit";
			}
			else
			{
				roles += "Invite, Edit";
			}
		}
		return roles;
	}

	@Override
	public void update(Connection conn) throws SQLException
	{
		if(!this.fields.isEmpty())
		{
			PreparedStatement statement = conn.prepareStatement("UPDATE treaty_members " +
					"SET founder=?, manage=?, kick=?, invite=?, edit=? " +
					"WHERE nation_id=?");
			statement.setBoolean(1, this.founder);
			statement.setBoolean(2, this.manage);
			statement.setBoolean(3, this.kick);
			statement.setBoolean(4, this.invite);
			statement.setBoolean(5, this.edit);
			statement.setLong(6, this.id);
			statement.execute();
		}
	}
}
