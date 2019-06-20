package com.watersfall.clocgame.servlet;

import com.watersfall.clocgame.database.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/nationresults")
public class NationResults extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String sess = request.getSession().getId();
		PrintWriter writer = response.getWriter();
		Connection conn = null;
		try
		{
			String type, param;
			conn = Database.getDataSource().getConnection();
			int id = Integer.parseInt(request.getParameter("id"));
			type = ((param = request.getParameter("sendoil")) != null) ? "oil"
					: ((param = request.getParameter("sendiron")) != null) ? "iron"
					: ((param = request.getParameter("sendcoal")) != null) ? "coal"
					: ((param = request.getParameter("sendmg")) != null) ? "manufactured"
					: ((param = request.getParameter("sendcash")) != null) ? "budget"
					: null;
			PreparedStatement sender = conn.prepareStatement("SELECT " + type + ", cloc_economy.id FROM cloc_economy, cloc_login WHERE sess=? AND cloc_login.id=cloc_economy.id FOR UPDATE");
			sender.setString(1, sess);
			PreparedStatement reciever = conn.prepareStatement("SELECT " + type + ", id FROM cloc_economy WHERE id=? FOR UPDATE");
			reciever.setInt(1, id);
			ResultSet resultsSender = sender.executeQuery();
			ResultSet resultsReciever = reciever.executeQuery();
			if(!resultsSender.first())
			{
				writer.append("<p>You must be logged in to do this!</p>");
			}
			else if(!resultsReciever.first())
			{
				writer.append("<p>Nation with that id does not exist!</p>");
			}
			else if(resultsSender.getInt("id") == resultsReciever.getInt("id"))
			{
				writer.append("<p>You appreciate the gift to yourself!</p>");
			}
			else if(Integer.parseInt(param) <= 0)
			{
				writer.append("<p>Cannot send 0 or less!</p>");
			}
			else if(resultsSender.getInt(type) < Integer.parseInt(param))
			{
				writer.append("<p>You do not have enough to send!</p>");
			}
			else
			{
				PreparedStatement send = conn.prepareStatement("UPDATE cloc_economy, cloc_login SET " + type + "=" + type + "-? WHERE sess=? AND cloc_login.id = cloc_economy.id");
				send.setInt(1, Integer.parseInt(param));
				send.setString(2, sess);
				PreparedStatement recieve = conn.prepareStatement("UPDATE cloc_economy SET " + type + "=" + type + "+? WHERE id=?");
				recieve.setInt(1, Integer.parseInt(param));
				recieve.setInt(2, id);
				send.execute();
				recieve.execute();
				conn.commit();
			}
		}
		catch(SQLException e)
		{
			writer.append("Error: " + e.getLocalizedMessage());
			try
			{
				conn.rollback();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
		catch(NumberFormatException | NullPointerException e)
		{
			writer.append("<p>Don't do that</p>");
		}
		catch(Exception e)
		{
			writer.append("<p>Error: " + e.getLocalizedMessage() + "</p>");
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
