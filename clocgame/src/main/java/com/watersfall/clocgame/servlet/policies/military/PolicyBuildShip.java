package com.watersfall.clocgame.servlet.policies.military;

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
import java.util.InputMismatchException;

@WebServlet(urlPatterns = "/policies/buildship")
public class PolicyBuildShip extends HttpServlet
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
			String type = request.getParameter("ship");
			conn = database.getConnection();
			ResultSet results;
			PreparedStatement read = conn.prepareStatement("SELECT coal, manufactured, submarines, destroyers, cruisers, battleships, oil, coal, manufactured FROM cloc_military, cloc_economy, cloc_login "
					+ "WHERE sess=? AND cloc_login.id=cloc_economy.id AND cloc_login.id = cloc_military.id FOR UPDATE");
			read.setString(1, sess);
			results = read.executeQuery();
			if(!results.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else
			{
				PreparedStatement update = null;
				int costMg;
				int costOil;
				switch(type)
				{
					case "ss":
						costMg = PolicyMath.getSubmarineMgCost(results);
						costOil = PolicyMath.getSubmarineOilCost(results);
						if(results.getInt("manufactured") < costMg)
						{
							writer.write("<p>You do not have enough manufacturing capacity!</p>");
						}
						else if(results.getInt("oil") < costOil)
						{
							writer.write("<p>You do not have enough oil!</p>");
						}
						else
						{
							update = conn.prepareStatement("UPDATE cloc_military, cloc_login, cloc_economy " +
									"SET submarines=submarines+1, manufactured=manufactured-?, oil=oil-? " +
									"WHERE sess=? AND cloc_login.id = cloc_military.id AND cloc_login.id = cloc_economy.id");
							update.setInt(1, costMg);
							update.setInt(2, costOil);
							update.setString(3, sess);
							writer.write("<p>You add a brand new submarine to your navy!</p>");
							update.execute();
						}
						break;
					case "bb":
						costMg = PolicyMath.getBattleshipMgCost(results);
						costOil = PolicyMath.getBattleshipOilCost(results);
						if(results.getInt("manufactured") < costMg)
						{
							writer.write("<p>You do not have enough manufacturing capacity!</p>");
						}
						else if(results.getInt("coal") < costOil)
						{
							writer.write("<p>You do not have enough coal!</p>");
						}
						else
						{
							update = conn.prepareStatement("UPDATE cloc_military, cloc_login, cloc_economy " +
									"SET battleships=battleships+1, manufactured=manufactured-?, coal=coal-? " +
									"WHERE sess=? AND cloc_login.id = cloc_economy.id AND cloc_login.id = cloc_military.id");
							update.setInt(1, costMg);
							update.setInt(2, costOil);
							update.setString(3, sess);
							writer.write("<p>You add a brand new battleship to your navy!</p>");
							update.execute();
						}
						break;
					case "dd":
						costMg = PolicyMath.getDestroyerMgCost(results);
						costOil = PolicyMath.getDestroyerOilCost(results);
						if(results.getInt("manufactured") < costMg)
						{
							writer.write("<p>You do not have enough manufacturing capacity!</p>");
						}
						else if(results.getInt("coal") < costOil)
						{
							writer.write("<p>You do not have enough coal!</p>");
						}
						else
						{
							update = conn.prepareStatement("UPDATE cloc_military, cloc_login, cloc_economy " +
									"SET destroyers=destroyers+1, manufactured=manufactured-?, coal=coal-? " +
									"WHERE sess=? AND cloc_login.id = cloc_economy.id AND cloc_login.id = cloc_military.id");
							update.setInt(1, costMg);
							update.setInt(2, costOil);
							update.setString(3, sess);
							writer.write("<p>You add a brand new destroyer to your navy!</p>");
							update.execute();
						}
						break;
					case "cl":
						costMg = PolicyMath.getCruiserMgCost(results);
						costOil = PolicyMath.getCruiserOilCost(results);
						if(results.getInt("manufactured") < costMg)
						{
							writer.write("<p>You do not have enough manufacturing capacity!</p>");
						}
						else if(results.getInt("coal") < costOil)
						{
							writer.write("<p>You do not have enough coal!</p>");
						}
						else
						{
							update = conn.prepareStatement("UPDATE cloc_military, cloc_login, cloc_economy " +
									"SET cruisers=cruisers+1, manufactured=manufactured-?, coal=coal-? "  +
									"WHERE sess=? AND cloc_login.id = cloc_economy.id AND cloc_login.id = cloc_military.id");
							update.setInt(1, costMg);
							update.setInt(2, costOil);
							update.setString(3, sess);
							writer.write("<p>You add a brand new cruiser to your navy!</p>");
							update.execute();
						}
						break;
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
		catch(Exception e)
		{
			writer.write("<p>Don't do that.</p>");
		}
		finally
		{
			try
			{
				conn.commit();
				conn.close();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
	}
}
