package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.ProductionActions;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.model.error.Errors;
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

@WebServlet(urlPatterns = {"/production/*"})
public class ProductionController extends HttpServlet
{
	public final static String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(request))
		{
			HashMap<String, String> url = Util.urlConvert(URL, request.getPathInfo());
			if(url.get("id") != null && url.get("id").equalsIgnoreCase("all"))
			{
				request.getRequestDispatcher("/WEB-INF/view/includes/allproduction.jsp").forward(request, response);
			}
			else if(url.get("id") != null)
			{
				int id = Integer.parseInt(url.get("id"));
				request.setAttribute("production", ((Nation)request.getAttribute("home")).getProductionById(id));
				request.getRequestDispatcher("/WEB-INF/view/api/production.jsp").forward(request, response);
			}
			else
			{
				request.getRequestDispatcher("/WEB-INF/view/production.jsp").forward(request, response);
			}
		}
		else
		{
			request.setAttribute("error", Errors.NOT_LOGGED_IN);
			request.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		Executor executor = (conn) -> {
			int productionId = Integer.parseInt(url.get("id"));
			NationDao dao = new NationDao(conn, true);
			Nation nation = dao.getNationById(UserUtils.getUser(req));
			String response;
			switch(req.getParameter("action"))
			{
				case "delete":
					response = ProductionActions.delete(nation, productionId);
					break;
				case "new":
					response = ProductionActions.create(nation);
					break;
				case "update":
					int factories = Integer.parseInt(req.getParameter("factories"));
					String newProduction = req.getParameter("production");
					response = ProductionActions.update(nation, productionId, factories, newProduction);
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