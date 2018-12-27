package com.watersfall.cloc;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Rebel extends SimpleTagSupport
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
            String rebels = (num > 75) ? ("Syrian Civil War")
                : (num > 50) ? ("Open Rebellion")
                : (num > 25) ? ("Guerrillas")
                : (num > 0) ? ("Scattered Terrorists")
                : ("None");
            out.print(rebels);
         } 
    }
}