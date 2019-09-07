package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.constants.Responses;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/decisions.jsp", "/decisions.do"})
public class DecisionsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/decisions.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		String decision = req.getParameter("decision");
		switch(decision)
		{
			case "manpower":
				req.getServletContext().getRequestDispatcher("/decisions/manpower").include(req, resp);
				break;
			case "food":
				req.getServletContext().getRequestDispatcher("/decisions/food").include(req, resp);
				break;
			case "economy":
				req.getServletContext().getRequestDispatcher("/decisions/economy").include(req, resp);
				break;
			default:
				writer.append(Responses.genericError());
				break;
		}
	}
}
