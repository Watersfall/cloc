package com.watersfall.clocgame.servlet.decisions;

import com.watersfall.clocgame.database.Database;
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

@WebServlet(urlPatterns = "/decisions/food")
public class DecisionFood extends HttpServlet
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
			int selection = Integer.parseInt(request.getParameter("selection"));
			conn = Database.getDataSource().getConnection();
			PreparedStatement statementCheck = conn.prepareStatement("SELECT food_change, food, cloc_policy.id " +
					"FROM cloc_policy, cloc_login " +
					"WHERE sess=? and cloc_login.id = cloc_policy.id");
			statementCheck.setString(1, sess);
			ResultSet check = statementCheck.executeQuery();
			if(!check.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else if(check.getInt("food_change") > 0)
			{
				writer.append("<p>You cannot change this yet!</p>");
			}
			else if(check.getInt("food") == selection)
			{
				writer.append("<p>You already have this policy set!</p>");
			}
			else if(selection > 2 || selection < 0)
			{
				writer.append("<p>Don't do that</p>");
			}
			else
			{
				PreparedStatement update = conn.prepareStatement("UPDATE cloc_policy SET food=?, food_change=? WHERE id=?");
				update.setInt(1, selection);
				update.setInt(2, 2);
				update.setInt(3, check.getInt("id"));
				update.execute();
				conn.commit();
				writer.append("<p>Policy changed!</p>");
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
			e.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
				writer.close();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
	}
}
