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

import com.watersfall.clocmath.PolicyMath;
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
			PreparedStatement read = conn.prepareStatement("SELECT industry, milindustry, rm, oil, mg FROM cloc "
					+ "WHERE sess=? FOR UPDATE");
			read.setString(1, sess);
			results = read.executeQuery();
			if(!results.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else
			{
				int costRm = PolicyMath.getFactoryRmCost(results);
				int costOil = PolicyMath.getFactoryOilCost(results);
				int costMg = PolicyMath.getFactoryMgCost(results);
				if(results.getInt("rm") < costRm)
				{
					writer.append("<p>You do not have enough raw material!</p>");
				}
				else if(results.getInt("oil") < costOil)
				{
					writer.append("<p>You do not have enough oil!</p>");
				}
				else if(results.getInt("mg") < costMg)
				{
					writer.append("<p>You do not have enough manufactured goods!</p>");
				}
				else
				{
					PreparedStatement update = conn.prepareStatement("UPDATE cloc "
							+ "SET rm=rm-?, oil=oil-?, mg=mg-?, industry=industry+1 "
							+ "WHERE sess=?");
					update.setInt(1, costRm);
					update.setInt(2, costOil);
					update.setInt(3, costMg);
					update.setString(4, sess);
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
