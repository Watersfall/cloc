package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.PolicyActions;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.model.error.Errors;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.policies.Policy;
import com.watersfall.clocgame.model.policies.PolicyCategory;
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
		if(UserUtils.checkLogin(req))
		{
			req.setAttribute("policies", Policy.getPoliciesByCategory());
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/policy.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("error", Errors.NOT_LOGGED_IN);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			NationDao dao = new NationDao(conn, true);
			PolicyCategory decision = PolicyCategory.valueOf(url.get("policy"));
			Nation nation = dao.getNationById(UserUtils.getUser(req));
			Policy policy = Policy.valueOf(req.getParameter("selection"));
			String response;
			switch(decision)
			{
				case MANPOWER:
					response = PolicyActions.manpower(nation, policy);
					break;
				case FOOD:
					response = PolicyActions.food(nation, policy);
					break;
				case ECONOMY:
					response = PolicyActions.economy(nation, policy);
					break;
				case FORTIFICATION:
					response = PolicyActions.fortification(nation, policy);
					break;
				case FARM_SUBSIDIZATION:
					response = PolicyActions.farmSubsidization(nation, policy);
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
