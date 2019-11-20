package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.TreatyActions;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.model.treaty.TreatyMember;
import com.watersfall.clocgame.util.UserUtils;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/treaty/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 4, fileSizeThreshold = 1024 * 1024 * 4)
public class TreatyController extends HttpServlet
{
	private static final String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection())
		{
			int id = Integer.parseInt(url.get("id"));
			Treaty treaty = new Treaty(conn, id, false, false);
			req.setAttribute("treaty", treaty);
			req.setAttribute("id", id);
			Nation nation = (Nation)req.getAttribute("home");
			if(nation != null && nation.getTreaty() != null && nation.getTreaty().getId() == treaty.getId())
			{
				TreatyMember member = new TreatyMember(conn, nation.getId(), nation.isSafe());
				req.setAttribute("home", member);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/treaty.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Connection conn = null;
		PrintWriter writer = resp.getWriter();
		String attribute = req.getParameter("attribute");
		if(req.getContentType().contains("multipart/form-data"))
		{
			if(req.getPart("flag") != null)
			{
				attribute = "flag";
			}
		}
		String value = req.getParameter("value");
		int user = 0;
		try
		{
			conn = Database.getDataSource().getConnection();
			user = UserUtils.getUser(req);
			TreatyMember member = new TreatyMember(conn, user, true);
			Treaty treaty = null;
			switch(attribute)
			{
				case "name":
					writer.append(TreatyActions.updateName(member, value));
					break;
				case "flag":
					writer.append(TreatyActions.updateFlag(req, member, req.getPart("flag")));
					break;
				case "description":
					writer.append(TreatyActions.updateDescription(member, value));
					break;
				case "invite":
					writer.append(TreatyActions.invite(member, value));
					break;
				case "kick":
					writer.append(TreatyActions.kick(member, value));
					break;
				case "resign":
					member.leaveTreaty();
					writer.append(Responses.resigned());
					break;
				case "accept":
					treaty = new Treaty(conn, Integer.parseInt(value), true);
					writer.append(member.getInvites().accept(treaty.getId(), member));
				case "decline":
					treaty = new Treaty(conn, Integer.parseInt(value), true);
					writer.append(member.getInvites().reject(treaty.getId()));
				default:
					writer.append(Responses.genericError());
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
			try
			{
				Nation nation = new Nation(conn, user, true);
				Treaty treaty = new Treaty(conn, Integer.parseInt(value), true);
				switch(attribute)
				{
					case "accept":
						writer.append(nation.getInvites().accept(treaty.getId(), nation));
						break;
					case "decline":
						writer.append(nation.getInvites().reject(treaty.getId()));
					default:
						writer.append(Responses.genericError());
				}
				conn.commit();
			}
			catch(SQLException ex)
			{
				try
				{
					conn.rollback();
				}
				catch(Exception exe)
				{
					//Ignore
				}
				writer.append(Responses.genericException(e));
				e.printStackTrace();
			}
			catch(NationNotFoundException ex)
			{
				writer.append(Responses.noNation());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				writer.append(Responses.genericException(ex));
			}
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
