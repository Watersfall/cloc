package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.model.CityType;
import com.watersfall.clocgame.model.Region;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

@WebServlet(urlPatterns = {"/register/"})
public class RegisterController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
	}

	@SuppressWarnings("JpaQueryApiInspection") @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			String username = req.getParameter("username");
			String nation = req.getParameter("nation");
			String capital = req.getParameter("capital");
			String password = req.getParameter("password");
			String region = req.getParameter("region");
			String govString = req.getParameter("government");
			String econString = req.getParameter("economy");
			if(username.isEmpty() || nation.isEmpty() || capital.isEmpty() || password.isEmpty() || region.isEmpty() || govString.isEmpty() || econString.isEmpty())
			{
				return Responses.nullFields();
			}
			int gov = Integer.parseInt(govString);
			int econ = Integer.parseInt(econString);
			if(gov > 100 || gov < 0)
			{
				gov = 50;
			}
			if(econ > 100 || econ < 0)
			{
				econ = 50;
			}
			if(username.length() > 32)
			{
				return Responses.tooLong("Username", 32);
			}
			else if(nation.length() > 32)
			{
				return Responses.tooLong("Nation name", 32);
			}
			else if(capital.length() > 32)
			{
				return Responses.tooLong("Nation name", 32);
			}
			else if(Region.getFromName(region) == null)
			{
				return Responses.genericError();
			}
			PreparedStatement check = conn.prepareStatement("SELECT id FROM cloc_cosmetic WHERE nation_name=? || username=?");
			check.setString(1, nation);
			check.setString(2, username);
			ResultSet checkResults = check.executeQuery();
			if(checkResults.first())
			{
				return Responses.nameTaken();
			}
			PreparedStatement create = conn.prepareStatement(
					"INSERT INTO cloc_login (username, email, password, register_ip, last_ip, last_login) VALUES (?,?,?,?,?,?);" +
					"INSERT INTO cloc_army () VALUES ();" +
					"INSERT INTO cloc_cosmetic (nation_name, username, description) VALUES (?,?,?);" +
					"INSERT INTO cloc_domestic (government) VALUES (?);" +
					"INSERT INTO cloc_economy (economic) VALUES (?);" +
					"INSERT INTO cloc_foreign (region) VALUES (?);" +
					"INSERT INTO cloc_military () VALUES ();" +
					"INSERT INTO cloc_policy () VALUES ();" +
					"INSERT INTO cloc_tech () VALUES ();",  Statement.RETURN_GENERATED_KEYS
			);
			create.setString(1, username);
			create.setString(2, "");
			create.setString(3, Security.hash(password));
			create.setString(4, req.getRemoteAddr());
			create.setString(5, req.getRemoteAddr());
			create.setLong(6, System.currentTimeMillis());
			create.setString(7, nation);
			create.setString(8, username);
			create.setString(9, "Welcome to CLOC! Please change me in the settings.");
			create.setInt(10, gov);
			create.setInt(11, econ);
			create.setString(12, region);
			create.execute();
			ResultSet key = create.getGeneratedKeys();
			key.first();
			int id = key.getInt(1);
			PreparedStatement cities = conn.prepareStatement("INSERT INTO cloc_cities (owner, capital, coastal, name, type) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			cities.setInt(1, id);
			cities.setBoolean(2, true);
			cities.setBoolean(3, true);
			cities.setString(4, capital);
			cities.setString(5, CityType.FARMING.name());
			cities.execute();
			ResultSet cityKey = cities.getGeneratedKeys();
			cityKey.first();
			PreparedStatement factory = conn.prepareStatement("INSERT INTO factories (owner, city_id, production_id) VALUES (?,?,?)");
			factory.setInt(1, id);
			factory.setInt(2, cityKey.getInt(1));
			factory.setNull(3, Types.INTEGER);
			factory.execute();
			req.getSession().setAttribute("user", id);
			resp.sendRedirect(req.getContextPath() + "/main/");
			return Responses.registered();
		};
		writer.append(Action.doAction(executor));
	}
}
