package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.PolicyActions;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/decisions/*")
public class DecisionsController extends HttpServlet
{
	public static final String URL = "/{decisions}";
	public static final PolicyActions policy = new PolicyActions();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		req.setAttribute("policy", policy);
		req.setAttribute("decisions", url.get("decisions"));
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/decisions.jsp").forward(req, resp);
	}
}
