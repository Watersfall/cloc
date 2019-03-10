package com.watersfall.clocgame.tags;

import com.watersfall.clocmath.FoodCalc;
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
                int food = Integer.parseInt(resultsMain.get("food").toString());
                int foodProduction = FoodCalc.getFoodProduction(resultsMain);
                int foodConsumption = FoodCalc.getStandardFoodCost(resultsPopulation);
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
