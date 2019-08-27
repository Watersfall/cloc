package com.watersfall.clocgame.servlet.policies.economy;

import com.watersfall.clocgame.database.Database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.watersfall.clocgame.servlet.policies.PolicyResponses;
import com.watersfall.clocgame.model.nation.NationEconomy;
import com.watersfall.clocgame.util.UserUtils;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Chris
 */
@WebServlet(urlPatterns = "/policies/freemoneycapitalist")
public class PolicyFreeMoneyCapitalist extends HttpServlet
{

	static BasicDataSource database = Database.getDataSource();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		String sess = request.getSession().getId();
		Connection connection = null;
		try
		{
			connection = database.getConnection();
			int id;
			if((id = UserUtils.getUser(request)) != -1)
			{
				NationEconomy update = new NationEconomy(connection, id, true);
				update.setBudget(update.getBudget() + 1000);
				update.setEconomic(update.getEconomic() + 5);
				update.update();
				connection.commit();
				out.write(PolicyResponses.freeMoneyCapitalist());
			}
			else
			{
				out.write(PolicyResponses.noLogin());
			}
		}
		catch(SQLException e)
		{
			try
			{
				connection.rollback();
			}
			catch(Exception ex)
			{
				e.printStackTrace();
			}
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
