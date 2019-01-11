package com.watersfall.cloc.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Stability extends SimpleTagSupport
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
            String stability = (num > 90) ? ("Peace and Prosperity")
                : (num > 80) ? ("Unsinkable")
                : (num > 70) ? ("Very Stable")
                : (num > 60) ? ("Stable")
                : (num > 50) ? ("Steady")
                : (num > 40) ? ("Unsteady")
                : (num > 30) ? ("Unsafe Driving Conditions")
                : (num > 20) ? ("Rioting as a National Sport")
                : (num > 10) ? ("Brink of Collapse")
                : ("Civil War");
            out.print(stability);
         } 
    }
}