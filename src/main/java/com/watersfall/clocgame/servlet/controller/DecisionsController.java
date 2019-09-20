package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.DecisionActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/decisions.jsp", "/decisions.do"})
public class DecisionsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/decisions.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		String decision = req.getParameter("decision");
		Connection connection = null;
		try
		{
			connection = Database.getDataSource().getConnection();
			int user = UserUtils.getUser(req);
			int selection = Integer.parseInt(req.getParameter("selection"));
			switch(decision)
			{
				case "manpower":
					writer.append(DecisionActions.manpower(connection, user, selection));
					break;
				case "food":
					writer.append(DecisionActions.food(connection, user, selection));
					break;
				case "economy":
					writer.append(DecisionActions.economy(connection, user, selection));
					break;
				default:
					writer.append(Responses.genericError());
					break;
			}
		}
		catch(SQLException e)
		{
			try
			{
				connection.rollback();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
		catch(NumberFormatException | NullPointerException e)
		{
			writer.append(Responses.genericError());
		}
		catch(NotLoggedInException e)
		{
			writer.append(Responses.noLogin());
		}
		catch(NationNotFoundException e)
		{
			writer.append(Responses.noNation());
		}
	}
}
