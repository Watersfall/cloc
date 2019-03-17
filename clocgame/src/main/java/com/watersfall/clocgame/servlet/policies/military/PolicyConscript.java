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

import com.watersfall.clocmath.PolicyConstants;
import com.watersfall.clocmath.PopulationMath;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Chris
 */
@WebServlet(urlPatterns = "/policies/conscript")
public class PolicyConscript extends HttpServlet
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
            ResultSet resultsMain;
            ResultSet resultsPopulation;
            PreparedStatement read = conn.prepareStatement("SELECT army, training FROM cloc "
                    + "WHERE sess=? FOR UPDATE");
            PreparedStatement readPop = conn.prepareStatement("SELECT * FROM cloc_population "
                    + "WHERE sess=? FOR UPDATE");
            read.setString(1, sess);
            readPop.setString(1, sess);
            resultsMain = read.executeQuery();
            resultsPopulation = readPop.executeQuery();
            if(!resultsMain.first() || !resultsPopulation.first())
            {
                writer.append("<p>You must be logged in to do this!</p>");
            }
            else
            {
                int costManpower = PolicyConstants.COST_CONSCRIPT_MANPOWER;
                int costTraining = (int)((resultsMain.getInt("training") / 100) * (4 / resultsMain.getInt("army")));
                int availableManpower = PopulationMath.getAvailableManpower(resultsMain, resultsPopulation);
                if(costManpower > availableManpower)
                {
                    writer.append("<p>You do not have enough manpower!</p>");
                }
                else
                {
                    PreparedStatement update = conn.prepareStatement("UPDATE cloc "
                            + "SET army=army+?, training=training-? "
                            + "WHERE sess=?");
                    update.setInt(1, PolicyConstants.GAIN_CONSCRIPT);
                    update.setInt(2, costTraining);
                    update.setString(3, sess);
                    update.execute();
                    conn.commit();
                    writer.append("<p>You conscript thousands of men into your army!</p>");
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
