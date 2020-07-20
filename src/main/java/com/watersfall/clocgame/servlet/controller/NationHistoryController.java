package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.history.NationHistory;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/history/*")
public class NationHistoryController extends HttpServlet
{
	public static final String URL = "/{id}";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection connection = Database.getDataSource().getConnection())
		{
			int id = Integer.parseInt(url.get("id"));
			PrintWriter writer = resp.getWriter();
			writer.append(new NationHistory(connection, id).getNationHistory());
		}
		catch(SQLException e)
		{

		}
	}
}
