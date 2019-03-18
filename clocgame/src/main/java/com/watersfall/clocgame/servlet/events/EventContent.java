package com.watersfall.clocgame.servlet.events;

public class EventContent
{
	public static final String REGION_CHANGE = "<p>Change your region here:</p><br>\n" +
			"<form action=\"/events/regionchange\" method=\"POST\">\n" +
			"<button type=\"submit\" name=\"region\" value=\"Asia\">Asia</button><br>\n" +
			"<button type=\"submit\" name=\"region\" value=\"North America\">North America</button><br>\n" +
			"<button type=\"submit\" name=\"region\" value=\"South America\">South America</button><br>\n" +
			"<button type=\"submit\" name=\"region\" value=\"Africa\">Africa</button><br>\n" +
			"<button type=\"submit\" name=\"region\" value=\"Middle East\">Middle East</button><br>\n" +
			"<button type=\"submit\" name=\"region\" value=\"Europe\">Europe</button><br>\n" +
			"<button type=\"submit\" name=\"region\" value=\"Oceania\">Oceania</button><br>\n" +
			"</form>";
}
