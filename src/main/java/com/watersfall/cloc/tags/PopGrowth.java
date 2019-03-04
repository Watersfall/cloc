package com.watersfall.cloc.tags;

import com.watersfall.cloc.math.FoodCalc;
import com.watersfall.cloc.math.PopGrowthCalc;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PopGrowth extends SimpleTagSupport
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
                int population = Integer.parseInt(results.get("population").toString());
                double migrationGrowth = (PopGrowthCalc.getPopGrowthFromEmployment(results));
                double foodGrowth = (PopGrowthCalc.getPopGrowthFromFoodProduction(results));
                out.println("<div class=\"dropdown\">");
                out.println("<span>");
                out.println(population + " People");
                out.println("<div class=\"dropdown-content\">");
                if(migrationGrowth < 0)
                {
                    out.println("<p class=\"negative\"> -" + migrationGrowth + "% per month from emigration</p>");
                }
                else
                {
                    out.println("<p class=\"positive\"> +" + migrationGrowth + "% per month from immigration</p>");
                }
                if(foodGrowth < 0)
                {
                    out.println("<p class=\"negative\"> -" + foodGrowth + "% per month from famine</p>");
                }
                else
                {
                    out.println("<p class=\"positive\"> +" + foodGrowth + "% per month from surplus food</p>");
                }
                out.println("<p class=\"positive\"> +" + PopGrowthCalc.BASE_GROWTH + "% base population growth</p>");
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
