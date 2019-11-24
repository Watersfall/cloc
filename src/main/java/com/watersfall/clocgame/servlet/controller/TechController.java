package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.util.Util;

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
			Technologies tech = Technologies.valueOf(url.get("tech"));
			writer.append(tech.toString());
		}
		catch(IllegalArgumentException e)
		{
			writer.append("<p>Not a tech</p>");
		}
	}
}
