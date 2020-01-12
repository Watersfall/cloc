package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.PolicyActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
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

@WebServlet(urlPatterns = "/decision/*")
public class DecisionController extends HttpServlet
{
	public static final String URL = "/{decision}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		if(url.get("decision") == null)
		{
			super.doPost(req, resp);
		}
		Connection conn = null;
		PrintWriter writer = resp.getWriter();
		try
		{
			conn = Database.getDataSource().getConnection();
			int user = UserUtils.getUser(req);
			switch(url.get("decision"))
			{
				case "freemoneycapitalist":
					writer.append(PolicyActions.freeMoneyCapitalist(conn, user));
					break;
				case "freemoneycommunist":
					writer.append(PolicyActions.freeMoneyCommunist(conn, user));
					break;
				case "crackdown":
					writer.append(PolicyActions.arrest(conn, user));
					break;
				case "free":
					writer.append(PolicyActions.free(conn, user));
					break;
				case "landclearance":
					writer.append(PolicyActions.landClearance(conn, user));
					break;
				case "propaganda":
					writer.append(PolicyActions.propaganda(conn, user));
					break;
				case "warpropaganda":
					writer.append(PolicyActions.warPropaganda(conn, user));
					break;
				case "alignentente":
					writer.append(PolicyActions.alignEntente(conn, user));
					break;
				case "alignneutral":
					writer.append(PolicyActions.alignNeutral(conn, user));
					break;
				case "aligncentral":
					writer.append(PolicyActions.alignCentralPowers(conn, user));
					break;
				case "conscript":
					writer.append(PolicyActions.conscript(conn, user));
					break;
				case "train":
					writer.append(PolicyActions.train(conn, user));
					break;
				case "deconscript":
					writer.append(PolicyActions.deconscript(conn, user));
					break;
				default:
					super.doGet(req, resp);
					break;
			}
			conn.commit();
		}
		catch(SQLException e)
		{
			try
			{
				conn.rollback();
			}
			catch(Exception ex)
			{
				//Ignore
			}
			writer.append(Responses.genericException(e));
			e.printStackTrace();
		}
		catch(NotLoggedInException e)
		{
			writer.append(Responses.noLogin());
		}
		catch(NumberFormatException | NullPointerException e)
		{
			writer.append(Responses.genericError());
			e.printStackTrace();
		}
		catch(NationNotFoundException e)
		{
			writer.append(Responses.noNation());
		}
		catch(CityNotFoundException e)
		{
			writer.append(Responses.noCity());
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
	}
}
