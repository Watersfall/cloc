package com.watersfall.clocgame.listeners;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.schedulers.DayScheduler;
import com.watersfall.clocgame.schedulers.MonthScheduler;
import com.watersfall.clocgame.schedulers.StatsScheduler;
import com.watersfall.clocgame.util.Time;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Startup implements ServletContextListener
{
	private static void generateSvgData() throws Exception
	{
		Path input = Paths.get("C:\\", "Users", "Chris", "Desktop", "map.svg");
		List<String> svg = Files.readAllLines(input);
		for(String string : svg)
		{
			string = string.trim();
			if(string.startsWith("<path"))
			{
				string = string.substring(5).trim();
				String[] attributes = string.split(" ", 3);
				String id = attributes[0].split("=")[1];
				String transform = attributes[1].split("=")[1];
				String d = attributes[2].split("=")[1];
				String newId = "";
				boolean first = true;
				for(Character ch : id.replace("\"", "").trim().toCharArray())
				{
					if(!first && Character.isUpperCase(ch))
					{
						newId = newId.concat("_" + ch);
					}
					else
					{
						newId = newId.concat(Character.toUpperCase(ch) + "");
					}
					first = false;
				}
				System.out.print(newId + "(\"id\", " + id + ", \"transform\", " + transform + ", \"d\", " + d + "),\n");
			}
		}
	}

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
			Time.month = results.getLong(1);
			Time.day = results.getLong(2);
			Time.currentMonth = (int)(Time.month % 12);
			generateSvgData();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			//Ignore
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
