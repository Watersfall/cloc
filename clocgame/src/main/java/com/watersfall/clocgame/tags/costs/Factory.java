package com.watersfall.clocgame.tags.costs;

import com.watersfall.clocmath.PolicyMath;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Factory extends SimpleTagSupport
{
    private Result main;
    StringWriter sw = new StringWriter();

    public void setMain(Result main)
    {
        this.main = main;
    }

    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        try
        {
            if(main.getRowCount() <= 0)
            {
                throw new SQLException();
            }
            else
            {
                int costRm = PolicyMath.getFactoryRmCost(main.getRows()[0]);
                int costOil = PolicyMath.getFactoryOilCost(main.getRows()[0]);
                int costMg = PolicyMath.getFactoryMgCost(main.getRows()[0]);
                out.print(costRm + " HTons of Raw Materials");
                if(costMg == 0)
                {
                    out.print(" and " + costOil + "Mmbls of Oil");
                }
                else
                {
                    out.print(", " + costOil + " Oil, and " + costMg + " Tons of Manufactured Goods");
                }
            }
        }
        catch(SQLException e)
        {
            out.print("Error");
        }
    }
}
