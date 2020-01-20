package com.watersfall.clocgame.model.nation;

import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class NationNews
{
	private @Getter int id;
	private @Getter Connection conn;
	private @Getter ResultSet results;
	private @Getter boolean safe;
	private @Getter LinkedHashMap<Integer, News> news;
	private @Getter boolean anyUnread = false;

	public NationNews(Connection conn, int id, boolean safe) throws SQLException
	{
		PreparedStatement read;
		if(safe)
		{
			read = conn.prepareStatement("SELECT sender, receiver, content, image, time, is_read, id " + "FROM cloc_news " + "WHERE receiver=? ORDER BY id DESC FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = conn.prepareStatement("SELECT sender, receiver, content, image, time, is_read, id " + "FROM cloc_news " + "WHERE receiver=? ORDER BY id DESC");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		this.news = new LinkedHashMap<>();
		this.conn = conn;
		this.safe = safe;
		this.id = id;
		while(results.next())
		{
			News temp = new News(results);
			news.put(temp.getId(), temp);
			if(!temp.isRead())
			{
				anyUnread = true;
			}
		}
	}

	public void deleteAll() throws SQLException
	{
		PreparedStatement delete = conn.prepareStatement("DELETE FROM cloc_news WHERE receiver=?");
		delete.setInt(1, this.id);
		delete.execute();
	}
}
