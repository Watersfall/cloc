package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.DecisionActions;
import com.watersfall.clocgame.model.decisions.Decision;
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
			Nation nation = UserUtils.getUserNation(conn, true, req);
			Decision decision = Decision.valueOf(url.get("decision").toUpperCase());
			switch(decision)
			{
				case FREE_MONEY_CAPITALIST:
					return DecisionActions.freeMoneyCapitalist(nation);
				case FREE_MONEY_COMMUNIST:
					return DecisionActions.freeMoneyCommunist(nation);
				case INCREASE_ARREST_QUOTAS:
					return DecisionActions.arrest(nation);
				case PARDON_CRIMINALS:
					return DecisionActions.free(nation);
				case LAND_CLEARANCE:
					return DecisionActions.landClearance(nation);
				case PROPAGANDA:
					return DecisionActions.propaganda(nation);
				case WAR_PROPAGANDA:
					return DecisionActions.warPropaganda(nation);
				case ALIGN_ENTENTE:
					return DecisionActions.alignEntente(nation);
				case ALIGN_NEUTRAL:
					return DecisionActions.alignNeutral(nation);
				case ALIGN_CENTRAL_POWERS:
					return DecisionActions.alignCentralPowers(nation);
				case CONSCRIPT:
					return DecisionActions.conscript(nation);
				case TRAIN:
					return DecisionActions.train(nation);
				case DECONSCRIPT:
					return DecisionActions.deconscript(nation);
				case FORTIFY:
					return DecisionActions.fortify(nation);
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}