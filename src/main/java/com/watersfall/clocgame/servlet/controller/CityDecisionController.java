package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.CityActions;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(urlPatterns = "/decision/city/*")
public class CityDecisionController extends HttpServlet
{
	public static String URL = "/{id}/{decision}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			int user = UserUtils.getUser(req);
			int id = Integer.parseInt(url.get("id"));
			switch(url.get("decision"))
			{
				case "coalmine":
					return CityActions.coalMine(conn, user, id);
				case "ironmine":
					return CityActions.ironMine(conn, user, id);
				case "drill":
					return CityActions.drill(conn, user, id);
				case "industrialize":
					return CityActions.industrialize(conn, user, id);
				case "militarize":
					return CityActions.militarize(conn, user, id);
				case "nitrogen":
					return CityActions.nitrogen(conn, user, id);
				case "university":
					return CityActions.university(conn, user, id);
				case "port":
					return CityActions.port(conn, user, id);
				case "barrack":
					return CityActions.barrack(conn, user, id);
				case "railroad":
					return CityActions.railroad(conn, user, id);
				case "uncoalmine":
					return CityActions.remove(conn, user, id, "cloc_cities.coal_mines");
				case "unironmine":
					return CityActions.remove(conn, user, id, "cloc_cities.iron_mines");
				case "undrill":
					return CityActions.remove(conn, user, id, "cloc_cities.oil_wells");
				case "unindustrialize":
					return CityActions.remove(conn, user, id, "cloc_cities.civilian_industry");
				case "unmilitarize":
					return CityActions.remove(conn, user, id, "military_industry");
				case "unnitrogen":
					return CityActions.remove(conn, user, id, "cloc_cities.nitrogen_industry");
				case "ununiversity":
					return CityActions.remove(conn, user, id, "cloc_cities.universities");
				case "unport":
					return CityActions.remove(conn, user, id, "cloc_cities.ports");
				case "unbarrack":
					return CityActions.remove(conn, user, id, "cloc_cities.barracks");
				case "unrailroad":
					return CityActions.remove(conn, user, id, "cloc_cities.railroads");
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}