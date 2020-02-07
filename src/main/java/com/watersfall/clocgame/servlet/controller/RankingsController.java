package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

@WebServlet(urlPatterns = "/rankings/*")
public class RankingsController extends HttpServlet
{
	public final static String URL = "/{page}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection();)
		{
			int page;
			try
			{
				page = Integer.parseInt(url.get("page"));
			}
			catch(NumberFormatException | NullPointerException e)
			{
				page = 1;
			}
			Collection<Nation> nations = Nation.getNations(conn, "cloc_login.id > 0", "cloc_economy.gdp DESC, cloc_login.id ASC", "20 OFFSET " + ((page - 1) * 20));
			int totalNations = Util.getTotalNations(conn);
			req.setAttribute("url", "rankings");
			req.setAttribute("page", page);
			req.setAttribute("maxPage", totalNations / 20 + 1);
			req.setAttribute("nations", nations);
			req.setAttribute("description", "World Rankings");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/rankings.jsp").forward(req, resp);
	}
}
