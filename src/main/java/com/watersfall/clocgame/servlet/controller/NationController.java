package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.NationActions;
import com.watersfall.clocgame.action.WarActions;
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
import java.util.HashMap;

@WebServlet(urlPatterns = {"/nation/*"})
public class NationController extends HttpServlet
{
	public static final String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		if(url.get("id") != null)
		{
			req.setAttribute("id", url.get("id"));
			int id = Integer.parseInt(url.get("id"));
			try(Connection connection = Database.getDataSource().getConnection())
			{
				req.setAttribute("nation", new Nation(connection, id, false));
			}
			catch(Exception e)
			{
				//Ignore
				e.printStackTrace();
			}
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
			Nation sender = new Nation(conn, idSender, true);
			Nation receiver = new Nation(conn, idReceiver, true);
			switch(action)
			{
				case "sendcoal":
					return NationActions.sendResource("coal", amount, sender, receiver);
				case "sendiron":
					return NationActions.sendResource("iron", amount, sender, receiver);
				case "sendoil":
					return NationActions.sendResource("oil", amount, sender, receiver);
				case "sendsteel":
					return NationActions.sendResource("steel", amount, sender, receiver);
				case "sendnitrogen":
					return NationActions.sendResource("nitrogen", amount, sender, receiver);
				case "sendmoney":
					return NationActions.sendResource("budget", amount, sender, receiver);
				case "war":
					return NationActions.declareWar(sender, receiver, req);
				case "land":
					return WarActions.landOffensive(conn, sender, receiver);
				case "navy":
					return WarActions.navyBattle(conn, sender, receiver);
				case "air":
					return WarActions.airBattle(conn, sender, receiver);
				case "landCity":
					return WarActions.cityBattle(conn, sender, receiver);
				case "navyCity":
					return WarActions.navyBombard(conn, sender, receiver);
				case "airCity":
					return WarActions.airBombard(conn, sender, receiver);
				case "fortify":
					return WarActions.entrench(conn, sender, receiver);
				case "bomb":
					return WarActions.airBombTroops(conn, sender, receiver);
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}
