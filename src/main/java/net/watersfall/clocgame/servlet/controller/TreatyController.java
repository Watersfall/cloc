package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.TreatyActions;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.dao.TreatyDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.listeners.Startup;
import net.watersfall.clocgame.model.Stats;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.treaty.Treaty;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Security;
import net.watersfall.clocgame.util.UserUtils;
import net.watersfall.clocgame.util.Util;

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
	private static final String URL = "/{id}/{manage}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection())
		{
			int id = Integer.parseInt(url.get("id"));
			Treaty treaty = new TreatyDao(conn, false).getTreatyWithMembersById(id);
			req.setAttribute("treaty", treaty);
			req.setAttribute("id", id);
			if(treaty != null)
			{
				req.setAttribute("description", "The Treaty of " + treaty.getName() + " : " + treaty.getDescription());
			}
			if(url.get("manage") != null)
			{
				req.setAttribute("manage", true);
			}
			req.setAttribute("stats", Stats.getInstance());
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/treaty.jsp").forward(req, resp);
		}
		catch(Exception e)
		{
			req.setAttribute("error", Errors.TREATY_DOES_NOT_EXIST);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
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
			String value = Security.sanitize(req.getParameter("value"));
			int user = UserUtils.getUser(req);
			NationDao dao = new NationDao(conn, true);
			Nation member = dao.getNationById(user);
			String response;
			switch(attribute)
			{
				case "name":
					response = TreatyActions.updateName(member, value);
					break;
				case "flag":
					response = TreatyActions.updateFlag(req, member, req.getPart("flag"));
					req.getSession().setAttribute("message", response);
					resp.sendRedirect(Startup.CONTEXT_PATH + "/treaty/" + member.getTreaty().getId());
					break;
				case "description":
					response = TreatyActions.updateDescription(member, value);
					break;
				case "invite":
					response = TreatyActions.invite(member, value);
					break;
				case "kick":
					response = TreatyActions.kick(member, dao.getCosmeticNationById(Integer.parseInt(value)));
					break;
				case "toggle_edit":
					response = TreatyActions.toggleEdit(member, Integer.parseInt(value));
					break;
				case "toggle_invite":
					response = TreatyActions.toggleInvite(member, Integer.parseInt(value));
					break;
				case "toggle_kick":
					response = TreatyActions.toggleKick(member, Integer.parseInt(value));
					break;
				case "toggle_manage":
					response = TreatyActions.toggleManage(member, Integer.parseInt(value));
					break;
				case "resign":
					response = TreatyActions.resign(member);
					break;
				case "accept":
					response = TreatyActions.acceptInvite(member, Integer.parseInt(req.getParameter("value")));
					break;
				case "decline":
					response = TreatyActions.rejectInvite(member, Integer.parseInt(req.getParameter("value")));
					break;
				default:
					response = Responses.genericError();
			}
			dao.saveNation(member);
			if(member.getTreaty() != null)
			{
				new TreatyDao(conn, true).saveTreaty(member.getTreaty());
			}
			return response;
		};
		writer.append(Action.doAction(executor));
	}
}
