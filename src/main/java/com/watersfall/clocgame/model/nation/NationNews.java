package com.watersfall.clocgame.model.nation;

import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class NationNews extends NationBase
{

	private @Getter LinkedHashMap<Integer, News> news;
	private @Getter boolean anyUnread = false;

	public NationNews(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT sender, receiver, content, image, time, is_read, id " + "FROM cloc_news " + "WHERE receiver=? ORDER BY id DESC FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT sender, receiver, content, image, time, is_read, id " + "FROM cloc_news " + "WHERE receiver=? ORDER BY id DESC");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		this.news = new LinkedHashMap<>();
		this.connection = connection;
		this.safe = safe;
		this.id = id;
		if(!results.first())
		{
			return;
		}
		results.beforeFirst();
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
		PreparedStatement delete = connection.prepareStatement("DELETE FROM cloc_news WHERE receiver=?");
		delete.setInt(1, this.id);
		delete.execute();
	}
}
