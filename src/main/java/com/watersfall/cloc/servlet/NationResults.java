package com.watersfall.cloc.servlet;

import com.watersfall.cloc.database.Database;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/nationresults")
public class NationResults extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String param, type;
        String sess, id = null;
        BasicDataSource database = Database.getDataSource();
        ResultSet sender = null, reciever = null;
        PrintWriter writer = response.getWriter();
        PreparedStatement readSend = null, readRecieve = null, send = null, recieve = null;

        try
        {
            if((sess = request.getSession().getId()) != null && (id = request.getParameter("id")) != null)
            {
                readSend = database.getConnection().prepareStatement("SELECT * FROM cloc WHERE sess=?");
                readSend.setString(1, sess);
                sender = readSend.executeQuery();
                readRecieve = database.getConnection().prepareStatement("SELECT * FROM cloc WHERE id=?");
                readRecieve.setInt(1, Integer.parseInt(id));
                reciever = readRecieve.executeQuery();
                if(!sender.first())
                {
                    writer.append("You are not logged in!");
                    return;
                }
                else if(!reciever.first())
                {
                    writer.append("There is no nation with that id!");
                    return;
                }
            }

            type = ((param = request.getParameter("sendoil")) != null) ? "oil"
                    : ((param = request.getParameter("sendrm")) != null) ? "rm"
                    : ((param = request.getParameter("sendmg")) != null) ? "mg"
                    : ((param = request.getParameter("sendcash")) != null) ? "budget"
                    : null;

            int amount = Integer.parseInt(param);
            if(amount > sender.getInt(type))
            {
                writer.append("You do not have enough oil!");
            }
            else if(amount <= 0)
            {
                writer.append("You cannot ship nothing!");
            }
            else
            {
                send = database.getConnection().prepareStatement("UPDATE cloc SET " + type + "=" + type + "-? WHERE sess=?");
                send.setInt(1, amount);
                send.setString(2, sess);
                recieve = database.getConnection().prepareStatement("UPDATE cloc SET " + type + "=" + type + "+? WHERE id=?");
                recieve.setInt(1, amount);
                recieve.setInt(2, Integer.parseInt(id));
                send.execute();
                recieve.execute();
                writer.append("Shipped!");
            }
        }
        catch(SQLException e)
        {
            writer.append("Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        catch(NumberFormatException | NullPointerException e)
        {
            writer.append("Don't do that");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                sender.close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                reciever.close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                send.getConnection().close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                readSend.getConnection().close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                recieve.getConnection().close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                readRecieve.getConnection().close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                send.close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                readSend.close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                recieve.close();
            }
            catch(Exception ex)
            {
            }
            try
            {
                readRecieve.close();
            }
            catch(Exception ex)
            {
            }
        }
    }
}
