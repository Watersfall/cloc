package com.watersfall.cloc.servlet.policies.foreign;

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
@WebServlet(urlPatterns = "/policies/aligncentral")
public class PolicyAlignCentralPowers extends HttpServlet
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
            PreparedStatement read = conn.prepareStatement("SELECT budget, alignment FROM cloc "
                    + "WHERE sess=? FOR UPDATE");
            read.setString(1, sess);
            results = read.executeQuery();
            if(!results.first())
            {
                writer.append("<p><p>You must be logged in to do this!</p>");
            }
            else
            {
                int cost = 100;
                if(cost > results.getInt("budget"))
                {
                    writer.append("<p><p>You do not have enough money!</p>");
                }
                else if(results.getInt("alignment") == 1)
                {
                    writer.append("<p><p>You are already aligned with the Central Powers!</p>");
                }
                else
                {
                    PreparedStatement update = conn.prepareStatement("UPDATE cloc SET alignment=1, budget=budget-? "
                            + "WHERE sess=?");
                    update.setInt(1, cost);
                    update.setString(2, sess);
                    update.execute();
                    conn.commit();
                    writer.append("<p>You align yourself with the Central Powers!</p>");
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
            writer.append("<p><p>Error: " + e.getLocalizedMessage() + "!</p>");
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