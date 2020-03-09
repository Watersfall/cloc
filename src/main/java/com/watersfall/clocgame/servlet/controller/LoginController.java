package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.Security;

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
		PrintWriter writer = resp.getWriter();
		if(req.getSession().getAttribute("user") != null)
		{
			writer.append(Responses.alreadyLoggedIn());
		}
		else
		{
			try(Connection conn = Database.getDataSource().getConnection())
			{
				if(username == null || password == null)
				{
					writer.append(Responses.nullFields());
				}
				else
				{
					PreparedStatement statement = conn.prepareStatement("SELECT password, id FROM cloc_login WHERE username=?");
					statement.setString(1, username);
					ResultSet results = statement.executeQuery();
					if(!results.first())
					{
						writer.append(Responses.invalidLogin());
					}
					else
					{
						if(Security.checkPassword(password, results.getString("password")))
						{
							req.getSession().setAttribute("user", results.getInt("id"));
							Executor exec = (connection) -> {
								PreparedStatement update = connection.prepareStatement("UPDATE cloc_login SET last_login=? WHERE id=?");
								update.setLong(1, System.currentTimeMillis());
								update.setInt(2, Integer.parseInt(req.getSession().getAttribute("user").toString()));
								update.execute();
								connection.commit();
								return null;
							};
							Action.doAction(exec);
						}
						else
						{
							writer.append(Responses.invalidLogin());
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
	}
}
