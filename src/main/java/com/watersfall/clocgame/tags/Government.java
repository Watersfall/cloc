package com.watersfall.clocgame.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Government extends SimpleTagSupport
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
		String government = (value > 80) ? ("Direct Democracy")
				: (value > 60) ? ("Constitutional Monarchy")
				: (value > 40) ? ("One Party State")
				: (value > 20) ? ("Military Dictatorship")
				: ("Absolute Monarch");
		out.print(government);
	}
}