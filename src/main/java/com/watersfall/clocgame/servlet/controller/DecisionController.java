package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.PolicyActions;
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
			int user = UserUtils.getUser(req);
			switch(url.get("decision"))
			{
				case "freemoneycapitalist":
					return PolicyActions.freeMoneyCapitalist(conn, user);
				case "freemoneycommunist":
					return PolicyActions.freeMoneyCommunist(conn, user);
				case "crackdown":
					return PolicyActions.arrest(conn, user);
				case "free":
					return PolicyActions.free(conn, user);
				case "landclearance":
					return PolicyActions.landClearance(conn, user);
				case "propaganda":
					return PolicyActions.propaganda(conn, user);
				case "warpropaganda":
					return PolicyActions.warPropaganda(conn, user);
				case "alignentente":
					return PolicyActions.alignEntente(conn, user);
				case "alignneutral":
					return PolicyActions.alignNeutral(conn, user);
				case "aligncentral":
					return PolicyActions.alignCentralPowers(conn, user);
				case "conscript":
					return PolicyActions.conscript(conn, user);
				case "train":
					return PolicyActions.train(conn, user);
				case "deconscript":
					return PolicyActions.deconscript(conn, user);
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}