package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.CityActions;
import com.watersfall.clocgame.dao.CityDao;
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
			int id = Integer.parseInt(url.get("id"));
			City city = null;
			if(req.getAttribute("home") != null)
			{
				city = ((Nation)(req.getAttribute("home"))).getCities().get(id);
			}
			if(city == null)
			{
				city = new CityDao(conn, false).getCityById(id);
			}
			req.setAttribute("description", "The city of " + city.getName());
			req.setAttribute("city", city);
			req.setAttribute("id", id);
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
			CityDao dao = new CityDao(conn, true);
			City city = dao.getCityById(cityId);
			if(city.getOwner() != user)
			{
				return Responses.notYourCity();
			}
			else
			{
				String response = CityActions.rename(city, req.getParameter("name"), conn);
				dao.saveCity(city);
				return response;
			}
		};
		writer.append(Action.doAction(executor));
	}
}