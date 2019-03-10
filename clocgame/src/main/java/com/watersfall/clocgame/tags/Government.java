package com.watersfall.clocgame.tags;

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

    @Override
    public void doTag() throws JspException, IOException
    {
        if(value != null)
        {
            int num = Integer.parseInt(value);
            JspWriter out = getJspContext().getOut();
            String government = (num > 80) ? ("Direct Democracy")
                    : (num > 60) ? ("Constitutional Monarchy")
                            : (num > 40) ? ("One Party State")
                                    : (num > 20) ? ("Military Dictatorship")
                                            : ("Absolute Monarch");
            out.print(government);
        }
    }
}
