package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(urlPatterns = {"/register/"})
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
		Executor executor = (conn) -> {
			String username = req.getParameter("username");
			String nation = req.getParameter("nation");
			String capital = req.getParameter("capital");
			String password = req.getParameter("password");
			String regionString = req.getParameter("region");
			String govString = req.getParameter("government");
			String econString = req.getParameter("economy");
			if(username.isEmpty() || nation.isEmpty() || capital.isEmpty() || password.isEmpty() || regionString.isEmpty() || govString.isEmpty() || econString.isEmpty())
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
			Region region = Region.valueOf(regionString);
			//TODO clean this up
			PreparedStatement check = conn.prepareStatement("SELECT id FROM cloc_cosmetic WHERE nation_name=? || username=?");
			check.setString(1, nation);
			check.setString(2, username);
			ResultSet checkResults = check.executeQuery();
			if(checkResults.first())
			{
				return Responses.nameTaken();
			}
			else
			{
				int id = new NationDao(conn, true).createNation(username, password, nation, capital, gov, econ, region, req.getRemoteAddr());
				req.getSession().setAttribute("user", id);
				return Responses.registered();
			}
		};
		writer.append(Action.doAction(executor));
	}
}
