package com.watersfall.clocgame.tags;

import com.watersfall.clocmath.PopulationConstants;
import com.watersfall.clocmath.PopGrowthMath;
import com.watersfall.clocmath.PopulationMath;
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
    private Result main, population;
    StringWriter sw = new StringWriter();

    public void setMain(Result main)
    {
        this.main = main;
    }

    public void setPopulation(Result population)
    {
        this.population = population;
    }

    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        try
        {
            if(main.getRowCount() <= 0 || population.getRowCount() <= 0)
            {
                throw new SQLException();
            }
            else
            {
                SortedMap resultsMain = main.getRows()[0];
                SortedMap resultsPopulation = population.getRows()[0];
                int population = PopulationMath.getPopulation(resultsPopulation);
                double migrationGrowth = PopGrowthMath.getPopGrowthFromEmployment(resultsMain, resultsPopulation) * 100;
                double foodGrowth = PopGrowthMath.getPopGrowthFromFoodProduction(resultsMain, resultsPopulation) * 100;
                out.println("<div class=\"dropdown\">");
                out.println("<span>");
                out.println(population + " People");
                out.println("<div class=\"dropdown-content\">");
                if(migrationGrowth < 0)
                {
                    out.println("<p class=\"negative\"> " + String.format("%.2f", migrationGrowth) + "% per month from emigration</p>");
                }
                else
                {
                    out.println("<p class=\"positive\"> +" + String.format("%.2f", migrationGrowth) + "% per month from immigration</p>");
                }
                if(foodGrowth < 0)
                {
                    out.println("<p class=\"negative\"> " + String.format("%.2f", foodGrowth) + "% per month from famine</p>");
                }
                else
                {
                    out.println("<p class=\"positive\"> +" + String.format("%.2f", foodGrowth) + "% per month from surplus food</p>");
                }
                out.println("<p class=\"positive\"> +" + PopulationConstants.BASE_GROWTH * 100 + "% base population growth</p>");
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
