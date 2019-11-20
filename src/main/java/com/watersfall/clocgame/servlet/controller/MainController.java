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

@WebServlet(urlPatterns = "/main/")
public class MainController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getAttribute("home") != null)
		{
			try(Connection conn = Database.getDataSource().getConnection())
			{
				Nation nation = (Nation) req.getAttribute("home");
				if(nation.getDefensive() != 0)
				{
					Nation defensive = new Nation(conn, nation.getDefensive(), false);
					req.setAttribute("defensive", defensive);
				}
				if(nation.getOffensive() != 0)
				{
					Nation offensive = new Nation(conn, nation.getOffensive(), false);
					req.setAttribute("offensive", offensive);
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
	}
}
