package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.CityActions;
import com.watersfall.clocgame.dao.NationDao;
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
		Executor exec = (conn) -> {
			NationDao dao = new NationDao(conn, true);
			Nation nation = dao.getNationById(UserUtils.getUser(req));
			int cityId = Integer.parseInt(url.get("id"));
			String response;
			switch(url.get("decision"))
			{
				case "coalmine":
					response =  CityActions.coalMine(nation, cityId);
					break;
				case "ironmine":
					response =  CityActions.ironMine(nation, cityId);
					break;
				case "drill":
					response =  CityActions.drill(nation, cityId);
					break;
				case "industrialize":
					response =  CityActions.industrialize(nation, cityId);
					break;
				case "militarize":
					response =  CityActions.militarize(nation, cityId);
					break;
				case "nitrogen":
					response =  CityActions.nitrogen(nation, cityId);
					break;
				case "university":
					response =  CityActions.university(nation, cityId);
					break;
				case "port":
					response =  CityActions.port(nation, cityId);
					break;
				case "barrack":
					response =  CityActions.barrack(nation, cityId);
					break;
				case "railroad":
					response =  CityActions.railroad(nation, cityId);
					break;
				case "uncoalmine":
					response =  CityActions.closeCoalMine(nation, cityId);
					break;
				case "unironmine":
					response =  CityActions.closeIronMine(nation, cityId);
					break;
				case "undrill":
					response =  CityActions.closeDrill(nation, cityId);
					break;
				case "unindustrialize":
					response =  CityActions.closeIndustrialize(nation, cityId);
					break;
				case "unmilitarize":
					response =  CityActions.closeMilitarize(nation, cityId);
					break;
				case "unnitrogen":
					response =  CityActions.closeNitrogen(nation, cityId);
					break;
				case "ununiversity":
					response =  CityActions.closeUniversity(nation, cityId);
					break;
				case "unport":
					response =  CityActions.closePort(nation, cityId);
					break;
				case "unbarrack":
					response =  CityActions.closeBarrack(nation, cityId);
					break;
				case "unrailroad":
					response =  CityActions.closeRailroad(nation, cityId);
					break;
				default:
					response =  Responses.genericError();
			}
			dao.saveNation(nation);
			return response;
		};
		writer.append(Action.doAction(exec));
	}
}