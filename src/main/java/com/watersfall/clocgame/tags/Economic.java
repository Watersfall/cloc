package com.watersfall.clocgame.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Economic extends SimpleTagSupport
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
		String economic = (value > 66) ? ("Free Market Economy")
				: (value > 33) ? ("Mixed Market Economy")
				: ("Communist Economy");
		out.print(economic);
	}
}