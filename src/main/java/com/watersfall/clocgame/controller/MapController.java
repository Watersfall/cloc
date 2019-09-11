package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.nation.Nation;

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
import java.util.Collection;
import java.util.HashMap;

@WebServlet(urlPatterns = "/map.jsp")
public class MapController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String param = req.getParameter("region");
		Connection conn;
		try
		{
			conn = Database.getDataSource().getConnection();
			if(param != null)
			{
				Region region = Region.getFromName(param);
				if(region != null)
				{
					String where = "region='" + region.getName() + "'";
					Collection<Nation> nations = Nation.getNations(conn, where, "cloc_login.id ASC");
					req.setAttribute("nations", nations);
				}
			}
			else
			{
				HashMap<String, HashMap<String, Double>> map = new HashMap<>();
				for(Region region : Region.values())
				{
					PreparedStatement home = conn.prepareStatement("SELECT SUM(gdp), SUM(army) FROM cloc_economy, cloc_armies, cloc_foreign \n" +
							"WHERE cloc_armies.region=? AND cloc_foreign.region=? AND cloc_economy.id = cloc_foreign.id AND cloc_foreign.id = cloc_armies.owner;");
					PreparedStatement foreign = conn.prepareStatement("SELECT SUM(army) FROM cloc_armies, cloc_foreign \n" +
							"WHERE cloc_armies.region != cloc_foreign.region AND cloc_armies.region=? AND cloc_foreign.id = cloc_armies.owner");
					foreign.setString(1, region.getName());
					home.setString(1, region.getName());
					home.setString(2, region.getName());
					ResultSet resultsHome = home.executeQuery();
					ResultSet resultsForeign = foreign.executeQuery();
					resultsHome.first();
					resultsForeign.first();
					HashMap<String, Double> temp = new HashMap<>();
					temp.put("gdp", resultsHome.getDouble(1));
					temp.put("home", resultsHome.getDouble(2));
					temp.put("foreign", resultsForeign.getDouble(1));
					map.put(region.getName(), temp);
				}
				req.setAttribute("regions", map);
			}
		}
		catch(SQLException | NullPointerException e)
		{
			e.printStackTrace();
		}

		req.getServletContext().getRequestDispatcher("/WEB-INF/view/map.jsp").forward(req, resp);
	}
}
