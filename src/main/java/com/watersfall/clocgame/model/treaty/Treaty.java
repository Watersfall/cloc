package com.watersfall.clocgame.model.treaty;

import com.watersfall.clocgame.exception.TreatyNotFoundException;
import com.watersfall.clocgame.exception.TreatyPermissionException;
import com.watersfall.clocgame.exception.ValueException;
import com.watersfall.clocgame.model.nation.NationBase;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;

public class Treaty extends NationBase
{
	public static ArrayList<Treaty> getAllTreaties(Connection conn) throws SQLException
	{
		ArrayList<Treaty> array = new ArrayList<>();
		PreparedStatement treaties = conn.prepareStatement("SELECT id FROM cloc_treaties");
		ResultSet results = treaties.executeQuery();
		while(results.next())
		{
			array.add(new Treaty(conn, results.getInt(1), false, true));
		}
		return array;
	}

	public static Treaty createTreaty(Connection conn, String name) throws SQLException
	{
		PreparedStatement create = conn.prepareStatement("INSERT INTO cloc_treaties (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
		create.setString(1, name);
		create.executeUpdate();
		ResultSet results = create.getGeneratedKeys();
		results.first();
		return new Treaty(conn, results.getInt(1), true, true);
	}

	private @Getter int id;
	private @Getter String name;
	private @Getter String flag;
	private @Getter String description;
	private @Getter int memberCount;
	private @Getter ArrayList<TreatyMember> members;


	/**
	 * @param connection The SQL Connection
	 * @param id         The treaty ID
	 * @param safe       Whether the results should be writable
	 * @throws SQLException if an SQL error occurs
	 */
	public Treaty(Connection connection, int id, boolean safe) throws SQLException
	{
		this(connection, id, safe, true);
	}

	/**
	 * @param connection The connection object to use
	 * @param id         The Treaty id
	 * @param safe       Whether the results should be writable
	 * @param lazyLoad   Controls how much of the Treaty is loaded.
	 *                   On <i>true</i>, only loads the Treaty,
	 *                   on <i>false</i>, loads the Treaty as well as all it's members
	 * @throws SQLException if an SQL error occurs
	 */

	public Treaty(Connection connection, int id, boolean safe, boolean lazyLoad) throws SQLException
	{
		super(connection, id, safe);
		ResultSet resultsMembers;
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT name, flag, description, id " + "FROM cloc_treaties " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT name, flag, description, id " + "FROM cloc_treaties " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new TreatyNotFoundException("No treaty with that id!");
		}
		else
		{
			PreparedStatement nations = connection.prepareStatement("SELECT nation_id FROM cloc_treaties_members WHERE alliance_id=?");
			nations.setInt(1, id);
			resultsMembers = nations.executeQuery();
			this.id = id;
			this.name = results.getString(1);
			this.flag = results.getString(2);
			this.description = results.getString(3);
		}
		if(!lazyLoad)
		{
			members = new ArrayList<>();
			while(resultsMembers.next())
			{
				members.add(new TreatyMember(connection, resultsMembers.getInt(1), false));
			}
			this.memberCount = members.size();
		}
		else
		{
			int i = 0;
			while(resultsMembers.next())
			{
				i++;
			}
			memberCount = i;
		}
	}

	public void setName(String name) throws SQLException
	{
		if(name.length() > 32)
		{
			throw new ValueException("Name can not be longer than 32 characters!");
		}
		else
		{
			results.updateString(1, name);
		}
	}

	public void setFlag(String flag) throws SQLException
	{
		if(flag.length() > 32)
		{
			throw new ValueException("Flag can not be longer than 32 characters!");
		}
		else
		{
			results.updateString(2, flag);
		}
	}

	public void setDescription(String description) throws SQLException
	{
		results.updateString(3, description);
	}

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

	public void delete() throws SQLException
	{
		PreparedStatement deleteMembers = connection.prepareStatement("DELETE FROM cloc_treaties_members WHERE alliance_id=?");
		PreparedStatement deleteAlliance = connection.prepareStatement("DELETE FROM cloc_treaties WHERE id=?");
		deleteMembers.setInt(1, this.id);
		deleteAlliance.setInt(1, this.id);
		deleteMembers.execute();
		deleteAlliance.execute();
	}
}
