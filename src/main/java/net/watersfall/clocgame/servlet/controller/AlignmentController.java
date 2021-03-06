package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.AlignmentActions;
import net.watersfall.clocgame.dao.AlignmentDao;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.alignment.Alignment;
import net.watersfall.clocgame.model.alignment.Alignments;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.UserUtils;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/alignment/*")
public class AlignmentController extends HttpServlet
{
	private static final String URL = "/{alignment}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
			Nation nation = UserUtils.getUserNation(req);
			try(Connection connection = Database.getDataSource().getConnection())
			{
				if(url.get("alignment") == null)
				{
					req.setAttribute("alignment", new AlignmentDao(connection, false).getAlignmentById(nation.getStats().getAlignment()));
				}
				else
				{
					req.setAttribute("alignment", new AlignmentDao(connection, false).getAlignmentById(Alignments.valueOf(url.get("alignment"))));
				}
			}
			catch(SQLException ignored) {}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/alignment.jsp").forward(req, resp);
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
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		Executor executor = (conn) -> {
			Alignments alignment = Alignments.valueOf(url.get("alignment"));
			Producibles producible = Producibles.valueOf(req.getParameter("producible"));
			String action = req.getParameter("action");
			NationDao dao = new NationDao(conn, true);
			AlignmentDao alignmentDao = new AlignmentDao(conn, true);
			Alignment actualAlignment = alignmentDao.getAlignmentById(alignment);
			Nation nation = dao.getNationById(UserUtils.getUser(req));
			String response = "";
			if(action.equalsIgnoreCase("buy"))
			{
				response = AlignmentActions.buy(actualAlignment, producible, nation, alignmentDao);
			}
			else
			{
				response = AlignmentActions.sell(actualAlignment, producible, nation, alignmentDao);
			}
			dao.saveNation(nation);
			return response;
		};
		resp.getWriter().append(Action.doAction(executor));
	}
}
