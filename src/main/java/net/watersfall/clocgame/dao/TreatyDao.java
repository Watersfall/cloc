package net.watersfall.clocgame.dao;

import net.watersfall.clocgame.exception.TreatyNotFoundException;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.treaty.Treaty;

import java.sql.*;
import java.util.ArrayList;

public class TreatyDao extends Dao
{
	private static final String GET_TREATY_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM treaties AS treaty\n" +
					"WHERE id=?\n";
	private static final String DELETE_MEMBERS_SQL_STATEMENT =
					"DELETE FROM treaty_members\n" +
					"WHERE alliance_id=?";
	private static final String DELETE_ALLIANCE_SQL_STATEMENT =
					"DELETE FROM treaties\n" +
					"WHERE id=?\n";
	private static final String DELETE_INVITES_SQL_STATEMENT =
					"DELETE FROM treaty_invites\n" +
					"WHERE alliance_id=?";
	private static final String LEAVE_TREATY_SQL_STATEMENT =
					"DELETE FROM treaty_members\n" +
					"WHERE nation_id=?";
	private static final String GET_TREATY_PAGE_SQL_STATEMENT =
					"SELECT treaty.*, COUNT(nation_id) AS members " +
					"FROM treaties AS treaty, treaty_members " +
					"WHERE treaty_members.alliance_id=treaty.id " +
					"GROUP BY treaty.id " +
					"ORDER BY members DESC, id LIMIT 20 OFFSET ?";
	private static final String JOIN_TREATY_SQL_STATEMENT =
					"INSERT INTO treaty_members (alliance_id, nation_id, founder)\n" +
					"VALUES (?,?,?)\n";
	private static final String CREATE_TREATY_SQL_STATEMENT =
					"INSERT INTO treaties (name, description)\n" +
					"VALUES (?,?)";
	private static final String CHECK_MEMBER_COUNT_SQL_STATEMENT =
					"SELECT count(nation_id)\n" +
					"FROM treaty_members\n" +
					"WHERE alliance_id=?\n";


	public TreatyDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public Treaty getCosmeticTreatyById(int id) throws SQLException
	{
		PreparedStatement statement;
		if(allowWriteAccess)
		{
			statement = connection.prepareStatement(GET_TREATY_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			statement = connection.prepareStatement(GET_TREATY_SQL_STATEMENT);
		}
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		if(!results.first())
		{
			return null;
		}
		else
		{
			return new Treaty(results);
		}
	}

	public Treaty getTreatyWithMembersById(int id) throws SQLException
	{
		Treaty treaty = getCosmeticTreatyById(id);
		if(treaty == null)
		{
			throw new TreatyNotFoundException();
		}
		else
		{
			treaty.setConn(connection);
			treaty.setMembers(getTreatyMembers(id));
			return treaty;
		}
	}

	public void leaveTreaty(int nation, long treaty) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(LEAVE_TREATY_SQL_STATEMENT);
		statement.setInt(1, nation);
		statement.execute();
		PreparedStatement check = connection.prepareStatement(CHECK_MEMBER_COUNT_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		check.setLong(1, treaty);
		ResultSet results = check.executeQuery();
		results.first();
		if(results.getInt(1) <= 0)
		{
			deleteTreaty(treaty);
		}
	}

	public void joinTreaty(int nation, int treaty) throws SQLException
	{
		joinTreaty(nation, treaty, false);
	}

	public void joinTreaty(int nation, int treaty, boolean founder) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(JOIN_TREATY_SQL_STATEMENT);
		statement.setInt(1, treaty);
		statement.setInt(2, nation);
		statement.setBoolean(3, founder);
		statement.execute();
	}

	public ArrayList<Nation> getTreatyMembers(int id) throws SQLException
	{
		return new NationDao(connection, allowWriteAccess).getTreatyMembers(id);
	}

	public void saveTreaty(Treaty treaty) throws SQLException
	{
		requireWriteAccess();
		treaty.update(connection);
	}

	public void deleteTreaty(long treaty) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement deleteMembers = connection.prepareStatement(DELETE_MEMBERS_SQL_STATEMENT);
		PreparedStatement deleteAlliance = connection.prepareStatement(DELETE_ALLIANCE_SQL_STATEMENT);
		PreparedStatement deleteInvites = connection.prepareStatement(DELETE_INVITES_SQL_STATEMENT);
		deleteInvites.setLong(1, treaty);
		deleteMembers.setLong(1, treaty);
		deleteAlliance.setLong(1, treaty);
		deleteMembers.execute();
		deleteInvites.execute();
		deleteAlliance.execute();
	}

	public Treaty createTreaty(String name) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_TREATY_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, name);
		statement.setString(2, "");
		statement.execute();
		ResultSet results = statement.getGeneratedKeys();
		results.first();
		return new TreatyDao(connection, true).getCosmeticTreatyById(results.getInt(1));
	}

	public ArrayList<Treaty> getTreatyPage(int page) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_TREATY_PAGE_SQL_STATEMENT);
		statement.setInt(1, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		ArrayList<Treaty> list = new ArrayList<>();
		while(results.next())
		{
			list.add(new Treaty(results));
		}
		return list;
	}
}
