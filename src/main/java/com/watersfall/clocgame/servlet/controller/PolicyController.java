package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.DecisionActions;
import com.watersfall.clocgame.model.Policy;
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

@WebServlet(urlPatterns = {"/policy/*"})
public class PolicyController extends HttpServlet
{
	public static final String URL = "/{policy}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("policies", Policy.getPoliciesByCategory());
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/policy.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			String decision = url.get("policy");
			Nation nation = UserUtils.getUserNation(conn, true, req);
			Policy policy = Policy.valueOf(req.getParameter("selection"));
			switch(decision)
			{
				case "Manpower":
					return DecisionActions.manpower(nation, policy);
				case "Food":
					return DecisionActions.food(nation, policy);
				case "Economy":
					return DecisionActions.economy(nation, policy);
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}
