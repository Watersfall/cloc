package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.NationActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.nation.Nation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/nation.jsp", "/nation.do"})
public class NationController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		if(req.getParameter("id") != null)
		{
			int id = Integer.parseInt(req.getParameter("id"));
			try
			{
				Connection connection = Database.getDataSource().getConnection();
				req.setAttribute("nation", new Nation(connection, id, false));
				connection.close();
			}
			catch(Exception e)
			{
				//Ignore
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/nation.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String action = req.getParameter("action");
		PrintWriter writer = resp.getWriter();

		if(req.getSession().getAttribute("user") == null)
		{
			writer.append(Responses.noLogin());
			return;
		}
		if(req.getParameter("id") == null)
		{
			writer.append(Responses.genericError());
			return;
		}
		if(action == null)
		{
			writer.append(Responses.genericError());
			return;
		}

		int idReceiever = Integer.parseInt(req.getParameter("id"));
		int idSender = Integer.parseInt(req.getSession().getAttribute("user").toString());
		Connection connection = null;
		try
		{
			connection = Database.getDataSource().getConnection();
			Nation sender = new Nation(connection, idSender, true);
			Nation receiver = new Nation(connection, idReceiever, true);
			int amount;
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
					writer.append(NationActions.landOffensive(connection, sender, receiver, req));
					break;
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
