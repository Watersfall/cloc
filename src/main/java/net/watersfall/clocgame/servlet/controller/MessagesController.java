package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.dao.MessageDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.message.Message;
import net.watersfall.clocgame.model.nation.Nation;
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

@WebServlet(urlPatterns = {"/messages/*"})
public class MessagesController extends HttpServlet
{
	public static final String URL = "/{type}/{page}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
			if(req.getSession().getAttribute("user") != null)
			{
				try(Connection connection = Database.getDataSource().getConnection())
				{
					Nation nation = (Nation)req.getAttribute("home");
					MessageDao dao = new MessageDao(connection, false);
					int page;
					try
					{
						page = Integer.parseInt(url.get("page"));
						if(page <= 0)
						{
							page = 1;
						}
					}
					catch(Exception e)
					{
						page = 1;
					}
					if(url.get("type") != null && url.get("type").equalsIgnoreCase("sent"))
					{
						Util.setPaginationAttributes(req, page, "messages/sent", (Util.getTotalSentMessages(connection, nation.getId()) / 50) + 1);
						req.setAttribute("messages", dao.getSentMessagePage(nation.getId(), page));
						req.setAttribute("sender", true);
					}
					else
					{
						Util.setPaginationAttributes(req, page, "messages/received", (Util.getTotalReceivedMessages(connection, nation.getId()) / 50) + 1);
						req.setAttribute("messages", dao.getReceivedMessagePage(nation.getId(), page));
						req.setAttribute("sender", false);
						Executor executor = (conn) -> {
							new MessageDao(conn, true).markMessagesAtRead(nation.getId());
							return null;
						};
						Action.doAction(executor);
					}
				}
				catch(SQLException e)
				{
					//Ignore
					e.printStackTrace();
				}
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/messages.jsp").forward(req, resp);
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
			MessageDao dao = new MessageDao(connection, true);
			Message message = dao.getMessageById(Integer.parseInt(req.getParameter("delete")));
			if(user == message.getReceiverId())
			{
				dao.deleteMessageById(message.getId());
				return Responses.deleted();
			}
			else
			{
				return Responses.genericError();
			}
		});
		writer.append(Action.doAction(executor));
	}
}
