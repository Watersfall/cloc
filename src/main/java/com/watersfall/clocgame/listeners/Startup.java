package com.watersfall.clocgame.listeners;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.schedulers.DayScheduler;
import com.watersfall.clocgame.schedulers.MonthScheduler;
import com.watersfall.clocgame.schedulers.StatsScheduler;
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
			MonthScheduler.stopMonth();
			DayScheduler.stopDay();
			StatsScheduler.stopStats();
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
			MonthScheduler.startMonth();
			DayScheduler.startDay();
			StatsScheduler.startStats();
			event.getServletContext().setAttribute("database", Database.getDataSource());
			conn = Database.getDataSource().getConnection();
			ResultSet results = conn.prepareStatement("SELECT month, day FROM cloc_main").executeQuery();
			results.first();
			Util.month = results.getLong(1);
			Util.day = results.getLong(2);
			Util.currentMonth = (int)(Util.month % 12);
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
