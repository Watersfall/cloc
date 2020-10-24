package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.SettingsActions;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.listeners.Startup;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Security;
import net.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/settings/"})
@MultipartConfig(maxFileSize = 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class SettingsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/settings.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("error", Errors.NOT_LOGGED_IN);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		String temp = req.getParameter("action");
		if(req.getContentType().contains("multipart/form-data"))
		{
			if(req.getPart("flag") != null)
			{
				temp = "flag";
			}
			else if(req.getPart("portrait") != null)
			{
				temp = "portrait";
			}
		}
		String action = temp;
		Executor exec = (conn) -> {
			NationDao dao = new NationDao(conn, true);
			Nation nation = dao.getNationById(UserUtils.getUser(req));
			String response;
			switch(action)
			{
				case "flag":
					response = SettingsActions.updateFlag(nation, req.getPart("flag"));
					req.getSession().setAttribute("message", response);
					resp.sendRedirect(Startup.CONTEXT_PATH + "/settings/");
					break;
				case "portrait":
					response = SettingsActions.updatePortrait(nation, req.getPart("portrait"));
					req.getSession().setAttribute("message", response);
					resp.sendRedirect(Startup.CONTEXT_PATH + "/settings/");
					break;
				case "nationTitle":
					response = SettingsActions.updateNationTitle(nation, Security.sanitize(req.getParameter("value")));
					break;
				case "leaderTitle":
					response = SettingsActions.updateLeaderTitle(nation, Security.sanitize(req.getParameter("value")));
					break;
				case "description":
					response = SettingsActions.updateDescription(nation, Security.sanitize(req.getParameter("value")));
					break;
				default:
					response = Responses.genericError();
			}
			dao.saveNation(nation);
			return response;
		};
		writer.append(Action.doAction(exec));
	}
}
