package com.watersfall.clocgame.servlet.policies.economy;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocmath.math.PolicyMath;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Chris
 */
@WebServlet(urlPatterns = "/policies/coalmine")
public class PolicyCoalMine extends HttpServlet
{

	static BasicDataSource database = Database.getDataSource();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String sess = request.getSession().getId();
		PrintWriter writer = response.getWriter();
		Connection conn = null;
		try
		{
			conn = database.getConnection();
			ResultSet results;
			PreparedStatement read = conn.prepareStatement("SELECT budget, iron_mines, coal_mines FROM cloc_economy, cloc_login "
					+ "WHERE sess=? AND cloc_login.id=cloc_economy.id FOR UPDATE");
			read.setString(1, sess);
			results = read.executeQuery();
			if(!results.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else
			{
				int cost = PolicyMath.getMineCost(results);
				if(cost > results.getInt("budget"))
				{
					writer.append("<p>You do not have enough money!</p>");
				}
				else
				{
					PreparedStatement update = conn.prepareStatement("UPDATE cloc_economy, cloc_login SET budget=budget-?, coal_mines=coal_mines+1 "
							+ "WHERE sess=? AND cloc_login.id = cloc_economy.id");
					update.setInt(1, cost);
					update.setString(2, sess);
					update.execute();
					conn.commit();
					writer.append("<p>You dig a new coal mine!</p>");
				}
			}
		}
		catch(SQLException e)
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
