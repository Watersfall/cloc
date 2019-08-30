package com.watersfall.clocgame.servlet.policies.foreign;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.servlet.policies.PolicyResponses;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.treaty.Treaty;
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


@WebServlet(urlPatterns = "/policies/creatytreaty")
public class PolicyCreateTreaty extends HttpServlet
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
			conn = database.getConnection();
			String name;
			if((name = request.getParameter("name")) != null)
			{
				if(name.length() > 32)
				{
					writer.append(PolicyResponses.tooLong());
				}
				else
				{
					Nation nation = new Nation(conn, user, false);
					Treaty treaty= Treaty.createTreaty(conn, name);
					nation.joinTreaty(treaty, true);
					conn.commit();
					request.getServletContext().getRequestDispatcher("/WEB-INF/view/treaty.jsp?id=" + treaty.getId()).forward(request, response);
				}
			}
			else
			{
				request.getServletContext().getRequestDispatcher("/WEB-INF/view/createtreaty.jsp").forward(request, response);
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