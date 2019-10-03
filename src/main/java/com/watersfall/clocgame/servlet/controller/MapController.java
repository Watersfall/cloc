package com.watersfall.clocgame.servlet.controller;

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
		Connection conn = null;
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
					PreparedStatement armies = conn.prepareStatement("SELECT SUM(gdp), SUM(size) FROM cloc_economy, cloc_army, cloc_foreign \n" +
							"WHERE cloc_foreign.region=? AND cloc_economy.id = cloc_foreign.id AND cloc_foreign.id = cloc_army.id");
					armies.setString(1, region.getName());
					ResultSet results = armies.executeQuery();
					results.first();
					HashMap<String, Double> temp = new HashMap<>();
					temp.put("gdp", results.getDouble(1));
					temp.put("army", results.getDouble(2));
					map.put(region.getName(), temp);
				}
				req.setAttribute("regions", map);
			}
		}
		catch(SQLException | NullPointerException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{

				//Ignore
				e.printStackTrace();
			}
		}

		req.getServletContext().getRequestDispatcher("/WEB-INF/view/map.jsp").forward(req, resp);
	}
}
