package com.watersfall.clocgame.servlet.policies.military;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.NationEconomy;
import com.watersfall.clocgame.model.nation.NationMilitary;
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


@WebServlet(urlPatterns = "/policies/buildartillery")
public class PolicyBuildArtillery extends HttpServlet
{

	static BasicDataSource database = Database.getDataSource();
	static final int NITROGEN = 5;
	static final int STEEL = 10;
	static final int GAIN = 3;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter writer = response.getWriter();

		Connection conn = null;
		try
		{
			int user = UserUtils.getUser(request);
			conn = database.getConnection();
			NationMilitary military = new NationMilitary(conn, user, true);
			NationEconomy economy = new NationEconomy(conn, user, true);
			if(economy.getSteel() < STEEL)
			{
				writer.append(Responses.noSteel());
			}
			else if(economy.getNitrogen() < NITROGEN)
			{
				writer.append(Responses.noNitrogen());
			}
			else
			{
				military.setStockpileArtillery(military.getStockpileArtillery() + GAIN);
				economy.setSteel(economy.getSteel() - STEEL);
				economy.setNitrogen(economy.getNitrogen() - NITROGEN);
				military.update();
				economy.update();
				conn.commit();
				writer.append(Responses.artillery());
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
			writer.append(Responses.genericException(e));
			e.printStackTrace();
		}
		catch(NotLoggedInException e)
		{
			writer.append(Responses.noLogin());
		}
		catch(NumberFormatException | NullPointerException e)
		{
			writer.append(Responses.genericError());
		}
		catch(NationNotFoundException e)
		{
			writer.append(Responses.noNation());
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
