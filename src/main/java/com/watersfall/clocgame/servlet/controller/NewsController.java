package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/news/"})
public class NewsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		try(Connection connection = Database.getDataSource().getConnection())
		{
			if(req.getSession().getAttribute("user") != null)
			{
				int user = Integer.parseInt(req.getSession().getAttribute("user").toString());
				PreparedStatement read = connection.prepareStatement("UPDATE cloc_news SET is_read=? WHERE receiver=?");
				read.setBoolean(1, true);
				read.setInt(2, user);
				read.execute();
				connection.commit();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/news.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			int user = UserUtils.getUser(req);
			String delete = req.getParameter("delete");
			Nation nation = new Nation(conn, user, true);
			if(delete.equalsIgnoreCase("all"))
			{
				nation.getNews().deleteAll();
			}
			else
			{
				int id = Integer.parseInt(delete);
				nation.getNews().getNews().get(id).delete();
			}
			return Responses.deleted();
		};
		writer.append(Action.doAction(executor));
	}
}