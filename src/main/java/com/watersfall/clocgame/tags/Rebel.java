package com.watersfall.clocgame.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class Rebel extends SimpleTagSupport
{

	private int value;
	StringWriter sw = new StringWriter();

	public void setValue(Integer value)
	{
		this.value = value;
	}

	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		String rebels = (value > 75) ? ("Syrian Civil War")
				: (value > 50) ? ("Open Rebellion")
				: (value > 25) ? ("Guerrillas")
				: (value > 0) ? ("Scattered Terrorists")
				: ("None");
		out.print(rebels);
	}
}
