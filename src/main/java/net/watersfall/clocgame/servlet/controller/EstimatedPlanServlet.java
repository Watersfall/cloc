package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.model.error.Errors;
import net.watersfall.clocgame.model.military.army.ArmyLocation;
import net.watersfall.clocgame.model.military.army.EstimatedBattlePlan;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

@WebServlet(urlPatterns = "/estimate/*")
public class EstimatedPlanServlet extends HttpServlet
{
	public final static String URL = "/{id}/{location}/{locationId}";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try(Connection connection = Database.getDataSource().getConnection())
		{
			ArmyLocation location = ArmyLocation.valueOf(url.get("location"));
			int id = Integer.parseInt(url.get("id"));
			String cityId = url.get("locationId");
			NationDao dao = new NationDao(connection, false);
			Nation nation = dao.getNationById(id);
			if(location == ArmyLocation.NATION)
			{
				req.setAttribute("estimatedPlan", new EstimatedBattlePlan(nation, location, nation));
			}
			else
			{
				req.setAttribute("estimatedPlan", new EstimatedBattlePlan(nation, location, nation.getCities().get(Long.parseLong(cityId))));
			}
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/api/estimatedplan.jsp").forward(req, resp);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			req.setAttribute("error", Errors.NATION_DOES_NOT_EXIST);
			req.getServletContext().getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
		}
	}
}
