package com.watersfall.cloc.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Government extends SimpleTagSupport
{

    private String value;
    StringWriter sw = new StringWriter();

    public void setValue(String value)
    {
        this.value = value;
    }

    public void doTag() throws JspException, IOException
    {
        if(value != null)
        {
            int num = Integer.parseInt(value);
            JspWriter out = getJspContext().getOut();
            String government = (num > 80) ? ("Dictatorship")
                    : (num > 60) ? ("Military Junta")
                            : (num > 40) ? ("One Party State")
                                    : (num > 20) ? ("Authoritarian Democracy")
                                            : ("Liberal Democracy");
            out.print(government);
        }
    }
}
