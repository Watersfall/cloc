package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.NationActions;
import com.watersfall.clocgame.action.WarActions;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.database.Database;
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
				resp.sendRedirect("/main/");
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
		}
		catch(Exception e)
		{
			//Ignore
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/nation.jsp").forward(req, resp);
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
			Nation receiver =dao.getNationById(idReceiver);
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
				case "land":
					response = WarActions.infantryBattle(sender, receiver);
					break;
				/*case "navy":
					response = WarActions.navyBattle(conn, sender, receiver);
					break;*/
				case "air":
					response = WarActions.airBattle(sender, receiver, false);
					break;
				case "landCity":
					response = WarActions.cityBattle(sender, receiver);
					break;
				/*case "navyCity":
					response = WarActions.navyBombard(conn, sender, receiver);
					break;*/
				case "airCity":
					response = WarActions.airBombCity(sender, receiver);
					break;
				case "fortify":
					response = WarActions.entrench(sender, receiver);
					break;
				case "bomb":
					response = WarActions.airBombTroops(sender, receiver);
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
