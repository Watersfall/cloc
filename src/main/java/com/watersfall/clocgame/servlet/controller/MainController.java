package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.dao.NewsDao;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.military.Bomber;
import com.watersfall.clocgame.model.military.Fighter;
import com.watersfall.clocgame.model.military.ReconPlane;
import com.watersfall.clocgame.util.UserUtils;

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
		req.setAttribute("fighters", Fighter.values());
		req.setAttribute("bombers", Bomber.values());
		req.setAttribute("reconPlanes", ReconPlane.values());
		if(req.getSession().getAttribute("user") != null)
		{
			try(Connection connection = Database.getDataSource().getConnection())
			{
				req.setAttribute("news", new NewsDao(connection, false).getUnreadNews(UserUtils.getUser(req)));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
	}
}
