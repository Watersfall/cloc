package com.watersfall.clocgame.tags;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Alignment extends SimpleTagSupport
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
            String economic = (num == 1) ? ("Central Powers")
                    : (num == 0) ? ("Neutral")
                            : (num == -1) ? ("Entente")
                                    : (num == 2) ? ("Soviet Union")
                                            : ("What did you do?");
            out.print(economic);
        }
    }
}
