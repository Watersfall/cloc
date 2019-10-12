package com.watersfall.clocgame.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class Alignment extends SimpleTagSupport
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
		String economic = (value == -1) ? ("Central Powers")
				: (value == 0) ? ("Neutral")
				: (value == 1) ? ("Entente")
				: ("What did you do?");
		out.print(economic);
	}
}
