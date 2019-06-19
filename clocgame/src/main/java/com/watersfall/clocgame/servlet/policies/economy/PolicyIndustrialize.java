package com.watersfall.clocgame.servlet.policies.economy;

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

import com.watersfall.clocmath.math.PolicyMath;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Chris
 */
@WebServlet(urlPatterns = "/policies/industrialize")
public class PolicyIndustrialize extends HttpServlet
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
			PreparedStatement read = conn.prepareStatement("SELECT civilian_industry, military_industry, coal, iron, oil, manufactured FROM cloc_economy, cloc_login "
					+ "WHERE sess=? AND cloc_login.id = cloc_economy.id FOR UPDATE");
			read.setString(1, sess);
			results = read.executeQuery();
			if(!results.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else
			{
				int costCoal = PolicyMath.getFactoryCoalCost(results);
				int costIron = PolicyMath.getFactoryIronCost(results);
				int costOil = PolicyMath.getFactoryOilCost(results);
				int costMg = PolicyMath.getFactoryMgCost(results);
				if(results.getInt("coal") < costCoal)
				{
					writer.append("<p>You do not have enough coal!</p>");
				}
				else if(results.getInt("iron") < costOil)
				{
					writer.append("<p>You do not have enough iron!</p>");
				}
				else if(results.getInt("oil") < costOil)
				{
					writer.append("<p>You do not have enough oil!</p>");
				}
				else if(results.getInt("manufactured") < costMg)
				{
					writer.append("<p>You do not have enough manufactured goods!</p>");
				}
				else
				{
					PreparedStatement update = conn.prepareStatement("UPDATE cloc_economy, cloc_login "
							+ "SET coal=coal-?, iron=iron-?, oil=oil-?, manufactured=manufactured-?, civilian_industry=civilian_industry+1 "
							+ "WHERE sess=? AND cloc_login.id = cloc_economy.id");
					update.setInt(1, costCoal);
					update.setInt(2, costIron);
					update.setInt(3, costOil);
					update.setInt(4, costMg);
					update.setString(5, sess);
					update.execute();
					conn.commit();
					writer.append("<p>Your farmers flock to the city for a new life!</p>");
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
