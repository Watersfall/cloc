package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/treaties/*")
public class TreatiesController extends HttpServlet
{
	public final static String URL = "/{page}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection())
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
			req.setAttribute("treaties", Treaty.getTreatyPage(conn, page));
			int totalTreaties = Util.getTotalTreaties(conn);
			req.setAttribute("url", "treaties");
			req.setAttribute("page", page);
			req.setAttribute("maxPage", totalTreaties / 20 + 1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/treaties.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doPost(req, resp);
	}
}
