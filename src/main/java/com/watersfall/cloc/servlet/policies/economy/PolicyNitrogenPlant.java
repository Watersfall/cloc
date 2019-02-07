package com.watersfall.cloc.servlet.policies.economy;

import com.watersfall.cloc.database.Database;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Chris
 */
@WebServlet(urlPatterns = "/policies/nitrogenplant")
public class PolicyNitrogenPlant extends HttpServlet
{

    static BasicDataSource database = Database.getDataSource();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String sess = request.getSession().getId();
        PrintWriter writer = response.getWriter();
        Connection conn = null;
        try
        {
            conn = database.getConnection();
            ResultSet results;
            PreparedStatement read = conn.prepareStatement("SELECT university, nitrogenplant, rm, oil, mg FROM cloc "
                    + "WHERE sess=? FOR UPDATE");
            read.setString(1, sess);
            results = read.executeQuery();
            if(!results.first())
            {
                writer.append("You must be logged in to do this!");
            }
            else
            {
                int costRm = 100 + (results.getInt("nitrogenplant") + results.getInt("university")) * 100;
                int costOil = 50 + (results.getInt("nitrogenplant") + results.getInt("university")) * 50;
                int costMg = 10 + (results.getInt("nitrogenplant") + results.getInt("university")) * 10;
                if(results.getInt("rm") < costRm)
                {
                    writer.append("You do not have enough raw material!");
                }
                else if(results.getInt("oil") < costOil)
                {
                    writer.append("You do not have enough oil!");
                }
                else if(results.getInt("mg") < costMg)
                {
                    writer.append("You do not have enough manufactured goods!");
                }
                else
                {
                    PreparedStatement update = conn.prepareStatement("UPDATE cloc "
                            + "SET rm=rm-?, oil=oil-?, mg=mg-?, nitrogenplant=nitrogenplant+1 "
                            + "WHERE sess=?");
                    update.setInt(1, costRm);
                    update.setInt(2, costOil);
                    update.setInt(3, costMg);
                    update.setString(4, sess);
                    update.execute();
                    conn.commit();
                    writer.append("You build a new Ammonia plant!");
                }
            }
        }
        catch(SQLException e)
        {
            try
            {
                conn.rollback();
            }
            catch(Exception ex)
            {
                //Ignore
            }
            writer.append("Error: " + e.getLocalizedMessage());
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                //Ignore
            }
        }
    }
}
