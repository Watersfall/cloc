package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.TreatyActions;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.model.treaty.TreatyMember;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
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
			Treaty treaty = new Treaty(conn, id, false);
			treaty.getMembers();
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
		PrintWriter writer = resp.getWriter();
		String temp = req.getParameter("attribute");
		if(req.getContentType().contains("multipart/form-data"))
		{
			if(req.getPart("flag") != null)
			{
				temp = "flag";
			}
		}
		String attribute = temp;
		Executor executor = (conn) -> {
			String value = req.getParameter("value");
			int user = UserUtils.getUser(req);
			TreatyMember member = new TreatyMember(conn, user, true);
			Treaty treaty = null;
			switch(attribute)
			{
				case "name":
					return TreatyActions.updateName(member, value);
				case "flag":
					return TreatyActions.updateFlag(req, member, req.getPart("flag"));
				case "description":
					return TreatyActions.updateDescription(member, value);
				case "invite":
					return TreatyActions.invite(member, value);
				case "kick":
					return TreatyActions.kick(member, value);
				case "resign":
					member.leaveTreaty();
					return Responses.resigned();
				case "accept":
					treaty = new Treaty(conn, Integer.parseInt(value), true);
					return member.getInvites().accept(treaty.getId(), member);
				case "decline":
					treaty = new Treaty(conn, Integer.parseInt(value), true);
					return member.getInvites().reject(treaty.getId());
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(executor));
	}
}
