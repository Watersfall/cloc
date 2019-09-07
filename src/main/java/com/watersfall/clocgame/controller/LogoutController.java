package com.watersfall.clocgame.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout.jsp")
public class LogoutController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getSession().invalidate();
		req.getSession(true);
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
	}
}
