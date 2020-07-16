package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.ResearchActions;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.technologies.Category;
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

@WebServlet(urlPatterns = {"/technology/*"})
public class TechnologyController extends HttpServlet
{
	public static final String URL = "/{type}/{category}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		if(req.getSession().getAttribute("user") != null)
		{
			req.setAttribute("techs", Technologies.values());
		}
		if(url.get("type") != null)
		{
			req.setAttribute("type", url.get("type"));
			if(url.get("type").equals("tree"))
			{
				req.setAttribute("categories", Category.values());
				if(url.get("category") != null)
				{
					req.setAttribute("category", url.get("category"));
				}
				else
				{
					req.setAttribute("category", "WEAPONS");
				}
			}
		}
		else
		{
			req.setAttribute("type", "standard");
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/technology.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			String tech = req.getParameter("tech");
			int user = UserUtils.getUser(req);
			NationDao dao = new NationDao(conn, true);
			Nation nation = dao.getNationById(user);
			Technologies technology = Technologies.valueOf(tech);
			String response = ResearchActions.doResearch(nation, technology);
			dao.saveNation(nation);
			return response;
		};
		writer.append(Action.doAction(executor));
	}
}
