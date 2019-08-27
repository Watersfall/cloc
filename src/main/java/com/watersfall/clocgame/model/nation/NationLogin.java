package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.ValueException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationLogin extends NationBase
{
	private int id;
	private @Getter
	String username;
	private @Getter
	String email;
	private @Getter
	String password;
	private @Getter
	String ipRegister;
	private @Getter
	String ipLast;

	public NationLogin(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT username, email, password, register_ip, last_ip, id " + "FROM cloc_login " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT username, email, password, register_ip, last_ip, id " + "FROM cloc_login " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.connection = connection;
			this.id = id;
			this.safe = safe;
			this.username = results.getString(1);
			this.email = results.getString(2);
			this.password = results.getString(3);
			this.ipRegister = results.getString(4);
			this.ipLast = results.getString(5);
		}
	}

	public void setUsername(String username) throws SQLException
	{
		if(username.length() > 32)
		{
			throw new ValueException("Can not be longer than 32 characters!");
		}
		else
		{
			results.updateInt(username, 1);
		}
	}

	public void setEmail(String email) throws SQLException
	{
		if(email.length() > 32767)
		{
			throw new ValueException("Can not be longer than 32,767 characters!");
		}
		else
		{
			results.updateInt(email, 2);
		}
	}

	public void setPassword(String password) throws SQLException
	{
		if(password.length() > 32)
		{
			throw new ValueException("Can not be longer than 32 characters!");
		}
		else
		{
			results.updateInt(password, 3);
		}
	}

	public void setIpRegister(String ip) throws SQLException
	{
		if(ip.length() > 15)
		{
			throw new ValueException("Can not be longer than 15 characters!");
		}
		else
		{
			results.updateInt(ip, 4);
		}
	}

	public void setIpLast(String ip) throws SQLException
	{
		if(ip.length() > 15)
		{
			throw new ValueException("Can not be longer than 15 characters!");
		}
		else
		{
			results.updateInt(ip, 4);
		}
	}

}
