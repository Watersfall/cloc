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

	public static String genericError()
	{
		return "<p>Don't do that</p>";
	}

	public static String invalidLogin()
	{
		return "<p>Incorrect username or password!</p>";
	}

	public static String noLogin()
	{
		return "<p>You must be logged in to do this!</p>";
	}

	public static String tooLong(String field, int characters)
	{
		return "<p>" + field + " must be less than " + characters + " characters!";
	}

	/*
	** Responses
	 */

	public static String loggedIn()
	{
		return "<p>Logged in!</p>";
	}

	public static String registered()
	{
		return "<p>Registered!</p>";
	}
}
