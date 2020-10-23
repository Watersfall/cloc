package net.watersfall.clocgame.action;

import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.exception.CityNotFoundException;
import net.watersfall.clocgame.exception.NationNotFoundException;
import net.watersfall.clocgame.exception.NotLoggedInException;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;

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
			e.printStackTrace();
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
			e.printStackTrace();
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