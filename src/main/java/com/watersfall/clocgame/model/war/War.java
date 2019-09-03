package com.watersfall.clocgame.model.war;

import com.watersfall.clocgame.exception.WarNotFoundException;
import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class War
{

	private @Getter Nation attacker;
	private @Getter Nation defender;
	private @Getter int start;
	private @Getter int end;
	private @Getter int id;

	public War(Connection conn, int id, boolean safe, boolean attacker) throws SQLException
	{
		PreparedStatement getWar;
		if(attacker)
		{
			getWar = conn.prepareStatement("SELECT attacker, defender, start, end, id FROM cloc_war WHERE attacker=? AND end=-1");
		}
		else
		{
			getWar = conn.prepareStatement("SELECT attacker, defender, start, end, id FROM cloc_war WHERE defender=? AND end=-1");
		}
		getWar.setInt(1, id);
		ResultSet war = getWar.executeQuery();
		if(!war.first())
		{
			throw new WarNotFoundException();
		}
		else
		{
			this.attacker = new Nation(conn, war.getInt(1), safe, false);
			this.defender = new Nation(conn, war.getInt(2), safe, false);
			this.start = war.getInt(3);
			this.end = war.getInt(4);
			this.id = war.getInt(5);
		}
	}

	public War(Connection conn, int id, boolean safe) throws SQLException
	{
		PreparedStatement getWar = conn.prepareStatement("SELECT attacker, defender, start, end, id FROM cloc_war WHERE id=?");
		getWar.setInt(1, id);
		ResultSet war = getWar.executeQuery();
		if(!war.first())
		{
			throw new WarNotFoundException();
		}
		else
		{
			this.id = id;
			attacker = new Nation(conn, war.getInt(1), safe, false);
			defender = new Nation(conn, war.getInt(2), safe, false);
			start = war.getInt(3);
			end = war.getInt(4);
		}
	}

	public War(Connection conn, Nation attacker, Nation defender) throws SQLException
	{
		PreparedStatement createWar = conn.prepareStatement("INSERT INTO cloc_war (attacker, defender, start) VALUES (?,?,?)");
		createWar.setInt(1, attacker.getId());
		createWar.setInt(2, defender.getId());
		createWar.setInt(3, 1);
		createWar.execute();
	}


}
