package com.watersfall.clocgame.model.nation;

import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
			News temp = new News(conn, results);
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

	public List<News> getNewsPage(int page)
	{
		List<News> list = new ArrayList<>(this.news.values());
		int min = page * 100;
		if(min > list.size())
		{
			min = list.size() - 1;
		}
		int max = (page + 1) * 100;
		if (max > list.size())
		{
			max = list.size() - 1;
		}
		if(max <= 0 || min < 0)
		{
			return list;
		}
		System.out.println("Min: " + min + "\nMax: " + max);
		return list.subList(min, max);
	}
}
