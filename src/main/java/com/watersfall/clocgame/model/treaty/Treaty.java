package com.watersfall.clocgame.model.treaty;

import com.watersfall.clocgame.exception.TreatyNotFoundException;
import com.watersfall.clocgame.exception.TreatyPermissionException;
import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;

public class Treaty extends Updatable
{
	public static final String TABLE_NAME = "cloc_treaties";
	private @Getter Connection conn;
	private @Getter String name;
	private @Getter String flag;
	private @Getter String description;
	private @Getter int memberCount;
	private ArrayList<TreatyMember> members = null;

	/**
	 * Returns an Array of all treaties in the database <i>without</i> their members
	 * @param conn The SQL Connection to use
	 * @return An ArrayList containing all treaties <i>without</i> their members
	 * @throws SQLException if a database issue occurs
	 */
	public static ArrayList<Treaty> getTreatyPage(Connection conn, int page) throws SQLException
	{
		ArrayList<Treaty> array = new ArrayList<>();
		PreparedStatement treaties = conn.prepareStatement("SELECT cloc_treaties.*, COUNT(nation_id) AS count " +
				"FROM cloc_treaties, cloc_treaties_members " +
				"WHERE cloc_treaties.id > 0 AND cloc_treaties_members.alliance_id=cloc_treaties.id " +
				"GROUP BY cloc_treaties.id " +
				"ORDER BY count DESC, id LIMIT 20 OFFSET ?");
		treaties.setInt(1, (page - 1) * 20);
		ResultSet results = treaties.executeQuery();
		while(results.next())
		{
			if(results.getInt("cloc_treaties.id") != 0)
			{
				array.add(new Treaty(results.getInt("cloc_treaties.id"), results.getString("cloc_treaties.name"),
						results.getString("cloc_treaties.flag"), results.getString("cloc_treaties.description"), results.getInt("count")));
			}
		}
		return array;
	}

	/**
	 * Creates a Treaty with the given name in the database
	 * @param conn The SQL Connection to use
	 * @param name The Treaty name
	 * @return The created Treaty
	 * @throws SQLException if a database issue occurs
	 */
	public static Treaty createTreaty(Connection conn, String name) throws SQLException
	{
		PreparedStatement create = conn.prepareStatement("INSERT INTO cloc_treaties (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
		create.setString(1, name);
		create.executeUpdate();
		ResultSet results = create.getGeneratedKeys();
		results.first();
		return new Treaty(conn, results.getInt(1), true);
	}

	public Treaty(int id, String name, String flag, String description, int memberCount)
	{
		super(TABLE_NAME, id, null);
		this.id = id;
		this.name = name;
		this.flag = flag;
		this.description = description;
		this.memberCount = memberCount;
	}

	public Treaty(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, null);
		this.id = id;
		this.name = results.getString("treaty.name");
		this.flag = results.getString("treaty.flag");
		this.description = results.getString("treaty.description");
		try{this.memberCount = results.getInt("treaty.members");}catch(SQLException ignored){}
	}

	/**
	 * Creates a treaty object
	 * @param connection The connection object to use
	 * @param id         The Treaty id
	 * @param safe       Whether the results should be writable
	 * @throws SQLException if an SQL error occurs
	 */
	public Treaty(Connection connection, int id, boolean safe) throws SQLException
	{
		super(TABLE_NAME, id, null);
		this.conn = connection;
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT name, flag, description, id, COUNT(nation_id)" +
					"FROM cloc_treaties, cloc_treaties_members " + "WHERE id=? AND id=alliance_id FOR UPDATE ");
		}
		else
		{
			read = connection.prepareStatement("SELECT name, flag, description, id, COUNT(nation_id)" +
					"FROM cloc_treaties, cloc_treaties_members " + "WHERE id=? AND id=alliance_id");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new TreatyNotFoundException("No treaty with that id!");
		}
		else
		{
			this.id = id;
			this.name = results.getString(1);
			this.flag = results.getString(2);
			this.description = results.getString(3);
			this.memberCount = results.getInt(5);
		}
	}

	public String getTreatyUrl()
	{
		return "<a href=\"/treaty/" + id + "\"><b>" + this.name + "</b></a>";
	}

	public void setName(String name) throws SQLException
	{
		if(name.length() > 32)
		{
			throw new IllegalArgumentException("Name can not be longer than 32 characters!");
		}
		else
		{
			this.addField("name", name);
			this.name = name;
		}
	}

	public void setFlag(String flag) throws SQLException
	{
		if(flag.length() > 32)
		{
			throw new IllegalArgumentException("Flag can not be longer than 32 characters!");
		}
		else
		{
			this.addField("flag", flag);
			this.flag = flag;
		}
	}

	public void setDescription(String description) throws SQLException
	{
		this.addField("description", description);
		this.description = description;
	}

	public ArrayList<TreatyMember> getMembers() throws SQLException
	{
		if(members == null)
		{
			members = new ArrayList<>(memberCount);
			PreparedStatement getMembers = conn.prepareStatement("SELECT * FROM cloc_login\n" +
					"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
					"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
					"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
					"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
					"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
					"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
					"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
					"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
					"JOIN cloc_treaties_members ON cloc_login.id = nation_id\n" +
					"JOIN cloc_treaties treaty ON cloc_treaties_members.alliance_id = treaty.id\n" +
					"WHERE alliance_id=?");
			getMembers.setInt(1, this.id);
			ResultSet results = getMembers.executeQuery();
			while(results.next())
			{
				members.add(new TreatyMember(results.getInt("cloc_login.id"), results));
			}
		}
		return members;
	}

	/**
	 * Checks if a treaty be deleted by a user, and then deletes it if they have permission to do so
	 * @param member the member attempting to delete the Treaty
	 * @throws SQLException if a database error occurs
	 */
	public void delete(TreatyMember member) throws SQLException
	{
		if(!member.isFounder())
		{
			throw new TreatyPermissionException("Only the founder can do this!");
		}
		else
		{
			delete();
		}
	}

	/**
	 * Deletes this Treaty from the database without checking who's deleting it
	 * @throws SQLException if a database error occurs
	 */
	public void delete() throws SQLException
	{
		PreparedStatement deleteMembers = conn.prepareStatement("DELETE FROM cloc_treaties_members WHERE alliance_id=?");
		PreparedStatement deleteAlliance = conn.prepareStatement("DELETE FROM cloc_treaties WHERE id=?");
		PreparedStatement deleteInvites = conn.prepareStatement("DELETE FROM cloc_treaty_invites WHERE alliance_id=?");
		deleteInvites.setInt(1, this.id);
		deleteMembers.setInt(1, this.id);
		deleteAlliance.setInt(1, this.id);
		deleteMembers.execute();
		deleteInvites.execute();
		deleteAlliance.execute();
	}
}
