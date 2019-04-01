package com.watersfall.clocgame.tags.event;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.SortedMap;

public class EventText extends SimpleTagSupport
{

	private SortedMap value;
	StringWriter sw = new StringWriter();

	public void setValue(SortedMap value)
	{
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		out.println("<td>");
		out.println(value.get("content").toString().replace("{sender}", value.get("sender").toString()).replace("{receiver}", value.get("receiver").toString()));
		out.println("<br>");
		out.println(value.get("time"));
		out.println("<br>");
		out.println("</td>");
	}
}
