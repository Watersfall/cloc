package com.watersfall.clocgame.model.treaty;

import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TreatyMember extends Nation
{
	private @Getter @Setter boolean founder;
	private @Getter @Setter boolean manage;
	private @Getter @Setter boolean kick;
	private @Getter @Setter boolean invite;
	private @Getter @Setter boolean edit;
	private @Getter @Setter int idTreaty;
	private @Getter @Setter boolean member;
	private @Getter ResultSet results;

	public TreatyMember(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read = connection.prepareStatement("SELECT founder, manage, kick, invite, edit, alliance_id, nation_id FROM cloc_treaties_members WHERE nation_id=?");
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			this.member = false;
		}
		else
		{
			this.member = true;
			this.founder = results.getBoolean(1);
			this.manage = results.getBoolean(2);
			this.kick = results.getBoolean(3);
			this.invite = results.getBoolean(4);
			this.edit = results.getBoolean(5);
			this.idTreaty = results.getInt(6);
		}
	}

	public TreatyMember(int id, ResultSet results) throws SQLException
	{
		super(id, results);
		this.member = true;
		this.founder = results.getBoolean("founder");
		this.manage = results.getBoolean("manage");
		this.kick = results.getBoolean("kick");
		this.invite = results.getBoolean("invite");
		this.edit = results.getBoolean("edit");
		this.idTreaty = results.getInt("alliance_id");
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

	public void update() throws SQLException
	{
		super.update();
		PreparedStatement update = this.getConn().prepareStatement("UPDATE cloc_treaties_members " +
				"SET founder=?, manage=?, kick=?, invite=?, edit=? WHERE nation_id=?");
		update.setBoolean(1, founder);
		update.setBoolean(2, manage);
		update.setBoolean(3, kick);
		update.setBoolean(4, invite);
		update.setBoolean(5, edit);
		update.setInt(6, this.getId());
		update.execute();
	}
}
