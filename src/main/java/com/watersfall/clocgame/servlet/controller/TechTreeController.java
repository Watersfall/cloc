package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.technologies.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/techtree.jsp")
public class TechTreeController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getSession().getAttribute("user") != null)
		{
			req.setAttribute("techs", Technologies.values());
			req.setAttribute("categories", Category.values());
			if(req.getParameter("category") != null)
			{
				req.setAttribute("category", req.getParameter("category"));
			}
			else
			{
				req.setAttribute("category", "WEAPONS");
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/includes/techtree.jsp").forward(req, resp);
	}
}