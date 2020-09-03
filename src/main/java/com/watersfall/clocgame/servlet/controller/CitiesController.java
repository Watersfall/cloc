package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.action.CityActions;
import com.watersfall.clocgame.dao.CityDao;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.model.error.Errors;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.Security;
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

@WebServlet(urlPatterns = "/cities/*")
public class CitiesController extends HttpServlet
{
	public final static String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(UserUtils.checkLogin(req))
		{
			HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
			if(url.get("id") != null)
			{
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
					req.setAttribute("error", Errors.CITY_DOES_NOT_EXIST);
					req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
				}
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/cities.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("error", Errors.NOT_LOGGED_IN);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		PrintWriter writer = resp.getWriter();
		if(url.get("id").equalsIgnoreCase("new"))
		{
			Executor exec = (conn -> {
				Nation nation = new NationDao(conn, true).getNationById(UserUtils.getUser(req));
				if(!nation.canMakeNewCity())
				{
					return Responses.genericError();
				}
				else
				{
					CityDao dao = new CityDao(conn, true);
					return String.valueOf(dao.createNewCity(nation.getId()));
				}
			});
			writer.append(Action.doAction(exec));
		}
		else
		{
			Executor executor = (conn) -> {
				int user = UserUtils.getUser(req);
				int cityId = Integer.parseInt(url.get("id"));
				CityDao dao = new CityDao(conn, true);
				City city = dao.getCityById(cityId);
				if(city.getId() != user)
				{
					return Responses.notYourCity();
				}
				else
				{
					String name = Security.sanitize(req.getParameter("name"));
					String response = CityActions.rename(city, name);
					city.update(conn);
					return response;
				}
			};
			writer.append(Action.doAction(executor));
		}
	}
}
