package net.watersfall.clocgame.dao;

import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.news.News;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewsDao extends Dao
{
	private static final String CREATE_NEWS_SQL_STATEMENT =
					"INSERT INTO news (sender, receiver, content, time)\n" +
					"VALUES (?,?,?,?)";
	private static final String NEWS_PAGE_SQL_STATEMENT =
					"SELECT *\n" +
					"FROM news\n" +
					"WHERE receiver=?\n" +
					"ORDER BY time DESC\n" +
					"LIMIT 100\n" +
					"OFFSET ?\n";
	private static final String MARK_READ_SQL_STATEMENT =
					"UPDATE nation_stats JOIN (SELECT MAX(id) as max FROM news WHERE receiver=?) n SET last_news=n.max WHERE nation_stats.id=?";
	private static final String DELETE_ALL_NEWS_SQL_STATEMENT =
					"DELETE FROM news\n" +
					"WHERE receiver=?\n";
	private static final String DELETE_NEWS_SQL_STATEMENT =
					"DELETE FROM news\n" +
					"WHERE id=?\n";
	private static final String UNREAD_NEWS_SQL_STATEMENT =
					"SELECT * \n" +
					"FROM news\n" +
					"WHERE receiver=? AND id>?\n" +
					"LIMIT ?\n";
	private static final String GET_NEWS_BY_ID =
					"SELECT * \n" +
					"FROM news \n" +
					"WHERE id=? \n";

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
		statement.setLong(4, System.currentTimeMillis());
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

	public ArrayList<News> getUnreadNews(Nation nation) throws SQLException
	{
		PreparedStatement getNews = connection.prepareStatement(UNREAD_NEWS_SQL_STATEMENT);
		getNews.setInt(1, nation.getId());
		getNews.setLong(2, nation.getStats().getLastNews());
		getNews.setInt(3, 10);
		ResultSet results = getNews.executeQuery();
		ArrayList<News> list = new ArrayList<>();
		while(results.next())
		{
			list.add(new News(results));
		}
		return list;
	}

	public void markNewsAsRead(int nation) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement markAsRead = connection.prepareStatement(MARK_READ_SQL_STATEMENT);
		markAsRead.setInt(1, nation);
		markAsRead.setInt(2, nation);
		markAsRead.execute();
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

	public News getNewsById(int id) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_NEWS_BY_ID);
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		return new News(results);
	}
}
