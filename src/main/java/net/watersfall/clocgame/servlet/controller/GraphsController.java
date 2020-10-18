package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.dao.StatsDao;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Time;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
					return Util.convertToJson(new StatsDao(conn, false).getGraphData(Time.month - 20));
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
}
