package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.action.DeclarationActions;
import net.watersfall.clocgame.dao.DeclarationDao;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Security;
import net.watersfall.clocgame.util.UserUtils;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/declarations/*"})
public class DeclarationsController extends HttpServlet
{
	public final static String URL = "/{page}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection())
		{
			int page;
			try
			{
				page = Integer.parseInt(url.get("page"));
			}
			catch(NumberFormatException | NullPointerException e)
			{
				page = 1;
			}
			DeclarationDao dao = new DeclarationDao(conn, false);
			int totalDeclarations = Util.getTotalDeclarations(conn);
			req.setAttribute("url", "declarations");
			req.setAttribute("page", page);
			req.setAttribute("maxPage", totalDeclarations / 20 + 1);
			req.setAttribute("declarations", dao.getDeclarationPage(page));
		}
		catch(SQLException e)
		{
			//Ignore
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/declarations.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			String message = Security.sanitize(req.getParameter("message"));
			if(message == null)
			{
				return Responses.genericError();
			}
			else
			{
				NationDao dao = new NationDao(conn, true);
				Nation nation = dao.getNationById(UserUtils.getUser(req));
				String response = DeclarationActions.postDeclaration(nation, message);
				dao.saveNation(nation);
				return response;
			}
		};
		writer.append(Action.doAction(executor));
	}
}