package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.Time;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/graphs/*")
public class GraphsController extends HttpServlet
{
	public final static String URL = "/{type}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		PrintWriter writer = resp.getWriter();
		Executor exec = (conn) -> {
			if(url.get("type") != null)
			{
				if(url.get("type").equalsIgnoreCase("globalstats"))
				{
					PreparedStatement statement = conn.prepareStatement("SELECT * FROM global_stats_history WHERE month>? ORDER BY month ASC LIMIT 20");
					statement.setLong(1, Time.month - 20);
					ResultSet results = statement.executeQuery();
					return convert(results);
				}
				else
				{
					return Responses.genericError();
				}
			}
			else
			{
				return Responses.genericError();
			}
		};
		writer.append(Action.doAction(exec));
	}

	private static String convert(ResultSet results) throws SQLException
	{
		String string =
				"{\n" +
				"\t\"months\":[\n";
		while(results.next())
		{
			string += "\t\t{\n";
			ResultSetMetaData data = results.getMetaData();
			for(int i = 1; i <= data.getColumnCount(); i++)
			{
				string += "\t\t\t\"" + data.getColumnName(i) + "\":" + results.getLong(i);
				if(i != data.getColumnCount())
				{
					string += ", ";
				}
				string += "\n";
			}
			string += "\t\t}";
			if(!results.isLast())
			{
				string += ",";
			}
			string += "\n";
		}
		string +=
				"\t]\n" +
				"}";
		return string;
	}
}
