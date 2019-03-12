package com.watersfall.clocgame.servlet.policies.economy;

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
@WebServlet(urlPatterns = "/policies/freemoneycapitalist")
public class PolicyFreeMoneyCapitalist extends HttpServlet
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
            PreparedStatement read = conn.prepareStatement("SELECT budget FROM cloc "
                    + "WHERE sess=? FOR UPDATE");
            read.setString(1, sess);
            results = read.executeQuery();
            if(!results.first())
            {
                writer.append("<p>You must be logged in to do this!</p>");
            }
            else
            {
                PreparedStatement update = conn.prepareStatement("UPDATE cloc SET budget=budget+1000, economic=economic+5 "
                        + "WHERE sess=?");
                update.setString(1, sess);
                PreparedStatement update2 = conn.prepareStatement("UPDATE cloc SET economic=100 "
                        + "WHERE sess=? && economic>100");
                update2.setString(1, sess);
                update.execute();
                update2.execute();
                conn.commit();
                writer.append("<p>You cut the pay and benefits for government employees to fund your newest projects!</p>");
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
