package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.NationCosmetic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/settings.jsp", "/settings.do"})
public class SettingsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/settings.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		String flag = req.getParameter("flag");
		String leader = req.getParameter("leader");
		String nationTitle = req.getParameter("nationtitle");
		String leaderTitle = req.getParameter("leadertitle");
		String desc = req.getParameter("description");
		if(flag == null || leader == null || nationTitle == null || leaderTitle == null || desc == null)
		{
			writer.append(Responses.nullFields());
		}
		else
		{
			if(req.getSession().getAttribute("user") == null)
			{
				writer.append(Responses.noLogin());
			}
			else
			{
				if(flag.length() > 128)
				{
					writer.append(Responses.tooLong("Flag", 128));
				}
				else if(leader.length() > 128)
				{
					writer.append(Responses.tooLong("Leader", 128));
				}
				else if(nationTitle.length() > 128)
				{
					writer.append(Responses.tooLong("Nation title", 128));
				}
				else if(leaderTitle.length() > 128)
				{
					writer.append(Responses.tooLong("Leader Title", 128));
				}
				else
				{
					Connection connection = null;
					try
					{
						connection = Database.getDataSource().getConnection();
						NationCosmetic update = new NationCosmetic(connection, (Integer) req.getSession().getAttribute("user"), true);
						update.setFlag(flag);
						update.setPortrait(leader);
						update.setNationTitle(nationTitle);
						update.setLeaderTitle(leaderTitle);
						update.setDescription(desc);
						update.update();
						connection.commit();
					}
					catch(SQLException e)
					{
						try
						{
							connection.rollback();
							writer.append(Responses.genericException(e));
						}
						catch(Exception ex)
						{
							//Ignore
						}
					}
					finally
					{
						try
						{
							connection.close();
						}
						catch(Exception ex)
						{
							//Ignore
						}
					}
				}

			}
		}
		resp.sendRedirect("/settings.jsp");
	}
}
