package com.watersfall.clocgame.tags.costs;

import com.watersfall.clocmath.PolicyMath;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Well extends SimpleTagSupport
{
	private Result main;
	StringWriter sw = new StringWriter();

	public void setMain(Result main)
	{
		this.main = main;
	}

	@Override
	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		try
		{
			if(main.getRowCount() <= 0)
			{
				throw new SQLException();
			}
			else
			{
				int cost = PolicyMath.getWellCost(main.getRows()[0]);
				out.print(cost);
			}
		}
		catch(SQLException e)
		{
			out.print("Error");
		}
	}
}
