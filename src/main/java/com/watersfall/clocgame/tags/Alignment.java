package com.watersfall.clocgame.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Alignment extends SimpleTagSupport
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
		String economic = (value == 0) ? ("Central Powers")
				: (value == 1) ? ("Neutral")
				: (value == 2) ? ("Entente")
				: (value == -1) ? ("Soviet Union")
				: ("What did you do?");
		out.print(economic);
	}
}
