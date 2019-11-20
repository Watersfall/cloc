package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.technologies.Category;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/techtree/*")
public class TechTreeController extends HttpServlet
{
	public static final String URL = "/{category}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		if(req.getSession().getAttribute("user") != null)
		{
			req.setAttribute("techs", Technologies.values());
			req.setAttribute("categories", Category.values());
			if(url.get("category") != null)
			{
				req.setAttribute("category", url.get("category"));
			}
			else
			{
				req.setAttribute("category", "WEAPONS");
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/includes/techtree.jsp").forward(req, resp);
	}
}
