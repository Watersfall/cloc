package com.watersfall.clocgame.servlet.policies.domestic;

import com.watersfall.clocgame.database.Database;
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
@WebServlet(urlPatterns = "/policies/free")
public class PolicyFree extends HttpServlet
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
            PreparedStatement read = conn.prepareStatement("SELECT budget, approval, stability, political FROM cloc "
                    + "WHERE sess=? FOR UPDATE");
            read.setString(1, sess);
            results = read.executeQuery();
            if(!results.first())
            {
                writer.append("<p>You must be logged in to do this!</p>");
            }
            int cost = 100;
            if(results.getInt("budget") < cost)
            {
                writer.append("<p>You do not have enough money!</p>");
            }
            else if(results.getInt("political") <= 4)
            {
                writer.append("<p>You have no more prisoners to free!</p>");
            }
            else if(results.getInt("stability") <= 4)
            {
                writer.append("<p>You are not stable enough!</p>");
            }
            else
            {
                PreparedStatement update = conn.prepareStatement("UPDATE cloc "
                        + "SET stability=stability-5, approval=approval+3, political=political-5, budget=budget-? "
                        + "WHERE sess=?");
                update.setInt(1, cost);
                update.setString(2, sess);
                PreparedStatement update2 = conn.prepareStatement("UPDATE cloc SET approval=100 "
                        + "WHERE approval>100 && sess=?");
                update2.setString(1, sess);
                update.execute();
                update2.execute();
                conn.commit();
                writer.append("<p>Your convicts enjoy their freedom!</p>");
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
            writer.append("<p>Error: " + e.getLocalizedMessage() + "!</p>");
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