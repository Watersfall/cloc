package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.dao.EventDao;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.dao.NewsDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.event.Event;
import net.watersfall.clocgame.model.event.EventActions;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.news.News;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
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
						return net.watersfall.clocgame.action.EventActions.Strike.giveIn(nation, eventById);
					case STRIKE_IGNORE:
						return net.watersfall.clocgame.action.EventActions.Strike.ignore(nation, eventById);
					case STRIKE_SEND_ARMY:
						return net.watersfall.clocgame.action.EventActions.Strike.sendArmy(nation, eventById);
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