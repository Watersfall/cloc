package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.DecisionActions;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.model.decisions.Decision;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.UserUtils;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(urlPatterns = "/decision/*")
public class DecisionController extends HttpServlet
{
	public static final String URL = "/{decision}";

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
			NationDao dao = new NationDao(conn, true);
			Nation nation = dao.getNationById(UserUtils.getUser(req));
			Decision decision = Decision.valueOf(url.get("decision").toUpperCase());
			String response;
			switch(decision)
			{
				case FREE_MONEY_CAPITALIST:
					response = DecisionActions.freeMoneyCapitalist(nation);
					break;
				case FREE_MONEY_COMMUNIST:
					response = DecisionActions.freeMoneyCommunist(nation);
					break;
				case INCREASE_ARREST_QUOTAS:
					response = DecisionActions.arrest(nation);
					break;
				case PARDON_CRIMINALS:
					response = DecisionActions.free(nation);
					break;
				case LAND_CLEARANCE:
					response = DecisionActions.landClearance(nation);
					break;
				case PROPAGANDA:
					response = DecisionActions.propaganda(nation);
					break;
				case WAR_PROPAGANDA:
					response = DecisionActions.warPropaganda(nation);
					break;
				case ALIGN_ENTENTE:
					response = DecisionActions.alignEntente(nation);
					break;
				case ALIGN_NEUTRAL:
					response = DecisionActions.alignNeutral(nation);
					break;
				case ALIGN_CENTRAL_POWERS:
					response = DecisionActions.alignCentralPowers(nation);
					break;
				case CONSCRIPT:
					response = DecisionActions.conscript(nation);
					break;
				case TRAIN:
					response = DecisionActions.train(nation);
					break;
				case DECONSCRIPT:
					response = DecisionActions.deconscript(nation);
					break;
				case FORTIFY:
					response = DecisionActions.fortify(nation);
					break;
				default:
					response = Responses.genericError();
			}
			dao.saveNation(nation);
			return response;
		};
		writer.append(Action.doAction(executor));
	}
}