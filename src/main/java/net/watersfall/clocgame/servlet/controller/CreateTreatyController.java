package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.dao.TreatyDao;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.treaty.Treaty;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Security;
import net.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/createtreaty/"})
public class CreateTreatyController extends HttpServlet
{
	@Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(!UserUtils.checkLogin(req))
		{
			req.setAttribute("error", Errors.NOT_LOGGED_IN);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp");
		}
		else
		{
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/createtreaty.jsp").forward(req, resp);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter writer = response.getWriter();
		Executor executor = (conn) -> {
			int user = UserUtils.getUser(request);
			String name = Security.sanitize(request.getParameter("name"));
			if(name.length() > 32)
			{
				return Responses.tooLong();
			}
			else
			{
				Nation nation = new NationDao(conn, true).getNationById(user);
				if(nation.getTreaty() != null)
				{
					nation.leaveTreaty();
				}
				Treaty treaty = new TreatyDao(conn, true).createTreaty(name);
				nation.joinTreaty(treaty.getId(), true);
				response.setStatus(201);
				return Integer.toString(treaty.getId());
			}
		};
		writer.append(Action.doAction(executor));
	}
}
