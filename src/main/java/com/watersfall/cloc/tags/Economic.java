package com.watersfall.cloc.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Economic extends SimpleTagSupport
{
    private String value;
    StringWriter sw = new StringWriter();

    public void setValue(String value) 
    {
        this.value = value;
    }

    public void doTag() throws JspException, IOException
    {
        if (value != null) 
        {
            int num = Integer.parseInt(value);
            JspWriter out = getJspContext().getOut();
            String economic = (num > 66) ? ("Free Market Economy")
                : (num > 33) ? ("Mixed Market Economy")
                : ("Communist Economy");
            out.print(economic);
         } 
    }
}