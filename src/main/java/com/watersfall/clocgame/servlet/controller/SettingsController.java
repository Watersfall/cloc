package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.SettingsActions;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;

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
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/settings.jsp").forward(req, resp);
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
					break;
				case "portrait":
					response = SettingsActions.updatePortrait(nation, req.getPart("portrait"));
					break;
				case "nationTitle":
					response = SettingsActions.updateNationTitle(nation, req.getParameter("nationTitle"));
					break;
				case "leaderTitle":
					response = SettingsActions.updateLeaderTitle(nation, req.getParameter("leaderTitle"));
					break;
				case "description":
					response = SettingsActions.updateDescription(nation, req.getParameter("description"));
					break;
				case "all":
					response = SettingsActions.updateAll(nation,
							req.getParameter("nationTitle"),
							req.getParameter("leaderTitle"),
							req.getParameter("description"));
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
