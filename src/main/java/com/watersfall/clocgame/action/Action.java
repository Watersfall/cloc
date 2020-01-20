package com.watersfall.clocgame.action;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Action
{
	public static String doAction(Executor exec)
	{
		Connection conn = null;
		try
		{
			conn = Database.getDataSource().getConnection();
			String response = exec.exec(conn);
			conn.commit();
			return response;
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
			return Responses.genericException(e);
		}
		catch(NotLoggedInException e)
		{
			return Responses.noLogin();
		}
		catch(NullPointerException | IllegalArgumentException | IOException | ServletException e)
		{
			return Responses.genericError();
		}
		catch(NationNotFoundException e)
		{
			return Responses.noNation();
		}
		catch(CityNotFoundException e)
		{
			return Responses.noCity();
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
}