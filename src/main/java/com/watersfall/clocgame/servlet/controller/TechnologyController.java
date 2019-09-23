package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.ResearchActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
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

@WebServlet(urlPatterns = {"/technology.jsp", "/technology.do"})
public class TechnologyController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getSession().getAttribute("user") != null)
		{
			req.setAttribute("techs", Technologies.values());
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/technology.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		Connection conn = null;
		try
		{
			conn = Database.getDataSource().getConnection();
			String tech = req.getParameter("tech");
			int user = UserUtils.getUser(req);
			Nation nation = new Nation(conn, user, true);
			switch(tech)
			{
				case "bolt_action_tech":
					writer.append(ResearchActions.doResearch(nation, Technologies.BOLT_ACTION));
					break;
				case "bomber_tech":
					writer.append(ResearchActions.doResearch(nation, Technologies.BOMBERS));
					break;
				case "chem_tech":
					writer.append(ResearchActions.doResearch(nation, Technologies.CHEMICAL_WEAPONS));
					break;
				case "advanced_chem_tech":
					writer.append(ResearchActions.doResearch(nation, Technologies.ADVANCED_CHEMICAL_WEAPONS));
					break;
				default:
					writer.append(Responses.genericError());
					break;
			}
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
