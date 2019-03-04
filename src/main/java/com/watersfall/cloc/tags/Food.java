package com.watersfall.cloc.tags;

import com.watersfall.cloc.math.FoodCalc;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Food extends SimpleTagSupport
{
    
    private Result value;
    StringWriter sw = new StringWriter();

    public void setValue(Result value)
    {
        this.value = value;
    }
    
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        try
        {
            if(value.getRowCount() <= 0)
            {
                throw new SQLException();
            }
            else
            {
                SortedMap results = value.getRows()[0];
                int food = Integer.parseInt(results.get("food").toString());
                int foodProduction = FoodCalc.getFoodProduction(results);
                int foodConsumption = FoodCalc.getStandardFoodCost(results);
                out.println("<div class=\"dropdown\">");
                out.println("<span>");
                out.println(food + " Tons");
                out.println("<div class=\"dropdown-content\">");
                out.println("<p class=\"positive\"> +" + foodProduction + " Tons per month from farms</p>");
                out.println("<p class=\"negative\"> -" + foodConsumption + " Tons per month from population</p>");
                out.println("</div>");
                out.println("</div>");
            }
        }
        catch(SQLException e)
        {
            out.print("Error");
        }
    }
}
