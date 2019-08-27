package com.watersfall.clocgame.model.treaty;

import com.watersfall.clocgame.exception.TreatyPermissionException;
import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TreatyMember extends Nation
{
	private @Getter boolean founder;
	private @Getter boolean manage;
	private @Getter boolean kick;
	private @Getter boolean invite;
	private @Getter boolean edit;
	private @Getter int idTreaty;
	private @Getter ResultSet results;

	public TreatyMember(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read = connection.prepareStatement("SELECT founder, manage, kick, invite, edit, alliance_id, nation_id FROM cloc_treaties_members WHERE nation_id=?");
		read.setInt(1, id);
		this.results = read.executeQuery();
		this.founder = results.getBoolean(1);
		this.manage = results.getBoolean(2);
		this.kick = results.getBoolean(3);
		this.invite = results.getBoolean(4);
		this.edit = results.getBoolean(5);
		this.idTreaty = results.getInt(6);
	}

	public void setFounder(boolean founder) throws SQLException
	{
		results.updateBoolean(1, founder);
	}

	public void setManage(boolean manage) throws SQLException
	{
		results.updateBoolean(2, manage);
	}

	public void setKick(boolean kick) throws SQLException
	{
		results.updateBoolean(3, kick);
	}

	public void setInvite(boolean invite) throws SQLException
	{
		results.updateBoolean(4, invite);
	}

	public void setEdit(boolean edit) throws SQLException
	{
		results.updateBoolean(5, edit);
	}

	public void setIdTreaty(int id) throws SQLException
	{
		results.updateInt(6, id);
	}

	public void invite(Nation nation)
	{
		if(!(this.invite || this.manage || this.founder))
		{
			throw new TreatyPermissionException("You do not have permission to do this!");
		}
	}

	public void kick(TreatyMember member) throws SQLException
	{
		if(member.getIdTreaty() != this.getIdTreaty())
		{
			throw new TreatyPermissionException("Not your treaty!");
		}
		else if(!(this.kick || this.manage || this.founder))
		{
			throw new TreatyPermissionException("You do not have permission to do this!");
		}
		else
		{
			member.leaveTreaty();
		}
	}
}
