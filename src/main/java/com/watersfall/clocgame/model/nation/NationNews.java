package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NationNews extends NationBase
{

	private @Getter
	ArrayList<News> news;

	public NationNews(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT sender, receiver, content, image, id " + "FROM cloc_news " + "WHERE receiver=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT sender, receiver, content, image, id " + "FROM cloc_news " + "WHERE receiver=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			results.beforeFirst();
			this.news = new ArrayList<>();
			this.connection = connection;
			this.safe = safe;
			this.id = id;
			while(results.next())
			{
				news.add(new News(results));
			}
		}
	}

	public void updateAll() throws SQLException
	{
		results.beforeFirst();
		while(results.next())
		{
			results.updateRow();
		}
	}

}
