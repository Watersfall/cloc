package com.watersfall.clocgame.tags;

import com.watersfall.clocmath.constants.PopulationConstants;
import com.watersfall.clocmath.math.PopulationMath;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Manpower extends SimpleTagSupport
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
				int manpowerTotal = PopulationMath.getTotalManpower(main);
				int manpowerAvailable = PopulationMath.getAvailableManpower(main);
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
