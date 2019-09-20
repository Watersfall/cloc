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

@WebServlet(urlPatterns = "/main.jsp")
public class MainController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getAttribute("home") != null)
		{
			Connection conn = null;
			try
			{
				Nation nation = (Nation) req.getAttribute("home");
				req.setAttribute("growth", nation.getGrowthChange());
				req.setAttribute("production", nation.getCities().getAllTotalProductions());
				req.setAttribute("population", nation.getPopulationGrowth());
				req.setAttribute("food", nation.getFoodProduction());
				if(nation.getDefensive() != 0)
				{
					conn = Database.getDataSource().getConnection();
					Nation defensive = new Nation(conn, nation.getDefensive(), false);
					req.setAttribute("defensive", defensive);
				}
				if(nation.getOffensive() != 0)
				{
					conn = Database.getDataSource().getConnection();
					Nation offensive = new Nation(conn, nation.getOffensive(), false);
					req.setAttribute("offensive", offensive);
				}
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
					//Ignored
				}
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
	}
}
