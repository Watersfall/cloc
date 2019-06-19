package com.watersfall.clocgame.servlet.policies.foreign;

import com.watersfall.clocgame.database.Database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.watersfall.clocmath.constants.PolicyConstants;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Chris
 */
@WebServlet(urlPatterns = "/policies/alignneutral")
public class PolicyAlignNeutral extends HttpServlet
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
			PreparedStatement read = conn.prepareStatement("SELECT budget, alignment FROM cloc_economy, cloc_foreign, cloc_login "
					+ "WHERE sess=? AND cloc_login.id=cloc_economy.id AND cloc_login.id = cloc_foreign.id FOR UPDATE");
			read.setString(1, sess);
			results = read.executeQuery();
			if(!results.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else
			{
				int cost = PolicyConstants.COST_ALIGN_NEUTRAL;
				if(cost > results.getInt("budget"))
				{
					writer.append("<p>You do not have enough money!</p>");
				}
				else if(results.getInt("alignment") == 156)
				{
					writer.append("<p>You are already Neutral!</p>");
				}
				else
				{
					PreparedStatement update = conn.prepareStatement("UPDATE cloc_foreign, cloc_economy, cloc_login " +
							"SET alignment=1, budget=budget-? "
							+ "WHERE sess=? AND cloc_login.id = cloc_economy.id AND cloc_login.id = cloc_foreign.id");
					update.setInt(1, cost);
					update.setString(2, sess);
					update.execute();
					conn.commit();
					writer.append("<p>Your people cheer as you declare your neutrality!</p>");
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
