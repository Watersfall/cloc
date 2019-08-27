package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/nation.jsp")
public class NationController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		if(req.getParameter("id") != null)
		{
			int id = Integer.parseInt(req.getParameter("id"));
			if(Sessions.getInstance().getNation(id) != null)
			{
				req.setAttribute("nation", Sessions.getInstance().getNation(id));
			}
			else
			{
				try
				{
					Connection connection = Database.getDataSource().getConnection();
					req.setAttribute("nation", new Nation(connection, id, false));
					connection.close();
				}
				catch(Exception e)
				{
					//Ignore
				}
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/nation.jsp").forward(req, resp);
	}
}
