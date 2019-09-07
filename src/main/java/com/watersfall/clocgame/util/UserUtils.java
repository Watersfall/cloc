package com.watersfall.clocgame.util;

import com.watersfall.clocgame.exception.NotLoggedInException;

import javax.servlet.http.HttpServletRequest;

public class UserUtils
{
	/**
	 *
	 * @param request The request from the HttpServlet#doGet or HttpServlet#doPost
	 * @return The id of the user if they're logged in, or -1 if they are not
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
}
