package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.News;

import java.sql.*;
import java.util.ArrayList;

public class NewsDao extends Dao
{
	private static final String CREATE_NEWS_SQL_STATEMENT =
					"INSERT INTO cloc_news (sender, receiver, content, image, time)\n" +
					"VALUES (?,?,?,?,?)";
	private static final String NEWS_PAGE_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM cloc_news\n" +
					"WHERE receiver=?\n" +
					"ORDER BY time DESC\n" +
					"LIMIT 100\n" +
					"OFFSET ?\n";
	private static final String MARK_READ_SQL_STATEMENT =
					"UPDATE cloc_news\n" +
					"SET is_read=?\n" +
					"WHERE receiver=?\n";
	private static final String DELETE_ALL_NEWS_SQL_STATEMENT =
					"DELETE FROM cloc_news\n" +
					"WHERE receiver=?\n";
	private static final String DELETE_NEWS_SQL_STATEMENT =
					"DELETE FROM cloc_news\n" +
					"WHERE id=?\n";

	public NewsDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public void createNews(int sender, int receiver, String content) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_NEWS_SQL_STATEMENT);
		statement.setInt(1, sender);
		statement.setInt(2, receiver);
		statement.setString(3, content);
		statement.setNull(4, Types.VARCHAR);
		statement.setLong(5, System.currentTimeMillis());
		statement.execute();
	}

	public ArrayList<News> getNewsPage(int page, int nation) throws SQLException
	{
		PreparedStatement getNews = connection.prepareStatement(NEWS_PAGE_SQL_STATEMENT);
		getNews.setInt(1, nation);
		getNews.setInt(2, (page - 1) * 100);
		ResultSet newsResults = getNews.executeQuery();
		ArrayList<News> list = new ArrayList<>();
		while(newsResults.next())
		{
			list.add(new News(newsResults));
		}
		return list;
	}

	public ArrayList<News> getNewsPage(int page, Nation nation) throws SQLException
	{
		return getNewsPage(page, nation.getId());
	}

	public void markNewsAsRead(int nation) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement markAsRead = connection.prepareStatement(MARK_READ_SQL_STATEMENT);
		markAsRead.setBoolean(1, true);
		markAsRead.setInt(2, nation);
		markAsRead.execute();
	}

	public void markNewsAsRead(Nation nation) throws SQLException
	{
		markNewsAsRead(nation.getId());
	}

	public void deleteAllNews(Nation nation) throws SQLException
	{
		deleteAllNews(nation.getId());
	}

	public void deleteAllNews(int nation) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement delete = connection.prepareStatement(DELETE_ALL_NEWS_SQL_STATEMENT);
		delete.setInt(1, nation);
		delete.execute();
	}

	public void deleteNewsById(int id) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement delete = connection.prepareStatement(DELETE_NEWS_SQL_STATEMENT);
		delete.setInt(1, id);
		delete.execute();
	}
}
