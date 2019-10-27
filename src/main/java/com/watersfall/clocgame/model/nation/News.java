package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class News
{
	private @Getter Connection conn;
	private @Getter int id;
	private @Getter int sender;
	private @Getter int receiver;
	private @Getter long time;
	private @Getter boolean read;
	private @Getter String content;
	private @Getter String image;

	public static final int ID_SEND_COAL = 0;
	public static final int ID_SEND_IRON = 1;
	public static final int ID_SEND_OIL = 2;
	public static final int ID_SEND_STEEL = 3;
	public static final int ID_SEND_NITROGEN = 4;
	public static final int ID_SEND_RESEARCH = 5;
	public static final int ID_SEND_MONEY = 6;
	public static final int ID_DECLARE_WAR = 7;
	public static final int ID_DEFENSIVE_LOST = 8;
	public static final int ID_DEFENSIVE_WON = 9;
	public static final int ID_WAR_LOST = 10;

	private static String sendMessage(String resource)
	{
		return "%SENDER% has sent us %AMOUNT% " + resource + "!";
	}

	public static String createMessage(int id)
	{
		switch(id)
		{
			case ID_SEND_COAL:
				return sendMessage("coal");
			case ID_SEND_IRON:
				return sendMessage("iron");
			case ID_SEND_OIL:
				return sendMessage("oil");
			case ID_SEND_STEEL:
				return sendMessage("steel");
			case ID_SEND_NITROGEN:
				return sendMessage("nitrogen");
			case ID_SEND_RESEARCH:
				return sendMessage("research");
			case ID_SEND_MONEY:
				return "%SENDER% has wired us $%AMOUNT%k!";
			case ID_DECLARE_WAR:
				return "%SENDER% has declared war on us!";
			case ID_DEFENSIVE_LOST:
				return "%SENDER% has defeated our army in battle! We lost %LOST% soldiers and killed %KILLED% enemy soldiers!";
			case ID_DEFENSIVE_WON:
				return "%SENDER% attacked our army, but has been defeated! We lose %LOST% soldiers and killed %KILLED% enemy soldiers!";
			case ID_WAR_LOST:
				return "%SENDER% has defeated us in war, stealing nothing because I haven't written that part yet!";
		}
		return "";
	}

	public static void sendNews(Connection conn, int sender, int receiver, String message) throws SQLException
	{
		long now = System.currentTimeMillis();
		PreparedStatement write = conn.prepareStatement("INSERT INTO cloc_news (sender, receiver, content, image, time) " +
				"VALUES (?,?,?,?,?)");
		write.setInt(1, sender);
		write.setInt(2, receiver);
		write.setString(3, message);
		write.setString(4, null);
		write.setLong(5, now);
		write.execute();
	}

	public News(Connection conn, ResultSet results) throws SQLException
	{
		this.conn = conn;
		this.sender = results.getInt(1);
		this.receiver = results.getInt(2);
		this.content = results.getString(3);
		this.image = results.getString(4);
		this.time = results.getLong(5);
		this.read = results.getBoolean(6);
		this.id = results.getInt(7);
	}

	public News(Connection conn, int id, boolean safe) throws SQLException
	{
		this.conn = conn;
		PreparedStatement read;
		if(safe)
		{
			read = conn.prepareStatement("SELECT sender, receiver, content, image, time, is_read, id " + "FROM cloc_news " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = conn.prepareStatement("SELECT sender, receiver, content, image, time, is_read, id " + "FROM cloc_news " + "WHERE id=?");
		}
		read.setInt(1, id);
		ResultSet results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.sender = results.getInt(1);
			this.receiver = results.getInt(2);
			this.content = results.getString(3);
			this.image = results.getString(4);
			this.time = results.getLong(5);
			this.read = results.getBoolean(6);
			this.id = results.getInt(7);
		}
	}

	public void delete() throws SQLException
	{
		PreparedStatement delete = this.conn.prepareStatement("DELETE FROM cloc_news WHERE id=?");
		delete.setInt(1, this.id);
		delete.execute();
	}

	public void read() throws SQLException
	{
		if(!this.read)
		{
			PreparedStatement read = this.conn.prepareStatement("UPDATE cloc_news SET is_read=? WHERE id=?");
			read.setBoolean(1, true);
			read.setInt(2, this.id);
			read.execute();
		}
	}
}
