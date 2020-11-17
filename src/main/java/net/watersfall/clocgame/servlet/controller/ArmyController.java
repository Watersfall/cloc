package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.ArmyActions;
import net.watersfall.clocgame.dao.ArmyDao;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.military.army.Army;
import net.watersfall.clocgame.model.military.army.BattalionType;
import net.watersfall.clocgame.model.military.army.Priority;
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
import java.util.HashMap;

@WebServlet(urlPatterns = "/army/*")
public class ArmyController extends HttpServlet
{
	public final static String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
			long id = Long.parseLong(url.get("id"));
			Nation nation = UserUtils.getUserNation(req);
			for(Army army : nation.getArmies())
			{
				if(army.getId() == id)
				{
					req.setAttribute("army", army);
					break;
				}
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/army.jsp").forward(req, resp);
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
		Executor executor = (connection -> {
			String action = req.getParameter("action");
			NationDao dao = new NationDao(connection, true);
			int nationId = UserUtils.getUser(req);
			long armyId = Long.parseLong(url.get("id"));
			Nation nation = dao.getNationById(nationId);
			Army army = null;
			if(!action.equalsIgnoreCase("create_army"))
			{
				 army = nation.getArmy(armyId);
			}
			String response;
			switch(action)
			{
				case "create_army":
					response = ArmyActions.createArmy(nation);
					break;
				case "delete_army":
					response = ArmyActions.deleteArmy(nation, army);
					break;
				case "delete_battalion":
					response = ArmyActions.removeBattalion(nation, new ArmyDao(connection, true), army, Long.parseLong(req.getParameter("type")));
					break;
				case "create_battalion":
					response = ArmyActions.addBattalion(nation, new ArmyDao(connection, true), army, BattalionType.valueOf(req.getParameter("type")));
					break;
				case "set_priority":
					response = ArmyActions.setPriority(nation, army, Priority.valueOf(req.getParameter("type")));
					break;
				case "rename":
					response = ArmyActions.rename(army, req.getParameter("type"));
					break;
				default:
					response = Responses.genericError();
			}
			nation.update(connection);
			return response;
		});
		resp.getWriter().append(Action.doAction(executor));
	}
}
