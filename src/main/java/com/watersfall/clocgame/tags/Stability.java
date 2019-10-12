package com.watersfall.clocgame.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class Stability extends SimpleTagSupport
{

	private int value;
	StringWriter sw = new StringWriter();

	public void setValue(Integer value)
	{
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		String stability = (value > 90) ? ("Peace and Prosperity")
				: (value > 80) ? ("Unsinkable")
				: (value > 70) ? ("Very Stable")
				: (value > 60) ? ("Stable")
				: (value > 50) ? ("Steady")
				: (value > 40) ? ("Unsteady")
				: (value > 30) ? ("Unsafe Driving Conditions")
				: (value > 20) ? ("Rioting as a National Sport")
				: (value > 10) ? ("Brink of Collapse")
				: ("Civil War");
		out.print(stability);
	}
}
