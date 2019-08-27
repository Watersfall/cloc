package com.watersfall.clocgame.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Approval extends SimpleTagSupport
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
		String approval = (value > 90) ? ("Worshipped as the Color Purple")
				: (value > 80) ? ("Worshipped as Hexy's Disciple")
				: (value > 70) ? ("Loved")
				: (value > 60) ? ("Liked")
				: (value > 50) ? ("Accepted")
				: (value > 40) ? ("Neutral")
				: (value > 30) ? ("Disliked")
				: (value > 20) ? ("Hated")
				: (value > 10) ? ("Enemy of Hexy")
				: ("Enemy of the Color Purple");
		out.print(approval);
	}
}
