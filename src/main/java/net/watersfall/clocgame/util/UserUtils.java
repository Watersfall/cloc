package net.watersfall.clocgame.util;

import net.watersfall.clocgame.exception.NotLoggedInException;
import net.watersfall.clocgame.model.nation.Nation;

import javax.servlet.http.HttpServletRequest;

public class UserUtils
{
	/**
	 * Method to check if a user is logged in, and return their user id if they are
	 * @param request The request from the HttpServlet#doGet or HttpServlet#doPost
	 * @return The id of the user if they're logged in
	 * @throws NotLoggedInException if the user is not logged in
	 */
	public static int getUser(HttpServletRequest request) throws NotLoggedInException
	{
		Object check = request.getSession().getAttribute("user");
		if(check != null)
		{
			return Integer.parseInt(check.toString());
		}
		else
		{
			throw new NotLoggedInException();
		}
	}

	public static boolean checkLogin(HttpServletRequest request)
	{
		if(request.getSession() != null && request.getSession().getAttribute("user") != null)
		{
			return true;
		}
		return false;
	}

	public static Nation getUserNation(HttpServletRequest request)
	{
		return (Nation)request.getAttribute("home");
	}
}
