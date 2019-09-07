package com.watersfall.clocgame.listeners;

import com.watersfall.clocgame.database.Database;
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

	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		try
		{
			WeekScheduler.startWeek();
			event.getServletContext().setAttribute("database", Database.getDataSource());
			Connection conn = Database.getDataSource().getConnection();
			ResultSet results = conn.prepareStatement("SELECT turn FROM cloc_main").executeQuery();
			Util.turn = results.getInt(1);
		}
		catch(SQLException e)
		{

		}


	}
}
