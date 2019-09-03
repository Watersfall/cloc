package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.treaty.Treaty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/treaties.jsp")
public class TreatiesController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Connection conn = null;
		try
		{
			conn = Database.getDataSource().getConnection();
			req.setAttribute("treaties", Treaty.getAllTreaties(conn));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{
				//Ignore
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/treaties.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doPost(req, resp);
	}
}
