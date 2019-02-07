package com.watersfall.cloc.servlet.policies.domestic;

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
@WebServlet(urlPatterns = "/policies/crackdown")
public class PolicyCrackdown extends HttpServlet
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
            int cost = 100;
            PreparedStatement read = conn.prepareStatement("SELECT budget, approval, stability, political FROM cloc "
                    + "WHERE sess=? FOR UPDATE");
            read.setString(1, sess);
            results = read.executeQuery();
            if(!results.first())
            {
                writer.append("You must be logged in to do this!");
            }
            else 
            {
                if(results.getInt("budget") < cost)
                {
                    writer.print("You do not have enough money!");
                }
                else if(results.getInt("political") >= 96)
                {
                    writer.print("There are no more lollygaggers to arrest!");
                }
                else if(results.getInt("approval") < 3)
                {
                    writer.print("You are not popular enough to do this!");
                }
                else
                {
                    PreparedStatement update = conn.prepareStatement("UPDATE cloc "
                            + "SET stability=stability+5, approval=approval-3, political=political+5, budget=budget-? "
                            + "WHERE sess=?");
                    update.setInt(1, cost);
                    update.setString(2, sess);
                    PreparedStatement update2 = conn.prepareStatement("UPDATE cloc SET stability=100 "
                            + "WHERE stability>100 && sess=?");
                    update2.setString(1, sess);
                    update.execute();
                    update2.execute();
                    conn.commit();
                    writer.print("Your police force arrests every petty criminal they could find!");
                }
            }
        }
        catch(Exception e)
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
