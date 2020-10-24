package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Security;

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
		String username = Security.sanitize(req.getParameter("username"));
		String password = req.getParameter("password");
		PrintWriter writer = resp.getWriter();
		if(req.getSession().getAttribute("user") != null)
		{
			writer.append(Responses.alreadyLoggedIn());
		}
		else if(username == null || password == null)
		{
			writer.append(Responses.nullFields());
		}
		else
		{
			//TODO clean this up
			try(Connection conn = Database.getDataSource().getConnection())
			{
				PreparedStatement statement = conn.prepareStatement("SELECT password, id FROM login WHERE username=?");
				statement.setString(1, username);
				ResultSet results = statement.executeQuery();
				if(!results.first())
				{
					writer.append(Responses.invalidLogin());
				}
				else if(Security.checkPassword(password, results.getString("password")))
				{
					Executor executor = (connection) -> {
						req.getSession().setAttribute("user", results.getInt("id"));
						PreparedStatement updateLastLogin = connection.prepareStatement("UPDATE nation_stats SET last_login=? WHERE id=?");
						updateLastLogin.setLong(1, System.currentTimeMillis());
						updateLastLogin.setInt(2, results.getInt("id"));
						updateLastLogin.execute();
						return null;
					};
					Action.doAction(executor);
				}
				else
				{
					writer.append(Responses.invalidLogin());
				}
			}
			catch(SQLException e)
			{
				//Ignore
				e.printStackTrace();
			}
		}
	}
}