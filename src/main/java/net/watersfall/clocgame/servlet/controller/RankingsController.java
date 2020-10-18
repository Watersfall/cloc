package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.util.Util;

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

@WebServlet(urlPatterns = "/rankings/*")
public class RankingsController extends HttpServlet
{
	public final static String URL = "/{page}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection connection = Database.getDataSource().getConnection())
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
			ArrayList<Nation> nations = new NationDao(connection, false).getNationPage(page);
			int totalNations = Util.getTotalNations(connection);
			req.setAttribute("url", "rankings");
			req.setAttribute("page", page);
			req.setAttribute("maxPage", totalNations / 20 + 1);
			req.setAttribute("nations", nations);
			req.setAttribute("description", "World Rankings");
		}
		catch(SQLException e)
		{
			//Ignore
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/rankings.jsp").forward(req, resp);
	}
}
