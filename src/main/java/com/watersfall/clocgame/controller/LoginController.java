package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.servlet.policies.PolicyResponses;
import com.watersfall.clocgame.util.Md5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login.do")
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
		PrintWriter writer = resp.getWriter();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String url = req.getParameter("url");
		try
		{
			if(username == null || password == null)
			{
				writer.append(ControllerResponses.nullFields());
			}
			else
			{
				Connection conn = Database.getDataSource().getConnection();
				password = Md5.md5(password);
				PreparedStatement check = conn.prepareStatement("SELECT id FROM cloc_login WHERE username=? AND password=?");
				check.setString(1, username);
				check.setString(2, password);
				ResultSet results = check.executeQuery();
				if(!results.first())
				{
					writer.append(ControllerResponses.invalidLogin());
				}
				else
				{
					writer.append(ControllerResponses.loggedIn());
					req.getSession().setAttribute("user", results.getInt(1));
					if(url == null)
					{
						url = "main.jsp";
					}
					resp.sendRedirect(url);
				}
			}
		}
		catch(SQLException e)
		{
			writer.append(ControllerResponses.genericException(e));
		}
	}
}