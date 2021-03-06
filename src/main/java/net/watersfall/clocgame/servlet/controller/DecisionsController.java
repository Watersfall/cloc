package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.DecisionActions;
import net.watersfall.clocgame.model.decisions.Decision;
import net.watersfall.clocgame.model.decisions.DecisionCategory;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.util.UserUtils;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/decisions/*")
public class DecisionsController extends HttpServlet
{
	public static final String URL = "/{decisions}";
	public static final DecisionActions decision = new DecisionActions();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
			req.setAttribute("decision", decision);
			DecisionCategory category = DecisionCategory.valueOf(url.get("decisions").toUpperCase());
			req.setAttribute("category", category);
			req.setAttribute("decisions", Decision.values());
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/decisions.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("error", Errors.NOT_LOGGED_IN);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
	}
}
