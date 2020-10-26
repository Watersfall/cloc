package net.watersfall.clocgame.servlet.controller;

import net.watersfall.clocgame.action.Action;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.model.Region;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import net.watersfall.clocgame.util.Security;
import org.json.JSONObject;

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
			JSONObject object = new JSONObject();
			String username = Security.sanitize(req.getParameter("username"));
			String nation = Security.sanitize(req.getParameter("nation"));
			String capital = Security.sanitize(req.getParameter("capital"));
			String password = req.getParameter("password");
			String regionString = Security.sanitize(req.getParameter("region"));
			String govString = Security.sanitize(req.getParameter("government"));
			String econString = Security.sanitize(req.getParameter("economy"));
			if(username.isEmpty() || nation.isEmpty() || capital.isEmpty() || password.isEmpty() || regionString.isEmpty() || govString.isEmpty() || econString.isEmpty())
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.nullFields());
				return object.toString();
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
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.tooLong("Username", 32));
				return object.toString();
			}
			else if(nation.length() > 32)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.tooLong("Nation Name", 32));
				return object.toString();
			}
			else if(capital.length() > 32)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.tooLong("Capital Name", 32));
				return object.toString();
			}
			Region region = Region.valueOf(regionString);
			//TODO clean this up
			PreparedStatement check = conn.prepareStatement("SELECT id FROM nation_cosmetic WHERE nation_name=? || username=?");
			check.setString(1, nation);
			check.setString(2, username);
			ResultSet checkResults = check.executeQuery();
			if(checkResults.first())
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.nameTaken());
				return object.toString();
			}
			else
			{
				int id = new NationDao(conn, true).createNation(username, password, nation, capital, gov, econ, region, req.getRemoteAddr());
				req.getSession().setAttribute("user", id);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.registered());
				return object.toString();
			}
		};
		writer.append(Action.doAction(executor));
	}
}
