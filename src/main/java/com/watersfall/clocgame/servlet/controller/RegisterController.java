package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.CityType;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.util.Md5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = {"/register.jsp", "/register.do"})
public class RegisterController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		String username = req.getParameter("username");
		String nation = req.getParameter("nation");
		String capital = req.getParameter("capital");
		String password = req.getParameter("password");
		String region = req.getParameter("region");
		String govString = req.getParameter("government");
		String econString = req.getParameter("economy");
		if(username == null || nation == null || capital == null || password == null || region == null || govString == null || econString == null)
		{
			writer.append(Responses.nullFields());
		}
		else
		{
			int gov = 50;
			int econ = 50;
			try
			{
				gov = Integer.parseInt(govString);
				econ = Integer.parseInt(econString);
				if(gov > 100 || gov < 0)
				{
					gov = 50;
				}
				if(econ > 100 || econ < 0)
				{
					econ = 50;
				}
			}
			catch(NumberFormatException e)
			{
				writer.append(Responses.genericError());
			}
			if(username.length() > 32)
			{
				writer.append(Responses.tooLong("Username", 32));
			}
			else if(nation.length() > 32)
			{
				writer.append(Responses.tooLong("Nation name", 32));
			}
			else if(capital.length() > 32)
			{
				writer.append(Responses.tooLong("Nation name", 32));
			}
			else if(Region.getFromName(region) == null)
			{
				writer.append(Responses.genericError());
			}
			else
			{
				Connection connection = null;
				try
				{
					password = Md5.md5(password);
					connection = Database.getDataSource().getConnection();
					PreparedStatement check = connection.prepareStatement("SELECT id FROM cloc_cosmetic WHERE nation_name=? || username=?");
					check.setString(1, nation);
					check.setString(2, username);
					ResultSet checkResults = check.executeQuery();
					if(checkResults.first())
					{
						writer.append(Responses.nameTaken());
					}
					else
					{
						PreparedStatement login = connection.prepareStatement("INSERT INTO cloc_login (username, password) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
						PreparedStatement cosmetic = connection.prepareStatement("INSERT INTO cloc_cosmetic (nation_name, username, description) VALUES (?,?,?)");
						PreparedStatement domestic = connection.prepareStatement("INSERT INTO cloc_domestic (government) VALUES(?)");
						PreparedStatement economy = connection.prepareStatement("INSERT INTO cloc_economy (economic) VALUES(?)");
						PreparedStatement foreign = connection.prepareStatement("INSERT INTO cloc_foreign (region) VALUES(?)");
						PreparedStatement military = connection.prepareStatement("INSERT INTO cloc_military () VALUES ()");
						PreparedStatement policy = connection.prepareStatement("INSERT INTO cloc_policy () VALUES ()");
						PreparedStatement tech = connection.prepareStatement("INSERT INTO cloc_tech () VALUES ()");
						PreparedStatement armies = connection.prepareStatement("INSERT INTO cloc_army () VALUES ()");
						PreparedStatement cities = connection.prepareStatement("INSERT INTO cloc_cities (owner, capital, coastal, name, type) VALUES (?,?,?,?,?)");
						login.setString(1, username);
						login.setString(2, password);
						cosmetic.setString(1, nation);
						cosmetic.setString(2, username);
						cosmetic.setString(3, "Welcome to Cloc! Please change me.");
						domestic.setInt(1, gov);
						economy.setInt(1, econ);
						foreign.setString(1, region);
						login.execute();
						ResultSet results = login.getGeneratedKeys();
						int id;
						if(!results.first())
						{
							throw new SQLException();
						}
						else
						{
							id = results.getInt(1);
						}
						cities.setInt(1, id);
						cities.setBoolean(2, true);
						cities.setBoolean(3, false);
						cities.setString(4, capital);
						cities.setString(5, CityType.DRILLING.getName());
						cosmetic.execute();
						domestic.execute();
						economy.execute();
						foreign.execute();
						military.execute();
						policy.execute();
						tech.execute();
						armies.execute();
						cities.execute();
						connection.commit();
						writer.append(Responses.registered());
						req.getSession().setAttribute("user", id);
						resp.sendRedirect(req.getContextPath() + "/main.jsp");
					}
				}
				catch(SQLException e)
				{
					try
					{
						connection.rollback();
					}
					catch(Exception ex)
					{
						//Ignore
					}
				}
				finally
				{
					try
					{
						connection.close();
					}
					catch(Exception e)
					{
						//Ignore
					}
				}
			}
		}
	}
}
