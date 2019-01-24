package com.watersfall.cloc.tags.event;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class EventText extends SimpleTagSupport
{
    private int value;
    StringWriter sw = new StringWriter();
    private String text;

    public void setValue(String value)
    {
        this.value = Integer.parseInt(value);
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        switch(value)
        {
            case 1:
                text = "Test";
                break;
            default:
                text = "wtf";
                break;
        }
        out.print(text);
    }
}