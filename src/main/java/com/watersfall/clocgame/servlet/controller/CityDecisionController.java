package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.CityActions;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.model.decisions.CityDecisions;
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
			CityDecisions decision = CityDecisions.valueOf(url.get("decision"));
			switch(decision)
			{
				case BUILD_COAL_MINE:
					response =  CityActions.coalMine(nation, cityId);
					break;
				case BUILD_IRON_MINE:
					response =  CityActions.ironMine(nation, cityId);
					break;
				case BUILD_OIL_WELL:
					response =  CityActions.drill(nation, cityId);
					break;
				case BUILD_CIVILIAN_INDUSTRY:
					response =  CityActions.industrialize(nation, cityId);
					break;
				case BUILD_MILITARY_INDUSTRY:
					response =  CityActions.militarize(nation, cityId);
					break;
				case BUILD_NITROGEN_INDUSTRY:
					response =  CityActions.nitrogen(nation, cityId);
					break;
				case BUILD_UNIVERSITY:
					response =  CityActions.university(nation, cityId);
					break;
				case BUILD_PORT:
					response =  CityActions.port(nation, cityId);
					break;
				case BUILD_BARRACK:
					response =  CityActions.barrack(nation, cityId);
					break;
				case BUILD_RAILROAD:
					response =  CityActions.railroad(nation, cityId);
					break;
				case REMOVE_COAL_MINE:
					response =  CityActions.closeCoalMine(nation, cityId);
					break;
				case REMOVE_IRON_MINE:
					response =  CityActions.closeIronMine(nation, cityId);
					break;
				case REMOVE_OIL_WELL:
					response =  CityActions.closeDrill(nation, cityId);
					break;
				case REMOVE_CIVILIAN_INDUSTRY:
					response =  CityActions.closeIndustrialize(nation, cityId);
					break;
				case REMOVE_MILITARY_INDUSTRY:
					response =  CityActions.closeMilitarize(nation, cityId);
					break;
				case REMOVE_NITROGEN_INDUSTRY:
					response =  CityActions.closeNitrogen(nation, cityId);
					break;
				case REMOVE_UNIVERSITY:
					response =  CityActions.closeUniversity(nation, cityId);
					break;
				case REMOVE_PORT:
					response =  CityActions.closePort(nation, cityId);
					break;
				case REMOVE_BARRACK:
					response =  CityActions.closeBarrack(nation, cityId);
					break;
				case REMOVE_RAILROAD:
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