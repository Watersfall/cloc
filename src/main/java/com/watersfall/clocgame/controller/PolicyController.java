package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.Army;
import com.watersfall.clocgame.servlet.policies.Policies;
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
							writer.append(Policies.coalMine(conn, user, id));
							break;
						case "ironmine":
							writer.append(Policies.ironMine(conn, user, id));
							break;
						case "drill":
							writer.append(Policies.drill(conn, user, id));
							break;
						case "industrialize":
							writer.append(Policies.industrialize(conn, user, id));
							break;
						case "militarize":
							writer.append(Policies.militarize(conn, user, id));
							break;
						case "nitrogenplant":
							writer.append(Policies.nitrogen(conn, user, id));
							break;
						case "university":
							writer.append(Policies.university(conn, user, id));
							break;
						case "port":
							writer.append(Policies.port(conn, user, id));
							break;
						case "barrack":
							writer.append(Policies.barrack(conn, user, id));
							break;
						case "railroad":
							writer.append(Policies.railroad(conn, user, id));
							break;
						case "uncoalmine":
							writer.append(Policies.unCoalMine(conn, user, id));
							break;
						case "unironmine":
							writer.append(Policies.unIronMine(conn, user, id));
							break;
						case "undrill":
							writer.append(Policies.unDrill(conn, user, id));
							break;
						case "unindustrialize":
							writer.append(Policies.unIndustrialize(conn, user, id));
							break;
						case "unmilitarize":
							writer.append(Policies.unMilitarize(conn, user, id));
							break;
						case "unnitrogenplant":
							writer.append(Policies.unNitrogen(conn, user, id));
							break;
						case "ununiversity":
							writer.append(Policies.unUniversity(conn, user, id));
							break;
						case "unport":
							writer.append(Policies.unPort(conn, user, id));
							break;
						case "unbarrack":
							writer.append(Policies.unBarrack(conn, user, id));
							break;
						case "unrailroad":
							writer.append(Policies.unRailroad(conn, user, id));
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
							writer.append(Policies.conscript(conn, user, id));
							break;
						case "train":
							writer.append(Policies.train(conn, user, id));
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
							writer.append(Policies.freeMoneyCapitalist(conn, user));
							break;
						case "freemoneycommunist":
							writer.append(Policies.freeMoneyCommunist(conn, user));
							break;
						case "crackdown":
							writer.append(Policies.arrest(conn, user));
							break;
						case "free":
							writer.append(Policies.free(conn, user));
							break;
						case "alignentente":
							writer.append(Policies.alignEntente(conn, user));
							break;
						case "alignneutral":
							writer.append(Policies.alignNeutral(conn, user));
							break;
						case "aligncentral":
							writer.append(Policies.alignCentralPowers(conn, user));
							break;
						case "buildartillery":
							writer.append(Policies.artillery(conn, user));
							break;
						case "buildweapons":
							writer.append(Policies.weapons(conn, user));
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
