package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.CityActions;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/city/*"})
public class CityController extends HttpServlet
{
	public final static String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection())
		{
			if(url.get("id") != null)
			{
				int id = Integer.parseInt(url.get("id"));
				req.setAttribute("id", id);
				Nation nation = (Nation) req.getSession().getAttribute("home");
				if(nation != null && nation.getCities().getCities().get(id) != null)
				{
					req.setAttribute("city", nation.getCities().getCities().get(id));
					req.setAttribute("description", "The city of " + nation.getCities().getCities().get(id).getName());
				}
				else
				{
					City city =  City.getCity(conn, id);
					req.setAttribute("city", city);
					req.setAttribute("description", "The city of " + city.getName());
				}
			}
		}
		catch(NullPointerException | NumberFormatException | SQLException | CityNotFoundException e)
		{
			//Ignore
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/includes/city.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		Executor executor = (conn) -> {
			int user = UserUtils.getUser(req);
			int cityId = Integer.parseInt(url.get("id"));
			City city = City.getCity(conn, cityId);
			if(city.getOwner() != user)
			{
				return Responses.notYourCity();
			}
			else
			{
				return CityActions.rename(city, req.getParameter("name"), conn);
			}
		};
		writer.append(Action.doAction(executor));
	}
}