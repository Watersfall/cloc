package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.CityActions;
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

@WebServlet(urlPatterns = "/decision/city/*")
public class CityDecisionController extends HttpServlet
{
	public static String URL = "/{id}/{decision}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		if(url.get("decision") == null || url.get("id") == null)
		{
			super.doPost(req, resp);
		}
		Connection conn = null;
		PrintWriter writer = resp.getWriter();
		try
		{
			int id = Integer.parseInt(url.get("id"));
			int user = UserUtils.getUser(req);
			conn = Database.getDataSource().getConnection();
			switch(url.get("decision"))
			{
				case "coalmine":
					writer.append(CityActions.coalMine(conn, user, id));
					break;
				case "ironmine":
					writer.append(CityActions.ironMine(conn, user, id));
					break;
				case "drill":
					writer.append(CityActions.drill(conn, user, id));
					break;
				case "industrialize":
					writer.append(CityActions.industrialize(conn, user, id));
					break;
				case "militarize":
					writer.append(CityActions.militarize(conn, user, id));
					break;
				case "nitrogenplant":
					writer.append(CityActions.nitrogen(conn, user, id));
					break;
				case "university":
					writer.append(CityActions.university(conn, user, id));
					break;
				case "port":
					writer.append(CityActions.port(conn, user, id));
					break;
				case "barrack":
					writer.append(CityActions.barrack(conn, user, id));
					break;
				case "railroad":
					writer.append(CityActions.railroad(conn, user, id));
					break;
				case "uncoalmine":
					writer.append(CityActions.unCoalMine(conn, user, id));
					break;
				case "unironmine":
					writer.append(CityActions.unIronMine(conn, user, id));
					break;
				case "undrill":
					writer.append(CityActions.unDrill(conn, user, id));
					break;
				case "unindustrialize":
					writer.append(CityActions.unIndustrialize(conn, user, id));
					break;
				case "unmilitarize":
					writer.append(CityActions.unMilitarize(conn, user, id));
					break;
				case "unnitrogenplant":
					writer.append(CityActions.unNitrogen(conn, user, id));
					break;
				case "ununiversity":
					writer.append(CityActions.unUniversity(conn, user, id));
					break;
				case "unport":
					writer.append(CityActions.unPort(conn, user, id));
					break;
				case "unbarrack":
					writer.append(CityActions.unBarrack(conn, user, id));
					break;
				case "unrailroad":
					writer.append(CityActions.unRailroad(conn, user, id));
					break;
				default:
					super.doPost(req, resp);
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
