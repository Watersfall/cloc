package com.watersfall.clocgame.servlet.policies;

public class PolicyResponses
{

	/*
	** ERRORS
	 */

	public static String noLogin()
	{
		return "<p>You must be logged in to do this!</p>";
	}

	public static String notYourNation()
	{
		return "<p>Not your nation!</p>";
	}

	public static String notYourCity()
	{
		return "<p>Not your city!</p>";
	}

	public static String genericError()
	{
		return "<p>Don't do that</p>";
	}

	public static String genericException(Exception e)
	{
		return "<p>Error: " + e.getLocalizedMessage() + "!</p>";
	}

	public static String noNation()
	{
		return "<p>Nation does not exist!</p>";
	}

	public static String noCity()
	{
		return "<p>City does not exist!</p>";
	}

	public static String tooLong()
	{
		return "<p>Name must be less than 32 characters!</p>";
	}


	/*
	** MISSING REQUIREMENTS
	 */

	public static String noMoney()
	{
		return "<p>You do not have enough money!</p>";
	}

	public static String noIron()
	{
		return "<p>You do not have enough iron!<p>";
	}

	public static String noCoal()
	{
		return "<p>You do not have enough coal!</p>";
	}

	public static String noSteel()
	{
		return "<p>You do not have enough steel!</p>";
	}

	public static String noNitrogen()
	{
		return "<p>You do not have enough nitrogen!</p>";
	}

	public static String noResearch()
	{
		return "<p>You do not have enough research!</p>";
	}

	public static String notEnough()
	{
		return "<p>You do not have enough to send!</p>";
	}

	public static String notCoastal()
	{
		return "<p>City is not on the coast!</p>";
	}

	/*
	** RESPONSES
	 */

	public static String freeMoneyCapitalist()
	{
		return "<p>You cut the pay and benefits for government employees to fund your newest projects!</p>";
	}

	public static String freeMoneyCommunist()
	{
		return "<p>You raise taxes by 1% to fund your newest projects!</p>";
	}

	public static String coalMine()
	{
		return "<p>You dig a new coal mine!</p>";
	}

	public static String ironMine()
	{
		return "<p>You dig a new iron mine!</p>";
	}

	public static String drill()
	{
		return "<p>You drill a new oil well!</p>";
	}

	public static String industrialize()
	{
		return "<p>Your farmers flock to the city for a new life!</p>";
	}

	public static String militarize()
	{
		return "<p>Your farmers flock to the city for a new life!</p>";
	}

	public static String nitrogenPlant()
	{
		return "<p>Your farmers flock to the city for a new life!</p>";
	}

	public static String university()
	{
		return "<p>You build a new University!</p>";
	}

	public static String railway()
	{
		return "<p>You build a new railway!</p>";
	}

	public static String port()
	{
		return "<p>You build a new port!</p>";
	}

	public static String barrack()
	{
		return "<p>You build the army some shiny new barracks!<p>";
	}

	public static String artillery()
	{
		return "<p>You build some new artillery guns!</p>";
	}

	public static String weapons()
	{
		return "<p>You manufacture some new uniforms and rifles for your conscripts!</p>";
	}
}
