package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.servlet.policies.Policies;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/policies.jsp")
public class PoliciesController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("costs", new Policies());
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/policies.jsp").forward(req, resp);
	}
}
