package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.DecisionActions;
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
			switch(url.get("decision"))
			{
				case "freemoneycapitalist":
					return DecisionActions.freeMoneyCapitalist(nation);
				case "freemoneycommunist":
					return DecisionActions.freeMoneyCommunist(nation);
				case "crackdown":
					return DecisionActions.arrest(nation);
				case "free":
					return DecisionActions.free(nation);
				case "landclearance":
					return DecisionActions.landClearance(nation);
				case "propaganda":
					return DecisionActions.propaganda(nation);
				case "warpropaganda":
					return DecisionActions.warPropaganda(nation);
				case "alignentente":
					return DecisionActions.alignEntente(nation);
				case "alignneutral":
					return DecisionActions.alignNeutral(nation);
				case "aligncentral":
					return DecisionActions.alignCentralPowers(nation);
				case "conscript":
					return DecisionActions.conscript(nation);
				case "train":
					return DecisionActions.train(nation);
				case "deconscript":
					return DecisionActions.deconscript(nation);
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}