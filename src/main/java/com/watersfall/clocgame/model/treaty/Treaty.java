package com.watersfall.clocgame.model.treaty;

import com.watersfall.clocgame.exception.TreatyNotFoundException;
import com.watersfall.clocgame.exception.ValueException;
import com.watersfall.clocgame.model.nation.NationBase;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;

public class Treaty extends NationBase
{
	public static Treaty createTreaty(Connection conn, String name) throws SQLException
	{
		PreparedStatement create = conn.prepareStatement("INSERT INTO cloc_treaties (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
		create.setString(1, name);
		create.executeUpdate();
		ResultSet results = create.getGeneratedKeys();
		results.first();
		return new Treaty(conn, results.getInt(1), false);
	}

	private @Getter int id;
	private @Getter String name;
	private @Getter String flag;
	private @Getter String description;
	private @Getter int memberCount;
	private @Getter ArrayList<TreatyMember> members;

	public Treaty(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		ResultSet treaty;
		if(safe)
		{
			read = connection.prepareStatement("SELECT alliance_id FROM cloc_treaties_members WHERE nation_id=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT alliance_id FROM cloc_treaties_members WHERE nation_id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new TreatyNotFoundException("Nation not in any treaties!");
		}
		else
		{
			if(safe)
			{
				read = connection.prepareStatement("SELECT name, flag, description, id " + "FROM cloc_treaties " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			else
			{
				read = connection.prepareStatement("SELECT name, flag, description, id " + "FROM cloc_treaties " + "WHERE id=?");
			}
			read.setInt(1, results.getInt(1));
			treaty = read.executeQuery();
			if(!treaty.first())
			{
				throw new TreatyNotFoundException("Treaty does not exist!");
			}
			this.name = treaty.getString(1);
			this.flag = treaty.getString(2);
			this.description = treaty.getString(3);
			this.id = treaty.getInt(4);
		}
	}

	/**
	 *
	 * @param connection The connection object to use
	 * @param id The Treaty id
	 * @param safe Whether the results should be writable
	 * @param lazyLoad Controls how much of the Treaty is loaded.
	 * On <i>true</i>, only loads the Treaty,
	 * on <i>false</i>, loads the Treaty as well as all it's members
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
			this.memberCount = resultsMembers.getFetchSize();
		}
		if(!lazyLoad)
		{
			while(results.next())
			{
				members.add(new TreatyMember(connection, resultsMembers.getInt(1), false));
			}
		}
		if(!safe)
		{
			connection.close();
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
}
