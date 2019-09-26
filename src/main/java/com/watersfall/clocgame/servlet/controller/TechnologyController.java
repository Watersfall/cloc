package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.ResearchActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.technologies.Category;
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
		if(req.getParameter("type") != null)
		{
			req.setAttribute("type", req.getParameter("type"));
			if(req.getParameter("type").equals("tree"))
			{
				req.setAttribute("categories", Category.values());
				if(req.getParameter("category") != null)
				{
					req.setAttribute("category", req.getParameter("category"));
				}
				else
				{
					req.setAttribute("category", "WEAPONS");
				}
			}
		}
		else
		{
			req.setAttribute("type", "standard");
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
			Technologies technology = Technologies.valueOf(tech);
			writer.append(ResearchActions.doResearch(nation, technology));
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
		catch(NullPointerException | IllegalArgumentException e)
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
