package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.model.nation.Nation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/main.jsp")
public class MainController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getAttribute("home") != null)
		{
			Nation nation = (Nation) req.getAttribute("home");
			req.setAttribute("growth", nation.getGrowthChange());
			req.setAttribute("production", nation.getCities().getAllTotalProductions());
			req.setAttribute("population", nation.getPopulationGrowth());
			req.setAttribute("food", nation.getFoodProduction());
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
	}
}
