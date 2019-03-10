package com.watersfall.clocgame.servlet.policies.military;

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
@WebServlet(urlPatterns = "/policies/train")
public class PolicyTrain extends HttpServlet
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
            PreparedStatement read = conn.prepareStatement("SELECT budget, army, training FROM cloc "
                    + "WHERE sess=? FOR UPDATE");
            read.setString(1, sess);
            results = read.executeQuery();
            if(!results.first())
            {
                writer.append("<p><p>You must be logged in to do this!</p>");
            }
            else
            {
                int cost = (int) Math.floor(results.getInt("army") * Math.pow(results.getInt("training"), 2) / 100);
                if(cost> results.getInt("budget"))
                {
                    writer.append("<p><p>You do not have enough money!</p>");
                }
                else if(results.getInt("training") >= 100)
                {
                    writer.append("<p><p>Your men are already fully trained!</p>");
                }
                else
                {
                    PreparedStatement update = conn.prepareStatement("UPDATE cloc SET training=training+5, budget=budget-? "
                            + "WHERE sess=?");
                    update.setInt(1, cost);
                    update.setString(2, sess);
                    update.execute();
                    conn.commit();
                    writer.append("<p>You train your men into a fine killing machine!</p>");
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
            writer.append("<p><p>Error: " + e.getLocalizedMessage());
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