package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/news.jsp", "/news.do"})
public class NewsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Connection connection = null;
		try
		{
			HttpSession sess = req.getSession(true);
			if(sess != null && sess.getAttribute("user") != null)
			{
				int user = Integer.parseInt(sess.getAttribute("user").toString());
				connection = Database.getDataSource().getConnection();
				req.removeAttribute("home");
				req.setAttribute("home", new Nation(connection, user, true));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/news.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		String delete = req.getParameter("delete");
		if(req.getSession().getAttribute("user") == null)
		{
			writer.append(Responses.noLogin());
			return;
		}
		Connection connection = null;
		try
		{
			int user = UserUtils.getUser(req);
			connection = Database.getDataSource().getConnection();
			if(delete != null && (delete.equalsIgnoreCase("all") || delete.matches("\\d+")))
			{
				Nation nation = new Nation(connection, user, true);
				if(delete.equalsIgnoreCase("all"))
				{
					nation.getNews().deleteAll();
					writer.append(Responses.deleted());
				}
				else
				{
					int id = Integer.parseInt(delete);
					if(nation.getNews().getNews().get(id) != null)
					{
						nation.getNews().getNews().get(id).delete();
						writer.append(Responses.deleted());
					}
					else
					{
						writer.append(Responses.genericError());
					}
				}
			}
			else
			{
				writer.append(Responses.genericError());
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
