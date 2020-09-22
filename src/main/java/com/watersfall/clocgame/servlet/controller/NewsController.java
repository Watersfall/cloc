package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.dao.EventDao;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.dao.NewsDao;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.error.Errors;
import com.watersfall.clocgame.model.event.Event;
import com.watersfall.clocgame.model.event.EventActions;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.news.News;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;
import com.watersfall.clocgame.util.Util;

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

@WebServlet(urlPatterns = {"/news/*"})
public class NewsController extends HttpServlet
{
	public final static String URL = "/{page}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			if(req.getSession().getAttribute("user") != null)
			{
				HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
				Nation nation = (Nation)req.getAttribute("home");
				int page;
				try
				{
					page = Integer.parseInt(url.get("page"));
				}
				catch(NumberFormatException | NullPointerException e)
				{
					page = 1;
				}
				try(Connection connection = Database.getDataSource().getConnection())
				{
					Executor executor = (conn) -> {
						new NewsDao(conn, true).markNewsAsRead(nation.getId());
						return null;
					};
					req.setAttribute("news", new NewsDao(connection, false).getNewsPage(page, nation));
					req.setAttribute("url", "news");
					req.setAttribute("page", page);
					req.setAttribute("maxPage", (int)(nation.getNewsCount() / 100.0 + 1));
					Action.doAction(executor);
				}
				catch(SQLException e)
				{
					//Ignore
					e.printStackTrace();
				}
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/news.jsp").forward(req, resp);
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
		Executor executor = (connection -> {
			int user = UserUtils.getUser(req);
			if(req.getParameter("delete") != null)
			{
				NewsDao dao = new NewsDao(connection, true);
				News news = dao.getNewsById(Integer.parseInt(req.getParameter("delete")));
				if(user == news.getReceiver())
				{
					dao.deleteNewsById(news.getId());
					return Responses.deleted();
				}
				else
				{
					return Responses.genericError();
				}
			}
			else if(req.getParameter("event") != null)
			{
				int eventId = Integer.parseInt(req.getParameter("event"));
				EventActions eventAction = EventActions.valueOf(req.getParameter("event_action"));
				Event eventById = new EventDao(connection, true).getEventById(eventId);
				Nation nation = new NationDao(connection, true).getNationById(UserUtils.getUser(req));
				switch(eventAction)
				{
					//TODO fix this
					case STRIKE_GIVE_IN:
						return com.watersfall.clocgame.action.EventActions.Strike.giveIn(nation, eventById);
					case STRIKE_IGNORE:
						return com.watersfall.clocgame.action.EventActions.Strike.ignore(nation, eventById);
					case STRIKE_SEND_ARMY:
						return com.watersfall.clocgame.action.EventActions.Strike.sendArmy(nation, eventById);
					default:
						return Responses.genericError();
				}
			}
			else
			{
				return Responses.genericError();
			}
		});
		writer.append(Action.doAction(executor));
	}
}