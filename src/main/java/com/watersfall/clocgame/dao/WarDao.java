package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.war.War;
import com.watersfall.clocgame.util.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WarDao extends Dao
{
	private static final String WAR_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM cloc_war, cloc_login\n" +
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
					"WHERE cloc_war.id=? AND end=-1\n" +
					"AND (cloc_login.id=cloc_war.attacker OR cloc_login.id=cloc_war.defender)\n";

	private static final String ONGOING_WAR_PAGE_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM cloc_war, cloc_login\n" +
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
					"WHERE (attacker=cloc_login.id OR defender=cloc_login.id) AND end=-1 ORDER BY cloc_war.id DESC LIMIT 20 OFFSET ?\n";
	private static final String ENDED_WAR_PAGE_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM cloc_war, cloc_login\n" +
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
					"WHERE (attacker=cloc_login.id OR defender=cloc_login.id) AND end>0 ORDER BY cloc_war.id DESC LIMIT 20 OFFSET ?\n";

	private static final String CREATE_WAR_SQL_STATEMENT =
					"INSERT INTO cloc_war (attacker, defender, start)\n" +
					"VALUES (?,?,?)\n";

	private static final String OFFER_PEACE_SQL_STATEMENT =
					"UPDATE cloc_war\n" +
					"SET peace=?\n" +
					"WHERE attacker=? AND defender=? AND end=-1\n";

	private static final String END_WAR_SQL_STATEMENT =
					"UPDATE cloc_war\n" +
					"SET end=?, winner=?\n" +
					"WHERE attacker=? AND defender=? AND end=-1\n";

	public WarDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public ArrayList<War> getOngoingWarPage(int page) throws SQLException
	{
		return getWarPage(page, false);
	}

	public ArrayList<War> getEndedWarPage(int page) throws SQLException
	{
		return getWarPage(page, true);
	}

	private ArrayList<War> getWarPage(int page, boolean ended) throws SQLException
	{
		ArrayList<War> wars = new ArrayList<>();
		PreparedStatement statement;
		if(ended)
		{
			statement = connection.prepareStatement(ENDED_WAR_PAGE_SQL_STATEMENT);
		}
		else
		{
			statement = connection.prepareStatement(ONGOING_WAR_PAGE_SQL_STATEMENT);
		}
		statement.setInt(1, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		NationDao dao = new NationDao(connection, allowWriteAccess);
		while(results.next())
		{
			War war = new War(results.getInt("cloc_war.id"), results);
			if(results.getInt("cloc_login.id") == results.getInt("attacker"))
			{
				war.setAttacker(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
				results.next();
				war.setDefender(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
			}
			else
			{
				war.setDefender(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
				results.next();
				war.setAttacker(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
			}
			if(results.getInt("winner") == war.getAttacker().getId())
			{
				war.setWinner(war.getAttacker());
			}
			else if(results.getInt("winner") == war.getDefender().getId())
			{
				war.setWinner(war.getDefender());
			}
			else
			{
				war.setWinner(null);
			}
			wars.add(war);
		}
		return wars;
	}

	public War getWarById(int id) throws SQLException
	{
		PreparedStatement statement;
		if(allowWriteAccess)
		{
			statement = connection.prepareStatement(WAR_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			statement = connection.prepareStatement(WAR_SQL_STATEMENT);
		}
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		NationDao dao = new NationDao(connection, allowWriteAccess);
		War war = new War(results.getInt("cloc_war.id"), results);
		if(results.getInt("cloc_login.id") == results.getInt("attacker"))
		{
			war.setAttacker(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
			results.next();
			war.setDefender(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
		}
		else
		{
			war.setDefender(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
			results.next();
			war.setAttacker(dao.getCosmeticNationById(results.getInt("cloc_login.id"), results));
		}
		if(results.getInt("winner") == war.getAttacker().getId())
		{
			war.setWinner(war.getAttacker());
		}
		else if(results.getInt("winner") == war.getDefender().getId())
		{
			war.setWinner(war.getDefender());
		}
		else
		{
			war.setWinner(null);
		}
		return war;
	}

	public void endWar(War war) throws SQLException
	{
		endWar(war, null);
	}

	public void endWar(War war, Nation winner) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(END_WAR_SQL_STATEMENT);
		statement.setLong(1, Time.month);
		if(winner == null)
		{
			statement.setInt(2, -1);
		}
		else
		{
			statement.setInt(2, winner.getId());
		}
		statement.setInt(3, war.getAttacker().getId());
		statement.setInt(4, war.getDefender().getId());
		statement.execute();
	}

	public void createWar(Nation attacker, Nation defender) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_WAR_SQL_STATEMENT);
		statement.setInt(1, attacker.getId());
		statement.setInt(2, defender.getId());
		statement.setLong(3, Time.month);
		statement.execute();
	}

	public void offerPeace(int offering, Nation attacker, Nation defender) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(OFFER_PEACE_SQL_STATEMENT);
		statement.setInt(1, offering);
		statement.setInt(2, attacker.getId());
		statement.setInt(3, defender.getId());
		statement.execute();
	}
}
