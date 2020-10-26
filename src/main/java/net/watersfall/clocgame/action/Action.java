package net.watersfall.clocgame.action;

import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.exception.CityNotFoundException;
import net.watersfall.clocgame.exception.NationNotFoundException;
import net.watersfall.clocgame.exception.NotLoggedInException;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Executor;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Action
{
	public static String doAction(Executor exec)
	{
		JSONObject object = new JSONObject();
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
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericException(e));
		}
		catch(NotLoggedInException e)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noLogin());
		}
		catch(NullPointerException | IllegalArgumentException | IOException | ServletException e)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		catch(NationNotFoundException e)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noNation());
		}
		catch(CityNotFoundException e)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noCity());
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
		return object.toString();
	}
}