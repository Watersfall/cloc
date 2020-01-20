package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationLogin
{
	private @Getter Connection conn;
	private @Getter ResultSet results;
	private @Getter boolean safe;
	private @Getter int id;
	private @Getter String username;
	private @Getter String email;
	private @Getter String password;
	private @Getter String ipRegister;
	private @Getter String ipLast;

	public NationLogin(int id, String username, String email, String password, String ipRegister, String ipLast)
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.ipRegister = ipRegister;
		this.ipLast = ipLast;
	}

	public NationLogin(Connection conn, int id, boolean safe) throws SQLException
	{
		PreparedStatement read;
		if(safe)
		{
			read = conn.prepareStatement("SELECT username, email, password, register_ip, last_ip, id " + "FROM cloc_login " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = conn.prepareStatement("SELECT username, email, password, register_ip, last_ip, id " + "FROM cloc_login " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.conn = conn;
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
			throw new IllegalArgumentException("Can not be longer than 32 characters!");
		}
		else
		{
			results.updateString("username", username);
		}
	}

	public void setEmail(String email) throws SQLException
	{
		if(email.length() > 32767)
		{
			throw new IllegalArgumentException("Can not be longer than 32,767 characters!");
		}
		else
		{
			results.updateString("email", email);
		}
	}

	public void setPassword(String password) throws SQLException
	{
		if(password.length() > 32)
		{
			throw new IllegalArgumentException("Can not be longer than 32 characters!");
		}
		else
		{
			results.updateString("password", password);
		}
	}

	public void setIpRegister(String ip) throws SQLException
	{
		if(ip.length() > 15)
		{
			throw new IllegalArgumentException("Can not be longer than 15 characters!");
		}
		else
		{
			results.updateString("register_ip", ip);
		}
	}

	public void setIpLast(String ip) throws SQLException
	{
		if(ip.length() > 15)
		{
			throw new IllegalArgumentException("Can not be longer than 15 characters!");
		}
		else
		{
			results.updateString("last_ip", ip);
		}
	}

}
