package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.treaty.TreatyMember;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NationInvites extends NationBase
{
	private @Getter ArrayList<Integer> invites;

	public NationInvites(Connection conn, int id, boolean safe) throws SQLException
	{
		super(conn, id, safe);
		invites = new ArrayList<>();
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT alliance_id, id " + "FROM cloc_treaty_invites " + "WHERE nation_id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT alliance_id, id " + "FROM cloc_treaty_invites " + "WHERE nation_id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		while(results.next())
		{
			invites.add(results.getInt(1));
		}
	}

	public String reject(Integer alliance) throws SQLException
	{
		PreparedStatement delete = connection.prepareStatement("DELETE FROM cloc_treaty_invites WHERE nation_id=? AND alliance_id=?");
		delete.setInt(1, this.id);
		delete.setInt(2, alliance);
		int deleted = delete.executeUpdate();
		if(deleted > 0)
		{
			return Responses.inviteRejected();
		}
		else
		{
			return Responses.noInvite();
		}
	}

	public String accept(Integer alliance, Nation nation) throws SQLException
	{
		if(invites.contains(alliance))
		{
			nation.joinTreaty(alliance, false);
			return Responses.inviteAccepted();
		}
		else
		{
			return Responses.noInvite();
		}
	}

	public String invite(Nation nation, TreatyMember member) throws SQLException
	{
		PreparedStatement check = connection.prepareStatement("SELECT id FROM cloc_treaty_invites WHERE nation_id=? AND alliance_id=?");
		check.setInt(1, nation.getId());
		check.setInt(2, member.getTreaty().getId());
		ResultSet results = check.executeQuery();
		if(results.first())
		{
			return Responses.alreadyInvited();
		}
		else
		{
			PreparedStatement invite = connection.prepareStatement("INSERT INTO cloc_treaty_invites (alliance_id, nation_id) VALUES (?,?)");
			invite.setInt(1, member.getTreaty().getId());
			invite.setInt(2, nation.getId());
			invite.execute();
			return Responses.invited();
		}
	}
}
