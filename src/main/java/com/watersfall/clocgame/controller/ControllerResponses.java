package com.watersfall.clocgame.controller;

public class ControllerResponses
{
	/*
	** Errors
	 */

	public static String nullFields()
	{
		return "<p>Please fill out all fields!</p>";
	}

	public static String genericException(Exception e)
	{
		return "<p>An error has occurred: " + e.getLocalizedMessage() + "</p>";
	}

	public static String invalidLogin()
	{
		return "<p>Incorrect username or password!</p>";
	}

	/*
	** Responses
	 */

	public static String loggedIn()
	{
		return "<p>Logged in!</p>";
	}
}
