package com.watersfall.clocgame.tags;

import com.watersfall.clocmath.math.FoodMath;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Food extends SimpleTagSupport
{
	private Map main;
	StringWriter sw = new StringWriter();

	public void setMain(Map main)
	{
		this.main = main;
	}


	@Override
	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		try
		{
			if(main.isEmpty())
			{
				throw new SQLException();
			}
			else
			{
				double food = Double.parseDouble(main.get("food").toString());
				double foodProduction = FoodMath.getFoodProduction(main);
				double foodConsumption = FoodMath.getStandardFoodCost(main);
				out.println("<div class=\"dropdown\">");
				out.println("<span>");
				out.println(food + " Tons");
				out.println("<div class=\"dropdown-content\">");
				out.println("<p class=\"positive\"> +" + foodProduction + " Tons per month from farms</p>");
				out.println("<p class=\"negative\"> -" + foodConsumption + " Tons per month from population</p>");
				out.println("</div>");
				out.println("</div>");
			}
		}
		catch(SQLException e)
		{
			out.print("Error");
		}
	}
}
