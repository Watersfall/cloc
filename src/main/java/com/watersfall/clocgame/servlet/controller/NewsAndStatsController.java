package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.Stats;
import com.watersfall.clocgame.model.war.War;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = "/worldnews/*")
public class NewsAndStatsController extends HttpServlet
{
	public final static String URL = "/{page}/{number}";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection())
		{
			int page;
			try
			{
				page = Integer.parseInt(url.get("number"));
			}
			catch(NumberFormatException | NullPointerException e)
			{
				page = 1;
			}
			ArrayList<War> wars = null;
			int totalWars = 0;
			if(url.get("page") == null || url.get("page").equalsIgnoreCase("ongoing"))
			{
				wars = War.getOngoingWarPage(conn, page);
				totalWars = Util.getTotalOngoingWars(conn);
				req.setAttribute("url", "worldnews/ongoing");
			}
			else if(url.get("page").equalsIgnoreCase("ended"))
			{
				wars = War.getEndedWarPage(conn, page);
				totalWars = Util.getTotalEndedWars(conn);
				req.setAttribute("url", "worldnews/ended");
			}
			req.setAttribute("stats", Stats.getInstance());
			req.setAttribute("page", page);
			req.setAttribute("maxPage", totalWars / 20 + 1);
			req.setAttribute("wars", wars);
			req.setAttribute("description", "World News & Stats");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/worldnews.jsp").forward(req, resp);
	}
}
