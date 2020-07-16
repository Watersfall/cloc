package com.watersfall.clocgame.util;

import com.watersfall.clocgame.exception.NotLoggedInException;

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
}
