package com.watersfall.clocgame.tags.costs;

import com.watersfall.clocmath.math.PolicyMath;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Ship extends SimpleTagSupport
{
	private Result main;
	private String type;
	StringWriter sw = new StringWriter();

	public void setMain(Result main)
	{
		this.main = main;
	}

	public void setType(String type)
	{
		this.type = type;
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
				switch(type)
				{
					case "ss":
						out.print(PolicyMath.getSubmarineMgCost(main.getRows()[0]) + " Manufactured Goods and " + PolicyMath.getSubmarineOilCost(main.getRows()[0]) + " Oil");
						break;
					case "dd":
						out.print(PolicyMath.getDestroyerMgCost(main.getRows()[0]) + " Manufactured Goods and " + PolicyMath.getDestroyerOilCost(main.getRows()[0]) + " Coal");
						break;
					case "cl":
						out.print(PolicyMath.getCruiserMgCost(main.getRows()[0]) + " Manufactured Goods and " + PolicyMath.getCruiserOilCost(main.getRows()[0]) + " Coal");
						break;
					case "bb":
						out.print(PolicyMath.getBattleshipMgCost((main.getRows()[0])) + " Manufactured Goods and " + PolicyMath.getBattleshipOilCost(main.getRows()[0]) + " Coal");
						break;
					default:
						out.print("Error");
				}
			}
		}
		catch(SQLException e)
		{
			out.print("Error");
		}
	}
}
