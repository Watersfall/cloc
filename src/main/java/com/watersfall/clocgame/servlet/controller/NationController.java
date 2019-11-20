package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.NationActions;
import com.watersfall.clocgame.action.WarActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.nation.Nation;
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
		if(req.getSession().getAttribute("user") == null)
		{
			writer.append(Responses.noLogin());
			return;
		}
		if(url.get("id") == null)
		{
			writer.append(Responses.genericError());
			return;
		}
		if(action == null)
		{
			writer.append(Responses.genericError());
			return;
		}

		int idReceiever = Integer.parseInt(url.get("id"));
		int idSender = Integer.parseInt(req.getSession().getAttribute("user").toString());
		Connection connection = null;
		try
		{
			connection = Database.getDataSource().getConnection();
			Nation sender = new Nation(connection, idSender, true);
			Nation receiver = new Nation(connection, idReceiever, true);
			switch(action)
			{
				case "sendcoal":
					writer.append(NationActions.sendCoal(sender, receiver, req));
					break;
				case "sendiron":
					writer.append(NationActions.sendIron(sender, receiver, req));
					break;
				case "sendoil":
					writer.append(NationActions.sendOil(sender, receiver, req));
					break;
				case "sendsteel":
					writer.append(NationActions.sendSteel(sender, receiver, req));
					break;
				case "sendnitrogen":
					writer.append(NationActions.sendNitrogen(sender, receiver, req));
					break;
				case "sendmoney":
					writer.append(NationActions.sendMoney(sender, receiver, req));
					break;
				case "war":
					writer.append(NationActions.declareWar(sender, receiver, req));
					break;
				case "land":
					writer.append(WarActions.landOffensive(connection, sender, receiver));
					break;
				case "navy":
					writer.append(WarActions.navyBattle(connection, sender, receiver));
					break;
				case "air":
					writer.append(WarActions.airBattle(connection, sender, receiver));
					break;
				case "landCity":
					writer.append(WarActions.cityBattle(connection, sender, receiver));
					break;
				case "navyCity":
					writer.append(WarActions.navyBombard(connection, sender, receiver));
					break;
				case "airCity":
					writer.append(WarActions.airBombard(connection, sender, receiver));
					break;
				case "fortify":
					writer.append(WarActions.entrench(connection, sender, receiver));
					break;
				case "bomb":
					writer.append(WarActions.airBombTroops(connection, sender, receiver));
			}
			connection.commit();
		}
		catch(SQLException e)
		{
			writer.append(Responses.genericException(e));
			try
			{
				connection.rollback();
			}
			catch(Exception ex)
			{
				//Ignored
			}
		}
		catch(NationNotFoundException e)
		{
			writer.append(Responses.noNation());
		}
		catch(NumberFormatException e)
		{
			writer.append(Responses.genericError());
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch(Exception e)
			{
				//Ignored
			}
		}


	}
}
