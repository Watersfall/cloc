package com.watersfall.clocgame.listeners;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.constants.PolicyConstants;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Startup implements ServletContextListener
{
	@Override
	public void contextDestroyed(ServletContextEvent event)
	{

	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		event.getServletContext().setAttribute("dataSource", Database.getDataSource());
	}
}
