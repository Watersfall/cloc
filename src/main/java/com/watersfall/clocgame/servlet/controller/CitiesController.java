package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
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
import java.sql.*;
import java.util.HashMap;

@WebServlet(urlPatterns = "/cities/*")
public class CitiesController extends HttpServlet
{
	public final static String URL = "/{id}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection conn = Database.getDataSource().getConnection())
		{
			int id = Integer.parseInt(url.get("id"));
			req.setAttribute("id", id);
			Nation nation = (Nation) req.getAttribute("home");
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
		catch(NullPointerException | NumberFormatException | SQLException | CityNotFoundException e)
		{
			//Ignore
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/cities.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		PrintWriter writer = resp.getWriter();
		if(!url.get("id").equalsIgnoreCase("new"))
		{
			writer.append(Responses.genericError());
		}
		else
		{
			Executor exec = (conn) -> {

				Nation nation = UserUtils.getUserNation(conn, true, req);
				if(nation.canMakeNewCity())
				{
					PreparedStatement statement = conn.prepareStatement("INSERT INTO cloc_cities " +
							"(owner, capital, coastal, railroads, ports, barracks, iron_mines, coal_mines, oil_wells, " +
									"civilian_industry, nitrogen_industry, universities, name, type, devastation, population) " +
							"VALUES (?,?,?,0,0,0,0,0,0,0,0,0,'New City','FARMING',0,5000)",
							Statement.RETURN_GENERATED_KEYS);
					statement.setInt(1, nation.getId());
					statement.setBoolean(2, false);
					statement.setBoolean(3, false);
					statement.execute();
					ResultSet key = statement.getGeneratedKeys();
					key.first();
					return Integer.toString(key.getInt(1));
				}
				else
				{
					return Responses.genericError();
				}
			};
			writer.append(Action.doAction(exec));
		}

	}
}
