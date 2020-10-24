package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.Region;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.util.Util;

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
				HashMap<Region, HashMap<String, Double>> map = new HashMap<>();
				for(Region region : Region.values())
				{
					PreparedStatement armies = connection.prepareStatement("SELECT SUM(army_battalions.size), \n" +
							"\t(SELECT \n" +
							"\t\tSUM(gdp)\n" +
							"\t\tFROM nation_stats\n" +
							"\t\tWHERE nation_stats.region=?\n" +
							"\t) AS gdp\n" +
							"\tFROM nation_stats\n" +
							"\tLEFT JOIN armies ON armies.owner = nation_stats.id \n" +
							"\tLEFT JOIN army_battalions ON armies.id = army_battalions.owner\n" +
							"\tWHERE nation_stats.region=?;");
					armies.setString(1, region.name());
					armies.setString(2, region.name());
					ResultSet results = armies.executeQuery();
					results.first();
					HashMap<String, Double> temp = new HashMap<>();
					temp.put("gdp", results.getDouble(2));
					temp.put("army", results.getDouble(1));
					map.put(region, temp);
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
