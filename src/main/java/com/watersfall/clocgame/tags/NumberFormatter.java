package com.watersfall.clocgame.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class NumberFormatter extends TagSupport
{
	private Double value;

	public void setValue(Double value)
	{
		this.value = value;
	}

	@Override
	public int doEndTag() throws JspException
	{
		long number = value.longValue();
		String formatted = Long.toString(number);
		if(formatted.length() > 15)
		{
			formatted = ">999T";
		}
		else if(formatted.length() > 12)
		{
			formatted = ((long)(number / 1e12)) + "." + ((long)((number / 1e10) % 100) / 10)+ "T";
		}
		else if(formatted.length() > 9)
		{
			formatted = ((long)(number / 1e9)) + "." + ((long)((number / 1e7) % 100) / 10)+ "B";
		}
		else if(formatted.length() > 6)
		{
			formatted = ((long)(number / 1e6)) + "." + ((long)((number / 1e4) % 100) / 10)+ "M";
		}
		else if(formatted.length() > 3)
		{
			formatted = ((long)(number / 1e3)) + "." + ((long)((number / 1e1) % 100) / 10)+ "K";
		}
		else
		{
			formatted = Double.toString(value);
		}
		try
		{
			pageContext.getOut().append(formatted);
		}
		catch(IOException e)
		{
			throw new JspException(e.getMessage(), e.getCause());
		}
		return EVAL_PAGE;
	}
}
