package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.SettingsActions;
import com.watersfall.clocgame.model.nation.NationCosmetic;
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
			int user = UserUtils.getUser(req);
			NationCosmetic cosmetic = NationCosmetic.getNationCosmetic(user, conn);
			switch(action)
			{
				case "flag":
					return SettingsActions.updateFlag(req, cosmetic, req.getPart("flag"), conn);
				case "portrait":
					return SettingsActions.updatePortrait(req, cosmetic, req.getPart("portrait"), conn);
				case "nationTitle":
					return SettingsActions.updateNationTitle(cosmetic, req.getParameter("nationTitle"), conn);
				case "leaderTitle":
					return SettingsActions.updateLeaderTitle(cosmetic, req.getParameter("leaderTitle"), conn);
				case "description":
					return SettingsActions.updateDescription(cosmetic, req.getParameter("description"), conn);
				case "all":
					return SettingsActions.updateAll(cosmetic,
							req.getParameter("nationTitle"),
							req.getParameter("leaderTitle"),
							req.getParameter("description"),
							conn);
				default:
					return Responses.genericError();
			}
		};
		writer.append(Action.doAction(exec));
	}
}
