package com.watersfall.clocgame.model.nation;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class News
{
	private @Getter int id;
	private @Getter int sender;
	private @Getter int receiver;
	private @Getter String content;
	private @Getter String image;
	private @Getter ResultSet results;
	private @Getter int row;

	private static void setRow(ResultSet results, int row) throws SQLException
	{
		if(results.getRow() != row)
		{
			results.beforeFirst();
			while(results.next())
			{
				if(results.getRow() == row)
				{
					return;
				}
			}
		}
	}

	public News(ResultSet results) throws SQLException
	{
		this.results = results;
		this.sender = results.getInt(1);
		this.receiver = results.getInt(2);
		this.content = results.getString(3);
		this.image = results.getString(4);
		this.id = results.getInt(5);
		this.row = results.getRow();
	}


	public void update() throws SQLException
	{
		setRow(this.results, this.row);
		results.updateRow();
	}
}
