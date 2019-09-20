package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationArmies;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/armies.jsp")
public class ArmiesController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
			int id = Integer.parseInt(req.getSession().getAttribute("user").toString());
			Nation nation = (Nation)req.getAttribute("nation");
			if(nation != null)
			{
				req.setAttribute("armies", nation.getArmies());
			}
			else if(id > 0)
			{
				Connection conn = Database.getDataSource().getConnection();
				req.setAttribute("armies", new NationArmies(conn, id, false));
				conn.close();
			}
		}
		catch(NumberFormatException | SQLException | NullPointerException e)
		{
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/armies.jsp").forward(req, resp);
	}
}
