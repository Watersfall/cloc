package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.SettingsActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.NationCosmetic;
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

@WebServlet(urlPatterns = {"/settings.jsp", "/settings.do"})
public class SettingsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/settings.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		String action = req.getParameter("action");
		Connection connection = null;
		try
		{
			connection = Database.getDataSource().getConnection();
			int user = UserUtils.getUser(req);
			NationCosmetic cosmetic = new NationCosmetic(connection, user, true);
			switch(action)
			{
				case "flag":
					writer.append(SettingsActions.updateFlag(cosmetic, req.getParameter("flag")));
					break;
				case "portrait":
					writer.append(SettingsActions.updatePortrait(cosmetic, req.getParameter("portrait")));
					break;
				case "nationTitle":
					writer.append(SettingsActions.updateNationTitle(cosmetic, req.getParameter("nationTitle")));
					break;
				case "leaderTitle":
					writer.append(SettingsActions.updateLeaderTitle(cosmetic, req.getParameter("leaderTitle")));
					break;
				case "description":
					writer.append(SettingsActions.updateDescription(cosmetic, req.getParameter("description")));
					break;
				case "all":
					writer.append(SettingsActions.updateAll(cosmetic,
							req.getParameter("flag"),
							req.getParameter("portrait"),
							req.getParameter("nationTitle"),
							req.getParameter("leaderTitle"),
							req.getParameter("description")
					));
					break;
				default:
					writer.append(Responses.genericError());
			}
			connection.commit();
		}
		catch(SQLException e)
		{
			try
			{
				connection.rollback();
				writer.append(Responses.genericException(e));
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
		catch(NotLoggedInException e)
		{
			writer.append(Responses.noLogin());
		}
		catch(NullPointerException e)
		{
			writer.append(Responses.genericError());
		}
		catch(NationNotFoundException e)
		{
			writer.append(Responses.noNation());
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch(Exception e)
			{
				//Ignore
			}
		}
	}
}
