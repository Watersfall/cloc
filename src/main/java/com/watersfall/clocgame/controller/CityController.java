package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/city.jsp")
public class CityController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
			if(req.getParameter("id") != null)
			{
				int id = Integer.parseInt(req.getParameter("id"));
				Nation nation = (Nation)req.getSession().getAttribute("home");
				if(req.getSession().getAttribute("user") != null && nation != null && nation.getCities().getCities().get(id) != null)
				{
					req.setAttribute("city", nation.getCities().getCities().get(id));
				}
				else
				{
					Connection connection = Database.getDataSource().getConnection();
					req.setAttribute("city", new City(connection, id, false));
					connection.close();
				}
			}
		}
		catch(NumberFormatException | SQLException e)
		{
		}

		req.getServletContext().getRequestDispatcher("/WEB-INF/view/includes/city.jsp").forward(req, resp);
	}
}
