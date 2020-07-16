package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.dao.CityDao;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.dao.ProductionDao;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
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
		else if(username == null || password == null)
		{
			writer.append(Responses.nullFields());
		}
		else
		{
			//TODO clean this up
			try(Connection conn = Database.getDataSource().getConnection())
			{
				PreparedStatement statement = conn.prepareStatement("SELECT password, id, last_login FROM cloc_login WHERE username=?");
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
						NationDao dao = new NationDao(connection, true);
						Nation nation = dao.getNationById(results.getInt("id"));
						if(results.getLong("last_login") == 0)
						{
							CityDao cityDao = new CityDao(connection, true);
							ProductionDao productionDao = new ProductionDao(connection, true);
							cityDao.buildMilitaryIndustry(nation.getLargestCity());
							productionDao.createDefaultProduction(nation.getId());
						}
						PreparedStatement update = connection.prepareStatement("UPDATE cloc_login SET last_login=? WHERE id=?");
						update.setLong(1, System.currentTimeMillis());
						update.setInt(2, Integer.parseInt(req.getSession().getAttribute("user").toString()));
						update.execute();
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