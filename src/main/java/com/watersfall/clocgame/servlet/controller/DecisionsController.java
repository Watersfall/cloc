package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.PolicyActions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/decisions.jsp")
public class DecisionsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("policy", new PolicyActions());
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/decisions.jsp").forward(req, resp);
	}
}
