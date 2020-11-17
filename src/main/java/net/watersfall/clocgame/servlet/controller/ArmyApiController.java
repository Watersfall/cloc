package net.watersfall.clocgame.servlet.controller;


import net.watersfall.clocgame.model.military.army.Army;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.util.UserUtils;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/api/army/*")
public class ArmyApiController extends HttpServlet
{
	public final static String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
			long id = Long.parseLong(url.get("id"));
			Nation nation = UserUtils.getUserNation(req);
			for(Army army : nation.getArmies())
			{
				if(army.getId() == id)
				{
					req.setAttribute("army", army);
					break;
				}
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/api/army.jsp").forward(req, resp);
		}
	}
}
