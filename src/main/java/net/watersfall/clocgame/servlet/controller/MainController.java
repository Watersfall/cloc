package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.dao.MessageDao;
import net.watersfall.clocgame.dao.NewsDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/main/")
public class MainController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			if(req.getSession().getAttribute("user") != null)
			{
				try(Connection connection = Database.getDataSource().getConnection())
				{
					req.setAttribute("news", new NewsDao(connection, false).getUnreadNews(UserUtils.getUserNation(req)));
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
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
		Executor executor = (connection -> {
			if(req.getParameter("mark").equalsIgnoreCase("news"))
			{
				NewsDao dao = new NewsDao(connection, true);
				dao.markNewsAsRead(UserUtils.getUser(req));
				return Responses.marked();
			}
			else if(req.getParameter("mark").equalsIgnoreCase("messages"))
			{
				MessageDao dao = new MessageDao(connection, true);
				dao.markMessagesAtRead(UserUtils.getUser(req));
				return Responses.marked();
			}
			return Responses.genericError();
		});
		resp.getWriter().append(Action.doAction(executor));
	}
}
