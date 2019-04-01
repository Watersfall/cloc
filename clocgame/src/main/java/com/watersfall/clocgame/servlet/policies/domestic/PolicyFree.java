package com.watersfall.clocgame.servlet.policies.domestic;

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

import com.watersfall.clocmath.PolicyConstants;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Chris
 */
@WebServlet(urlPatterns = "/policies/free")
public class PolicyFree extends HttpServlet
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
			PreparedStatement read = conn.prepareStatement("SELECT budget, approval, stability, political FROM cloc "
					+ "WHERE sess=? FOR UPDATE");
			read.setString(1, sess);
			results = read.executeQuery();
			if(!results.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			if(results.getInt("budget") < PolicyConstants.COST_FREE)
			{
				writer.append("<p>You do not have enough money!</p>");
			}
			else if(results.getInt("political") <= 4)
			{
				writer.append("<p>You have no more prisoners to free!</p>");
			}
			else if(results.getInt("stability") <= 4)
			{
				writer.append("<p>You are not stable enough!</p>");
			}
			else
			{
				PreparedStatement update = conn.prepareStatement("UPDATE cloc "
						+ "SET stability=stability+?, approval=approval+?, political=political+?, budget=budget-? "
						+ "WHERE sess=?");
				update.setInt(1, PolicyConstants.GAIN_STABILITY_FREE);
				update.setInt(2, PolicyConstants.GAIN_APPROVAL_FREE);
				update.setInt(3, PolicyConstants.GAIN_GOVERNMENT_FREE);
				update.setInt(4, PolicyConstants.COST_FREE);
				update.setString(5, sess);
				PreparedStatement update2 = conn.prepareStatement("UPDATE cloc SET approval=100 "
						+ "WHERE approval>100 && sess=?");
				update2.setString(1, sess);
				update.execute();
				update2.execute();
				conn.commit();
				writer.append("<p>Your convicts enjoy their freedom!</p>");
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
