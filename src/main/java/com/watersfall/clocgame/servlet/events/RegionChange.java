package com.watersfall.clocgame.servlet.events;

import com.watersfall.clocgame.database.Database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Chris
 */
@WebServlet(urlPatterns = "/events/regionchange")
public class RegionChange extends HttpServlet
{

	static BasicDataSource database = Database.getDataSource();

	private static boolean checkRegion(String string)
	{
		return string.equalsIgnoreCase("north america")
				|| string.equalsIgnoreCase("south america")
				|| string.equalsIgnoreCase("africa")
				|| string.equalsIgnoreCase("europe")
				|| string.equalsIgnoreCase("asia")
				|| string.equalsIgnoreCase("middle east")
				|| string.equalsIgnoreCase("oceania");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String sess = request.getSession().getId();
		PrintWriter writer = response.getWriter();
		Connection conn = null;
		try
		{
			conn = database.getConnection();
			ResultSet hasEvent, results;
			PreparedStatement read = conn.prepareStatement("SELECT id FROM cloc_main "
					+ "WHERE sess=? AND cloc_login.id=cloc_economy.id FOR UPDATE");
			read.setString(1, sess);
			results = read.executeQuery();
			if(!results.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else
			{
				PreparedStatement check = conn.prepareStatement("SELECT eventId FROM news "
						+ "WHERE receiver=? FOR UPDATE");
				check.setInt(1, results.getInt("id"));
				hasEvent = check.executeQuery();
				if(!hasEvent.first())
				{
					writer.append("<p>You do not have this event!</p>");
				}
				else
				{
					String region = request.getParameter("region");
					if(region == null || !checkRegion(region))
					{
						region = "Siberia";
					}
					PreparedStatement update = conn.prepareStatement("UPDATE cloc SET region=? WHERE sess=? AND cloc_login.id = cloc_economy.id");
					update.setString(1, region);
					update.setString(2, sess);
					PreparedStatement delete = conn.prepareStatement("DELETE FROM news WHERE eventId=? && receiver=? ORDER BY time DESC LIMIT 1");
					delete.setInt(1, EventIds.REGION_CHANGE);
					delete.setInt(2, results.getInt("id"));
					update.execute();
					delete.execute();
					conn.commit();
					writer.append("<p>Region changed to " + region + "</p>");
				}
			}
		}
		catch(Exception e)
		{
			try
			{
				conn.rollback();
			}
			catch(Exception ex)
			{
				//Ignore
			}
			writer.append("<p>Error: " + e.getLocalizedMessage() + "!</p>");
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
	}
}
