package com.watersfall.clocgame.servlet.policies.city;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.servlet.policies.PolicyResponses;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.NationEconomy;
import com.watersfall.clocgame.util.UserUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/policies/")
public class PolicyPort extends HttpServlet
{

	static BasicDataSource database = Database.getDataSource();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter writer = response.getWriter();

		Connection conn = null;
		try
		{
			int user = UserUtils.getUser(request);
			int id = Integer.parseInt(request.getParameter("id"));
			conn = database.getConnection();
			City city = new City(conn, id, true);
			NationEconomy economy = new NationEconomy(conn, user, true);
			int cost = city.getPortCost();
			if(user != city.getOwner())
			{
				writer.append(PolicyResponses.notYourCity());
			}
			else
			{
				if(!city.isCoastal())
				{
					writer.append(PolicyResponses.notCoastal());
				}
				else if(cost > economy.getBudget())
				{
					writer.append(PolicyResponses.noMoney());
				}
				else
				{
					city.setPorts(city.getPorts() + 1);
					economy.setBudget(economy.getBudget() - cost);
					city.update();
					economy.update();
					conn.commit();
					writer.append(PolicyResponses.port());
				}
			}
		}
		catch(SQLException e)
		{
			try
			{
				conn.rollback();
			}
			catch(Exception ex)
			{
				//Ignore
			}
			writer.append(PolicyResponses.genericException(e));
			e.printStackTrace();
		}
		catch(NotLoggedInException e)
		{
			writer.append(PolicyResponses.noLogin());
		}
		catch(NumberFormatException | NullPointerException e)
		{
			writer.append(PolicyResponses.genericError());
		}
		catch(NationNotFoundException e)
		{
			writer.append(PolicyResponses.noNation());
		}
		catch(CityNotFoundException e)
		{
			writer.append(PolicyResponses.noCity());
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		super.doGet(request, response);
	}
}
