package com.watersfall.cloc.servlet;

import com.watersfall.cloc.database.Database;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

@WebServlet("/nationresults")
public class NationResults extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String sess = request.getSession().getId();
        PrintWriter writer = response.getWriter();
        Connection conn = null;
        try
        {
            String type, param;
            conn = Database.getDataSource().getConnection();
            int id = Integer.parseInt(request.getParameter("id"));
            type = ((param = request.getParameter("sendoil")) != null) ? "oil"
                    : ((param = request.getParameter("sendrm")) != null) ? "rm"
                    : ((param = request.getParameter("sendmg")) != null) ? "mg"
                    : ((param = request.getParameter("sendcash")) != null) ? "budget"
                    : null;
            PreparedStatement sender = conn.prepareStatement("SELECT " + type + ", id FROM cloc WHERE sess=? FOR UPDATE");
            sender.setString(1, sess);
            PreparedStatement reciever = conn.prepareStatement("SELECT " + type + ", id FROM cloc WHERE id=? FOR UPDATE");
            reciever.setInt(1, id);
            ResultSet resultsSender = sender.executeQuery();
            ResultSet resultsReciever = reciever.executeQuery();
            if(!resultsSender.first())
            {
                writer.print("You must be logged in to do this!");
            }
            else if(!resultsReciever.first())
            {
                writer.print("Nation with that id does not exist!");
            }
            else if(resultsSender.getInt("id") == resultsReciever.getInt("id"))
            {
                writer.print("You appreciate the gift to yourself!");
            }
            else if(Integer.parseInt(param) <= 0)
            {
                writer.print("Cannot send 0 or less!");
            }
            else if(resultsSender.getInt(type) < Integer.parseInt(param))
            {
                writer.print("You do not have enough to send!");
            }
            else
            {
                PreparedStatement send = conn.prepareStatement("UPDATE cloc SET " + type + "=" + type + "-? WHERE sess=?");
                send.setInt(1, Integer.parseInt(param));
                send.setString(2, sess);
                PreparedStatement recieve = conn.prepareStatement("UPDATE cloc SET " + type + "=" + type + "+? WHERE id=?");
                recieve.setInt(1, Integer.parseInt(param));
                recieve.setInt(2, id);
                send.execute();
                recieve.execute();
                conn.commit();
            }
        }
        catch(SQLException e)
        {
            writer.append("Error: " + e.getLocalizedMessage());
            try
            {
                conn.rollback();
            }
            catch(Exception ex)
            {
                //Ignore
            }
        }
        catch(NumberFormatException | NullPointerException e)
        {
            writer.append("Don't do that");
        }
        catch(Exception e)
        {
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
