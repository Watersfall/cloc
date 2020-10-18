package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.NationActions;
import net.watersfall.clocgame.action.WarActions;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.listeners.Startup;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.military.army.ArmyLocation;
import net.watersfall.clocgame.model.military.army.BattlePlan;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Security;
import net.watersfall.clocgame.util.UserUtils;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/nation/*"})
public class NationController extends HttpServlet
{
	public static final String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try
		{

			if(req.getSession().getAttribute("user") != null
					&& Integer.parseInt(url.get("id")) == Integer.parseInt(req.getSession().getAttribute("user").toString()))
			{
				resp.sendRedirect(Startup.CONTEXT_PATH + "/main/");
				return;
			}
			else
			{
				try(Connection conn = Database.getDataSource().getConnection())
				{
					NationDao dao = new NationDao(conn, false);
					req.setAttribute("id", url.get("id"));
					req.setAttribute("nation", dao.getNationById(Integer.parseInt(url.get("id"))));
				}
				catch(SQLException e)
				{
					//Ignore
					e.printStackTrace();
				}
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/nation.jsp").forward(req, resp);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			req.setAttribute("error", Errors.NATION_DOES_NOT_EXIST);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String action = req.getParameter("action");
		PrintWriter writer = resp.getWriter();
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		Executor executor = (conn) -> {
			int idReceiver = Integer.parseInt(url.get("id"));
			int idSender = UserUtils.getUser(req);
			double amount = -1.0;
			if(req.getParameter("amount") != null)
			{
				amount = Double.parseDouble(req.getParameter("amount"));
			}
			NationDao dao = new NationDao(conn, true);
			Nation sender = dao.getNationById(idSender);
			Nation receiver = dao.getNationById(idReceiver);
			String response;
			switch(action)
			{
				case "sendcoal":
					response = NationActions.sendCoal(amount, sender, receiver);
					break;
				case "sendiron":
					response = NationActions.sendIron(amount, sender, receiver);
					break;
				case "sendoil":
					response = NationActions.sendOil(amount, sender, receiver);
					break;
				case "sendsteel":
					response = NationActions.sendSteel(amount, sender, receiver);
					break;
				case "sendnitrogen":
					response = NationActions.sendNitrogen(amount, sender, receiver);
					break;
				case "sendmoney":
					response = NationActions.sendMoney(amount, sender, receiver);
					break;
				case "war":
					response = NationActions.declareWar(sender, receiver, req.getParameter("war_name"));
					break;
				case "peace":
					response = WarActions.sendPeace(sender, receiver);
					break;
				case "land_land":
					BattlePlan attack = new BattlePlan(sender, sender.getArmies(), ArmyLocation.NATION, receiver);
					BattlePlan defense = new BattlePlan(receiver, receiver.getArmies(), ArmyLocation.NATION, receiver);
					response = WarActions.landBattle(attack, defense);
					break;
				/*case "navy":
					response = WarActions.navyBattle(conn, sender, receiver);
					break;
				case "air_air":
					response = WarActions.airBattle(sender, receiver, false);
					break;
				case "land_city":
					response = WarActions.cityBattle(sender, receiver);
					break;
				case "navyCity":
					response = WarActions.navyBombard(conn, sender, receiver);
					break;
				case "air_city":
					response = WarActions.airBombCity(sender, receiver);
					break;
				case "land_fortify":
					response = WarActions.entrench(sender, receiver);
					break;
				case "air_land":
					response = WarActions.airBombTroops(sender, receiver);
					break;*/
				case "message":
					String content = Security.sanitize(req.getParameter("message"));
					response = NationActions.sendMessage(sender, receiver, content);
					break;
				case "equipment":
					Producibles producible = Producibles.valueOf(req.getParameter("equipment"));
					response = NationActions.sendEquipment(sender, receiver, producible, (int)amount);
					break;
				default:
					response = Responses.genericError();
			}
			dao.saveNation(sender);
			dao.saveNation(receiver);
			return response;
		};
		writer.append(Action.doAction(executor));
	}
}
