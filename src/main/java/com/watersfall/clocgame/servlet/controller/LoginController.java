package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login/")
public class LoginController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String url = req.getParameter("url");
		if(req.getSession().getAttribute("user") != null)
		{
			req.setAttribute("error", Responses.alreadyLoggedIn());
		}
		else
		{
			try(Connection conn = Database.getDataSource().getConnection())
			{
				if(username == null || password == null)
				{
					req.setAttribute("error", Responses.nullFields());
				}
				else
				{
					PreparedStatement statement = conn.prepareStatement("SELECT password, id FROM cloc_login WHERE username=?");
					statement.setString(1, username);
					ResultSet results = statement.executeQuery();
					if(!results.first())
					{
						req.setAttribute("error", Responses.invalidLogin());
					}
					else
					{
						if(Security.checkPassword(password, results.getString("password")))
						{
							req.getSession().setAttribute("user", results.getInt("id"));
						}
						else
						{
							req.setAttribute("error", Responses.invalidLogin());
						}
					}
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				req.setAttribute("error", Responses.genericException(e));
			}
		}
		if(req.getAttribute("error") != null)
		{
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
		else if(url == null || url.isEmpty())
		{
			resp.sendRedirect("/main/");
		}
		else
		{
			resp.sendRedirect(url);
		}
	}
}
