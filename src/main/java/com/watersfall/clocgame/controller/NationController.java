package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/nation")
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
				try{req.setAttribute("nation", new Nation(Database.getDataSource().getConnection(), id, false));}catch(Exception e){}
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/nation.jsp").forward(req, resp);
	}
}
