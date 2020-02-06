package com.watersfall.clocgame.model.war;

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
	private @Getter int peace;
	private @Getter Nation winner;

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
				"LEFT JOIN cloc_treaties_members treaty_member ON cloc_login.id = treaty_member.nation_id\n" +
				"LEFT JOIN cloc_treaties treaty ON treaty_member.alliance_id = treaty.id \n" +
				"WHERE (attacker=cloc_login.id OR defender=cloc_login.id) AND end=-1 ORDER BY cloc_war.id DESC LIMIT 20 OFFSET ?");
		statement.setInt(1, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			wars.add(new War(results.getInt("cloc_war.id"), results));
		}
		return wars;
	}

	public static ArrayList<War> getEndedWarPage(Connection conn, int page) throws SQLException
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
				"LEFT JOIN cloc_treaties_members treaty_member ON cloc_login.id = treaty_member.nation_id\n" +
				"LEFT JOIN cloc_treaties treaty ON treaty_member.alliance_id = treaty.id \n" +
				"WHERE (attacker=cloc_login.id OR defender=cloc_login.id) AND end>0 ORDER BY cloc_war.end DESC, cloc_war.id DESC LIMIT 20 OFFSET ?");
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
		int winner = results.getInt("winner");
		this.peace = results.getInt("peace");
		this.id = id;
		if(results.getInt("cloc_login.id") == results.getInt("attacker"))
		{
			this.attacker = new Nation(results.getInt("cloc_login.id"), results);
			this.attackerTreaty = new Treaty(results.getInt("treaty.id"), results);
			results.next();
			this.defender = new Nation(results.getInt("cloc_login.id"), results);
			this.defenderTreaty = new Treaty(results.getInt("treaty.id"), results);
		}
		else
		{
			this.defender = new Nation(results.getInt("cloc_login.id"), results);
			this.defenderTreaty = new Treaty(results.getInt("treaty.id"), results);
			results.next();
			this.attacker = new Nation(results.getInt("cloc_login.id"), results);
			this.attackerTreaty = new Treaty(results.getInt("treaty.id"), results);
		}
		if(winner == attacker.getId())
		{
			this.winner = attacker;
		}
		else if(winner == defender.getId())
		{
			this.winner = defender;
		}
		else
		{
			this.winner = null;
		}
	}
}
