package com.watersfall.clocgame.tags;

import com.watersfall.clocmath.PopulationConstants;
import com.watersfall.clocmath.PopGrowthMath;
import com.watersfall.clocmath.PopulationMath;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Manpower extends SimpleTagSupport
{
	private Result main, population;
	StringWriter sw = new StringWriter();

	public void setMain(Result main)
	{
		this.main = main;
	}

	public void setPopulation(Result population)
	{
		this.population = population;
	}

	@Override
	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		try
		{
			if(main.getRowCount() <= 0 || population.getRowCount() <= 0)
			{
				throw new SQLException();
			}
			else
			{
				SortedMap resultsMain = main.getRows()[0];
				SortedMap resultsPopulation = population.getRows()[0];
				int manpowerTotal = PopulationMath.getTotalManpower(resultsPopulation);
				int manpowerAvailable = PopulationMath.getAvailableManpower(resultsMain, resultsPopulation);
				out.println("<div class=\"dropdown\">");
				out.println("<span>");
				out.println((manpowerAvailable / 1000) + "k Manpower");
				out.println("<div class=\"dropdown-content\">");
				out.println("<p>" + (PopulationConstants.BASE_MANPOWER * 100) + "% of total population available</p>");
				out.println("<p>" + (String.format("%,2d", manpowerTotal)) + " Total available manpower</p>");
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
