package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.error.Errors;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/map/*"})
public class MapController extends HttpServlet
{
	public static final String URL = "/region/{region}/{page}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection connection = Database.getDataSource().getConnection())
		{
			if(url.get("region") != null)
			{
				int page;
				try
				{
					page = Integer.parseInt(url.get("page"));
				}
				catch(NumberFormatException | NullPointerException e)
				{
					page = 1;
				}
				Region region = Region.valueOf(url.get("region"));
				ArrayList<Nation> nations = new NationDao(connection, false).getNationPage(region, page);
				int totalNations = Util.getTotalNationsInRegion(connection, region);
				req.setAttribute("region", region.getName());
				req.setAttribute("nations", nations);
				req.setAttribute("url", "map/region/" + region.name());
				req.setAttribute("page", page);
				req.setAttribute("maxPage", totalNations / 20 + 1);
			}
			else
			{
				//TODO clean this up
				HashMap<String, HashMap<String, Double>> map = new HashMap<>();
				for(Region region : Region.values())
				{
					PreparedStatement armies = connection.prepareStatement("SELECT SUM(gdp), SUM(size) FROM cloc_economy, cloc_army, cloc_foreign \n" +
							"WHERE cloc_foreign.region=? AND cloc_economy.id = cloc_foreign.id AND cloc_foreign.id = cloc_army.id");
					armies.setString(1, region.name());
					ResultSet results = armies.executeQuery();
					results.first();
					HashMap<String, Double> temp = new HashMap<>();
					temp.put("gdp", results.getDouble(1));
					temp.put("army", results.getDouble(2));
					map.put(region.getName(), temp);
				}
				req.setAttribute("regions", map);
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/map.jsp").forward(req, resp);
		}
		catch(SQLException | NullPointerException | IllegalArgumentException e)
		{
			req.setAttribute("error", Errors.REGION_DOES_NOT_EXIST);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
	}
}
