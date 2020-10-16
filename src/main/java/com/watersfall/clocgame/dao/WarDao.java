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
					"FROM wars, login\n" +
					"JOIN nation_producibles ON login.id = nation_producibles.id\n" +
					"JOIN nation_tech ON login.id = nation_tech.id\n" +
					"JOIN nation_policy ON login.id = nation_policy.id\n" +
					"JOIN nation_cosmetic ON login.id = nation_cosmetic.id\n" +
					"JOIN nation_stats ON login.id = nation_stats.id\n" +
					"LEFT JOIN treaty_members treaty_member ON login.id = treaty_member.nation_id\n" +
					"LEFT JOIN treaties treaty ON treaty_member.alliance_id = treaty.id\n" +
					"WHERE wars.id=? AND end=-1\n" +
					"AND (login.id=wars.attacker OR login.id=wars.defender)\n";

	private static final String ONGOING_WAR_PAGE_SQL_STATEMENT =
					"SELECT *" +
					"FROM wars, login\n" +
					"JOIN nation_producibles ON login.id = nation_producibles.id\n" +
					"JOIN nation_tech ON login.id = nation_tech.id\n" +
					"JOIN nation_policy ON login.id = nation_policy.id\n" +
					"JOIN nation_cosmetic ON login.id = nation_cosmetic.id\n" +
					"JOIN nation_stats ON login.id = nation_stats.id\n" +
					"LEFT JOIN treaty_members treaty_member ON login.id = treaty_member.nation_id\n" +
					"LEFT JOIN treaties treaty ON treaty_member.alliance_id = treaty.id\n" +
					"WHERE (attacker=login.id OR defender=login.id) AND end=-1 ORDER BY wars.id DESC LIMIT 20 OFFSET ?\n";
	private static final String ENDED_WAR_PAGE_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM wars, login\n" +
					"JOIN nation_producibles ON login.id = nation_producibles.id\n" +
					"JOIN nation_tech ON login.id = nation_tech.id\n" +
					"JOIN nation_policy ON login.id = nation_policy.id\n" +
					"JOIN nation_cosmetic ON login.id = nation_cosmetic.id\n" +
					"JOIN nation_stats ON login.id = nation_stats.id\n" +
					"LEFT JOIN treaty_members treaty_member ON login.id = treaty_member.nation_id\n" +
					"LEFT JOIN treaties treaty ON treaty_member.alliance_id = treaty.id\n" +
					"WHERE (attacker=login.id OR defender=login.id) AND end>0 ORDER BY wars.id DESC LIMIT 20 OFFSET ?\n";

	private static final String CREATE_WAR_SQL_STATEMENT =
					"INSERT INTO wars (attacker, defender, start, name)\n" +
					"VALUES (?,?,?,?)\n";

	private static final String OFFER_PEACE_SQL_STATEMENT =
					"UPDATE wars\n" +
					"SET peace=?\n" +
					"WHERE attacker=? AND defender=? AND end=-1\n";

	private static final String END_WAR_SQL_STATEMENT =
					"UPDATE wars\n" +
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
			War war = new War(results.getInt("wars.id"), results);
			if(results.getInt("login.id") == results.getInt("attacker"))
			{
				war.setAttacker(dao.getCosmeticNationById(results.getInt("login.id"), results));
				results.next();
				war.setDefender(dao.getCosmeticNationById(results.getInt("login.id"), results));
			}
			else
			{
				war.setDefender(dao.getCosmeticNationById(results.getInt("login.id"), results));
				results.next();
				war.setAttacker(dao.getCosmeticNationById(results.getInt("login.id"), results));
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
		War war = new War(results.getInt("wars.id"), results);
		if(results.getInt("login.id") == results.getInt("attacker"))
		{
			war.setAttacker(dao.getCosmeticNationById(results.getInt("login.id")));
			results.next();
			war.setDefender(dao.getCosmeticNationById(results.getInt("login.id")));
		}
		else
		{
			war.setDefender(dao.getCosmeticNationById(results.getInt("login.id")));
			results.next();
			war.setAttacker(dao.getCosmeticNationById(results.getInt("login.id")));
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

	public void createWar(Nation attacker, Nation defender, String name) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_WAR_SQL_STATEMENT);
		statement.setInt(1, attacker.getId());
		statement.setInt(2, defender.getId());
		statement.setLong(3, Time.month);
		statement.setString(4, name);
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
