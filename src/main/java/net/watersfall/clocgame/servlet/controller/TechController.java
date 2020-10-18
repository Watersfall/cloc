package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.model.technology.Technologies;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(urlPatterns = "/tech/*")
public class TechController extends HttpServlet
{
	public static final String URL = "/{tech}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		PrintWriter writer = resp.getWriter();
		try
		{
			req.setAttribute("technology", Technologies.valueOf(url.get("tech")).getTechnology());
			req.getRequestDispatcher("/WEB-INF/view/api/technology.jsp").forward(req, resp);
		}
		catch(IllegalArgumentException e)
		{
			writer.append("<p>Not a tech</p>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
}
