package com.watersfall.cloc;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Region extends SimpleTagSupport
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
            JspWriter out = getJspContext().getOut();
            String region = "";
            switch (value)
            {
                case "10":
                    region="Mesoamerica";
                case "11":
                    region="Caribbean";
                case "12":
                    region="Gran Colombia";
                case "13":
                    region="Amazonia";
                case "14":
                    region="Southern Cone";
                case "20":
                    region="Atlas";
                case "21":
                    region="West Africa";
                case "22":
                    region="Guinea";
                case "23":
                    region="East Africa";
                case "24":
                    region="Congo";
                case "25":
                    region="Southern Africa";
                case "30":
                    region="Egypt";
                case "31":
                    region="Mesopotamia";
                case "32":
                    region="Arabia";
                case "33":
                    region="Persia";
                case "40":
                    region="Subcontinent";
                case "41":
                    region="Indochina";
                case "42":
                    region="China";
                case "43":
                    region="East Indies";
                case "44":
                    region="Pacific Rim";
                default:
                    region="Test";
            }
            out.print(region);
         } 
    }
}