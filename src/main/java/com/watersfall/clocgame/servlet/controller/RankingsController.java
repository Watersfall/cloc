package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet(urlPatterns = "/rankings.jsp")
public class RankingsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Connection conn = null;
		try
		{
			conn = Database.getDataSource().getConnection();
			Collection<Nation> nations = Nation.getNations(conn, "cloc_login.id > 0", "cloc_login.id ASC");
			req.setAttribute("nations", nations);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{
				//Ignore
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/rankings.jsp").forward(req, resp);
	}
}
