package com.watersfall.clocgame.model.war;

import com.watersfall.clocgame.exception.WarNotFoundException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.treaty.Treaty;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class War
{
	private @Getter Nation attacker;
	private @Getter Treaty attackerTreaty;
	private @Getter Nation defender;
	private @Getter Treaty defenderTreaty;
	private @Getter int start;
	private @Getter int end;
	private @Getter int id;

	public static ArrayList<War> getOngoingWarPage(Connection conn, int page) throws SQLException
	{
		ArrayList<War> wars = new ArrayList<>();
		PreparedStatement statement = conn.prepareStatement("SELECT * " + "FROM cloc_war, cloc_login " +
				"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
				"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
				"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
				"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
				"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
				"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
				"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
				"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
				"LEFT JOIN cloc_treaties_members ctm ON cloc_login.id = ctm.nation_id\n" +
				"LEFT JOIN cloc_treaties ct ON ctm.alliance_id = ct.id \n" +
				"WHERE (attacker=cloc_login.id OR defender=cloc_login.id) AND end=-1 ORDER BY cloc_war.id DESC LIMIT 20 OFFSET ?");
		statement.setInt(1, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			wars.add(new War(results.getInt("cloc_war.id"), results));
		}
		return wars;
	}

	public War(int id, ResultSet results) throws SQLException
	{
		this.start = results.getInt("start");
		this.end = results.getInt("end");
		this.id = id;
		if(results.getInt("cloc_login.id") == results.getInt("attacker"))
		{
			this.attacker = new Nation(results.getInt("cloc_login.id"), results);
			this.attackerTreaty = new Treaty(results.getInt("ct.id"), results);
			results.next();
			this.defender = new Nation(results.getInt("cloc_login.id"), results);
			this.defenderTreaty = new Treaty(results.getInt("ct.id"), results);
		}
		else
		{
			this.defender = new Nation(results.getInt("cloc_login.id"), results);
			this.defenderTreaty = new Treaty(results.getInt("ct.id"), results);
			results.next();
			this.attacker = new Nation(results.getInt("cloc_login.id"), results);
			this.attackerTreaty = new Treaty(results.getInt("ct.id"), results);
		}
	}

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
			this.attacker = new Nation(conn, war.getInt(1), false);
			this.defender = new Nation(conn, war.getInt(2), false);
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
			this.attacker = new Nation(conn, war.getInt(1), false);
			this.defender = new Nation(conn, war.getInt(2), false);
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
