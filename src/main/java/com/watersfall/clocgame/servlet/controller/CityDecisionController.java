package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.CityActions;
import com.watersfall.clocgame.model.nation.Nation;
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
			Nation nation = UserUtils.getUserNation(conn, true, req);
			int cityId = Integer.parseInt(url.get("id"));
			switch(url.get("decision"))
			{
				case "coalmine":
					return CityActions.coalMine(nation, cityId);
				case "ironmine":
					return CityActions.ironMine(nation, cityId);
				case "drill":
					return CityActions.drill(nation, cityId);
				case "industrialize":
					return CityActions.industrialize(nation, cityId);
				case "militarize":
					return CityActions.militarize(nation, cityId);
				case "nitrogen":
					return CityActions.nitrogen(nation, cityId);
				case "university":
					return CityActions.university(nation, cityId);
				case "port":
					return CityActions.port(nation, cityId);
				case "barrack":
					return CityActions.barrack(nation, cityId);
				case "railroad":
					return CityActions.railroad(nation, cityId);
				case "uncoalmine":
					return CityActions.remove(nation, cityId, "coal_mines");
				case "unironmine":
					return CityActions.remove(nation, cityId, "iron_mines");
				case "undrill":
					return CityActions.remove(nation, cityId, "oil_wells");
				case "unindustrialize":
					return CityActions.remove(nation, cityId, "civilian_industry");
				case "unmilitarize":
					return CityActions.remove(nation, cityId, "military_industry");
				case "unnitrogen":
					return CityActions.remove(nation, cityId, "nitrogen_industry");
				case "ununiversity":
					return CityActions.remove(nation, cityId, "universities");
				case "unport":
					return CityActions.remove(nation, cityId, "ports");
				case "unbarrack":
					return CityActions.remove(nation, cityId, "barracks");
				case "unrailroad":
					return CityActions.remove(nation, cityId, "railroads");
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}