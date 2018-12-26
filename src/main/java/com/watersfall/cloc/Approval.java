package com.watersfall.cloc;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Approval extends SimpleTagSupport
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
            String approval = (num > 90) ? ("Worshipped as the Color Purple")
                : (num > 80) ? ("Worshipped as Hexy's Disciple")
                : (num > 70) ? ("Loved")
                : (num > 60) ? ("Liked")
                : (num > 50) ? ("Accepted")
                : (num > 40) ? ("Neutral")
                : (num > 30) ? ("Disliked")
                : (num > 20) ? ("Hated")
                : (num > 10) ? ("Enemy of Hexy")
                : ("Enemy of the Color Purple");
            out.print(approval);
         } 
    }
}