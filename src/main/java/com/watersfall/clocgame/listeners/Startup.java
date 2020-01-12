package com.watersfall.clocgame.listeners;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.schedulers.DayScheduler;
import com.watersfall.clocgame.schedulers.WeekScheduler;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Startup implements ServletContextListener
{
	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		try
		{
			Database.getDataSource().close();
			WeekScheduler.stopWeek();
			DayScheduler.stopDay();
		}
		catch(Exception e)
		{
			//Ignore
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		Connection conn = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			WeekScheduler.startWeek();
			DayScheduler.startDay();
			event.getServletContext().setAttribute("database", Database.getDataSource());
			conn = Database.getDataSource().getConnection();
			ResultSet results = conn.prepareStatement("SELECT turn FROM cloc_main").executeQuery();
			results.first();
			Util.turn = results.getInt(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			//Ignore
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{

				//Ignore
				e.printStackTrace();
			}
		}

	}
}
