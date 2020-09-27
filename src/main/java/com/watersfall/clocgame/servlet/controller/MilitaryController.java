package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.model.error.Errors;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/military/")
public class MilitaryController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/military.jsp").forward(req, resp);
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
		Executor executor = (connection -> {
			String response;
			ProducibleCategory category = ProducibleCategory.valueOf(req.getParameter("type"));
			int amount = Integer.parseInt(req.getParameter("amount"));
			NationDao dao = new NationDao(connection, true);
			Nation nation = dao.getNationById(UserUtils.getUser(req));
			switch(category)
			{
				case FIGHTER_PLANE:
					nation.getStats().setMaxFighters(amount);
					response = Responses.updated();
					break;
				case BOMBER_PLANE:
					nation.getStats().setMaxBombers(amount);
					response = Responses.updated();
					break;
				case RECON_PLANE:
					nation.getStats().setMaxRecon(amount);
					response = Responses.updated();
					break;
				default:
					response = Responses.genericError();
			}
			dao.saveNation(nation);
			return response;
		});
		resp.getWriter().append(Action.doAction(executor));
	}
}
