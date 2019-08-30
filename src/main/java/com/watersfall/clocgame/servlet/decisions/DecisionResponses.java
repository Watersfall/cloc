package com.watersfall.clocgame.servlet.decisions;

public class DecisionResponses
{
	public static String noChange()
	{
		return "<p>You can not change this yet!</p>";
	}

	public static String same()
	{
		return "<p>You already have this policy set!</p>";
	}

	public static String updated()
	{
		return "<p>Policy updated!</p>";
	}

	public static String noWar()
	{
		return "<p>You must be at war to select this!</p>";
	}

	public static String noPeace()
	{
		return "<p>You can not select this when at war!</p>";
	}
}
