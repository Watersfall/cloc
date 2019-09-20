package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.action.PolicyActions;
import com.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/policy/*")
public class PolicyController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String[] args = req.getPathInfo().split("/");
		if(args.length < 2)
		{
			super.doPost(req, resp);
		}
		String policy = null;
		Connection conn = null;
		int id = 0;
		PrintWriter writer = resp.getWriter();
		try
		{
			conn = Database.getDataSource().getConnection();
			int user = UserUtils.getUser(req);
			if(args[1].equalsIgnoreCase("city"))
			{
				if(args.length < 3)
				{
					super.doPost(req, resp);
				}
				else
				{
					policy = args[2];
					id = Integer.parseInt(req.getParameter("city"));
				}
			}
			else if(args[1].equalsIgnoreCase("army"))
			{
				if(args.length < 3)
				{
					super.doPost(req, resp);
				}
				else
				{
					policy = args[2];
					id = Integer.parseInt(req.getParameter("army"));
				}
			}
			else
			{
				policy = args[1];
			}

			switch(args[1])
			{
				case "city":
					switch(policy)
					{
						case "coalmine":
							writer.append(PolicyActions.coalMine(conn, user, id));
							break;
						case "ironmine":
							writer.append(PolicyActions.ironMine(conn, user, id));
							break;
						case "drill":
							writer.append(PolicyActions.drill(conn, user, id));
							break;
						case "industrialize":
							writer.append(PolicyActions.industrialize(conn, user, id));
							break;
						case "militarize":
							writer.append(PolicyActions.militarize(conn, user, id));
							break;
						case "nitrogenplant":
							writer.append(PolicyActions.nitrogen(conn, user, id));
							break;
						case "university":
							writer.append(PolicyActions.university(conn, user, id));
							break;
						case "port":
							writer.append(PolicyActions.port(conn, user, id));
							break;
						case "barrack":
							writer.append(PolicyActions.barrack(conn, user, id));
							break;
						case "railroad":
							writer.append(PolicyActions.railroad(conn, user, id));
							break;
						case "uncoalmine":
							writer.append(PolicyActions.unCoalMine(conn, user, id));
							break;
						case "unironmine":
							writer.append(PolicyActions.unIronMine(conn, user, id));
							break;
						case "undrill":
							writer.append(PolicyActions.unDrill(conn, user, id));
							break;
						case "unindustrialize":
							writer.append(PolicyActions.unIndustrialize(conn, user, id));
							break;
						case "unmilitarize":
							writer.append(PolicyActions.unMilitarize(conn, user, id));
							break;
						case "unnitrogenplant":
							writer.append(PolicyActions.unNitrogen(conn, user, id));
							break;
						case "ununiversity":
							writer.append(PolicyActions.unUniversity(conn, user, id));
							break;
						case "unport":
							writer.append(PolicyActions.unPort(conn, user, id));
							break;
						case "unbarrack":
							writer.append(PolicyActions.unBarrack(conn, user, id));
							break;
						case "unrailroad":
							writer.append(PolicyActions.unRailroad(conn, user, id));
							break;
						default:
							super.doPost(req, resp);
							break;
					}
					break;
				case "army":
					switch(policy)
					{
						case "conscript":
							writer.append(PolicyActions.conscript(conn, user, id));
							break;
						case "train":
							writer.append(PolicyActions.train(conn, user, id));
							break;
						case "deconscript":
							writer.append(PolicyActions.deconscript(conn, user, id));
							break;
						default:
							super.doPost(req, resp);
							break;
					}
					break;
				default:
					switch(policy)
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
						case "alignentente":
							writer.append(PolicyActions.alignEntente(conn, user));
							break;
						case "alignneutral":
							writer.append(PolicyActions.alignNeutral(conn, user));
							break;
						case "aligncentral":
							writer.append(PolicyActions.alignCentralPowers(conn, user));
							break;
						case "buildartillery":
							writer.append(PolicyActions.artillery(conn, user));
							break;
						case "buildweapons":
							writer.append(PolicyActions.weapons(conn, user));
							break;
					}
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
