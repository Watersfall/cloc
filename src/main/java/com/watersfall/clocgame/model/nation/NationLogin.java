package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.SQLException;

public class NationLogin extends Updatable
{
	private static final String TABLE_NAME = "cloc_login";
	private @Getter String username;
	private @Getter String email;
	private @Getter String password;
	private @Getter String ipRegister;
	private @Getter String ipLast;

	public NationLogin(int id, String username, String email, String password, String ipRegister, String ipLast)
	{
		super(TABLE_NAME, id);
		this.username = username;
		this.email = email;
		this.password = password;
		this.ipRegister = ipRegister;
		this.ipLast = ipLast;
	}

	public void setUsername(String username) throws SQLException
	{
		if(username.length() > 32)
		{
			throw new IllegalArgumentException("Can not be longer than 32 characters!");
		}
		else
		{
			this.setField("username", username);
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
			this.setField("email", email);
		}
	}

	public void setPassword(String password) throws SQLException
	{
		this.setField("password", password);
	}

	public void setIpRegister(String ip) throws SQLException
	{
		if(ip.length() > 15)
		{
			throw new IllegalArgumentException("Can not be longer than 15 characters!");
		}
		else
		{
			this.setField("register_ip", ip);
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
			this.setField("last_ip", ip);
		}
	}

}
