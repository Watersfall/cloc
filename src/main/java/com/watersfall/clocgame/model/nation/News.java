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
	public static final int ID_NAVAL_BATTLE = 11;
	public static final int ID_NAVAL_BOMBARD = 12;
	public static final int ID_AIR_BATTLE = 13;
	public static final int ID_AIR_BOMBARD = 14;
	public static final int ID_DEFENSIVE_CITY_LOST = 15;
	public static final int ID_DEFENSIVE_CITY_WON = 16;
	public static final int ID_FORTIFICATION = 17;
	public static final int ID_AIR_BOMB_TROOPS = 18;

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
				return "%SENDER% has defeated our army in battle! We lost %LOST%k soldiers and killed %KILLED%k enemy soldiers!";
			case ID_DEFENSIVE_WON:
				return "%SENDER% attacked our army, but has been defeated! We lost %LOST%k soldiers and killed %KILLED%k enemy soldiers!";
			case ID_DEFENSIVE_CITY_LOST:
				return "%SENDER% has attacked and occupied %CITY%! We lost %LOST%k soldiers and killed %KILLED%k enemy soldiers!";
			case ID_DEFENSIVE_CITY_WON:
				return "%SENDER% has attacked the city of %CITY%! We killed %KILLED%k enemy soldiers, with %LOST%k of our soldiers dying in the defense of the city!";
			case ID_FORTIFICATION:
				return "Our scouts report that %SENDER%'s army has further fortified their position";
			case ID_NAVAL_BATTLE:
				return "%SENDER% has attacked our navy! We have lost %DEFENDERBBLOSSES% Battleships, " +
						"%DEFENDERPBLOSSES% Pre-Dreadnought Battleships, " +
						"%DEFENDERCLLOSSES% Cruisers, %DEFENDERDDLOSSES% Destroyers, and %DEFENDERSSLOSSES% submarines. " +
						"However, we managed to sink %ATTACKERBBLOSSES% Battleships, " +
						"%ATTACKERPBLOSSES% Pre-Dreadnought Battleships, " +
						"%ATTACKERCLLOSSES% Cruisers, %ATTACKERDDLOSSES% Destroyers, and %ATTACKERSSLOSSES% submarines.";
			case ID_NAVAL_BOMBARD:
				return "%SENDER% has bombarded the city of %CITY% with their navy!";
			case ID_AIR_BATTLE:
				return "%s has attacked our airforce! They have shot down %s ," +
						"and we managed to shoot down %s !";
			case ID_AIR_BOMBARD:
				return "%SENDER% has bombed the city of %CITY% with their airforce!";
			case ID_AIR_BOMB_TROOPS:
				return "%SENDER% has bombed our army with their airforce, killing %KILLS%k of our troops!";
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

	public News(ResultSet results) throws SQLException
	{
		this.sender = results.getInt("sender");
		this.receiver = results.getInt("receiver");
		this.content = results.getString("content");
		this.image = results.getString("image");
		this.time = results.getLong("time");
		this.read = results.getBoolean("is_read");
		this.id = results.getInt("id");
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
