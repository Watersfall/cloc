package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.WarActions;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.military.army.Army;
import net.watersfall.clocgame.model.military.army.ArmyLocation;
import net.watersfall.clocgame.model.military.army.BattlePlan;
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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = "/battle/*")
public class BattlePlannerServlet extends HttpServlet
{
	public final static String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
			try(Connection connection = Database.getDataSource().getConnection())
			{
				NationDao dao = new NationDao(connection, false);
				int id = Integer.parseInt(url.get("id"));
				req.setAttribute("defender", dao.getNationById(id));
			}
			catch(Exception e)
			{
				req.setAttribute("error", Errors.NATION_DOES_NOT_EXIST);
				req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/battleplan.jsp").forward(req, resp);
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
		Executor executor = (connection) -> {
			int defenderId = (Integer.parseInt(req.getParameter("defender_id")));
			String[] armies = req.getParameter("attacker_armies").split(",");
			ArmyLocation battleLocation = ArmyLocation.valueOf(req.getParameter("battle_location"));
			long locationId = Long.parseLong(req.getParameter("location_id"));
			NationDao nationDao = new NationDao(connection, true);
			Nation attacker = nationDao.getNationById(UserUtils.getUser(req));
			Nation defender = nationDao.getNationById(defenderId);
			if(attacker == defender)
			{
				throw new IllegalArgumentException();
			}
			else if(armies.length == 0)
			{
				return Responses.notEnough();
			}
			else
			{
				ArrayList<Army> attackingArmies = new ArrayList<>();
				for(int i = 0; i < armies.length; i++)
				{
					attackingArmies.add(attacker.getArmy(Long.parseLong(armies[i])));
				}
				BattlePlan attackPlan, defensePlan;
				if(battleLocation == ArmyLocation.NATION)
				{
					attackPlan = new BattlePlan(attacker, attackingArmies, battleLocation, defender);
				}
				else
				{
					attackPlan = new BattlePlan(attacker, attackingArmies, battleLocation, defender.getCities().get(locationId));
				}
				defensePlan = defender.generateDefensePlan(attackPlan);
				String response = WarActions.landBattle(attackPlan, defensePlan);
				nationDao.saveNation(attacker);
				nationDao.saveNation(defender);
				return response;
			}
		};
		resp.getWriter().append(Action.doAction(executor));
	}
}
