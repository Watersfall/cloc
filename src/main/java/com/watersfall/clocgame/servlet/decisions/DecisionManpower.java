package com.watersfall.clocgame.servlet.decisions;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.NationPolicy;
import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.util.UserUtils;
import com.watersfall.clocgame.util.Util;
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


@WebServlet(urlPatterns = "/decisions/manpower")
public class DecisionManpower extends HttpServlet
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
			int selection = Integer.parseInt(request.getParameter("selection"));
			if(selection < 0 || selection > 4)
			{
				throw new NumberFormatException();
			}
			NationPolicy policy = new NationPolicy(conn, user, true);
			if(Util.turn < policy.getChangeManpower() + 1)
			{
				writer.append(DecisionResponses.noChange());
			}
			else if(policy.getManpower() == selection)
			{
				writer.append(DecisionResponses.same());
			}
			else
			{
				policy.setManpower(selection);
				policy.update();
				conn.commit();
				writer.append(DecisionResponses.updated());
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
