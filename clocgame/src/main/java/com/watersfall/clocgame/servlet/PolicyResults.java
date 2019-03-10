package com.watersfall.clocgame.servlet;

import com.watersfall.clocgame.database.Database;
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
import org.apache.commons.dbcp2.BasicDataSource;

@WebServlet("/policyresults")
public class PolicyResults extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        BasicDataSource database = Database.getDataSource();
        String param, sess;
        ResultSet results = null;
        PrintWriter writer = response.getWriter();
        PreparedStatement statement = null, check = null;
        try
        {
            if((sess = request.getSession().getId()) != null)
            {
                check = database.getConnection().prepareStatement("SELECT * FROM clocgame WHERE sess=?");
                check.setString(1, sess);
                results = check.executeQuery();
            }
            if(results != null && !results.first() || results == null)
            {
                writer.append("You must be logged in to do this!");
            }
            else if((param = request.getParameter("policy")) == null)
            {
                writer.append("You donate all your money to charity, how thoughtful");
            }
            else
            {

                /*
                 ** Economic Policy
                 */
                switch(param)
                {
                    case "freemoneycapitalist":
                        statement = database.getConnection().prepareStatement("UPDATE clocgame SET budget=budget+1000, economic=economic+5 "
                                + "WHERE sess=?");
                        statement.setString(1, sess);
                        statement.execute();
                        statement.getConnection().close();
                        statement.close();
                        statement = database.getConnection().prepareStatement("UPDATE clocgame SET economic=100 WHERE economic>100");
                        statement.execute();
                        statement.getConnection().close();
                        statement.close();
                        writer.append("You cut the pay and benefits for government employees to fund your newest projects");
                        break;
                    case "freemoneycommunist":
                        statement = database.getConnection().prepareStatement("UPDATE clocgame SET budget=budget+1000, economic=economic-5 "
                                + "WHERE sess=?");
                        statement.setString(1, sess);
                        statement.execute();
                        statement.getConnection().close();
                        statement.close();
                        statement = database.getConnection().prepareStatement("UPDATE clocgame SET economic=0 WHERE economic<0");
                        statement.execute();
                        statement.getConnection().close();
                        statement.close();
                        writer.append("You raise taxes by 1% to fund your newest projects");
                        break;
                    case "drill":
                    {
                        int cost = 500 + results.getInt("wells") * 100;
                        if(results.getInt("budget") < cost)
                        {
                            writer.append("You do not have enough money");
                        }
                        else
                        {
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET wells=wells+1, budget=budget-? "
                                    + "WHERE sess=?;");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.append("You drill a new oil well!");
                        }
                        break;
                    }
                    case "mine":
                    {
                        int cost = 500 + results.getInt("mines") * 50;
                        if(results.getInt("budget") < cost)
                        {
                            writer.append("You do not have enough money");
                        }
                        else
                        {
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET mines=mines+1, budget=budget-? "
                                    + "WHERE sess=?;");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.append("You dig a new mine!");
                        }
                        break;
                    }
                    case "industrialize":
                    {
                        int costRm = 50 + (results.getInt("industry") + results.getInt("milindustry")) * 50;
                        int costOil = 25 + (results.getInt("industry") + results.getInt("milindustry")) * 25;
                        int costMg = 0 + (results.getInt("industry") + results.getInt("milindustry")) * 5;
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
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET industry=industry+1, rm=rm-?, oil=oil-?, mg=mg-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, costRm);
                            statement.setInt(2, costOil);
                            statement.setInt(3, costMg);
                            statement.setString(4, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.append("Your farmers flock to the city for a new life!");
                        }
                        break;
                    }
                    case "militarize":
                    {
                        int costRm = 50 + (results.getInt("industry") + results.getInt("milindustry")) * 50;
                        int costOil = 25 + (results.getInt("industry") + results.getInt("milindustry")) * 25;
                        int costMg = 0 + (results.getInt("industry") + results.getInt("milindustry")) * 5;
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
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET milindustry=milindustry+1, rm=rm-?, oil=oil-?, mg=mg-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, costRm);
                            statement.setInt(2, costOil);
                            statement.setInt(3, costMg);
                            statement.setString(4, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.append("Your farmers flock to the city for a new life!");
                        }
                        break;
                    }
                    case "nitrogenplant":
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
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET nitrogenplant=nitrogenplant+1, rm=rm-?, oil=oil-?, mg=mg-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, costRm);
                            statement.setInt(2, costOil);
                            statement.setInt(3, costMg);
                            statement.setString(4, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.append("You build a new Ammonia plant!");
                        }
                        break;
                    }
                    /*
                         ** Domestic Policy
                     */
                    case "crackdown":
                    {
                        int cost = 100;
                        if(results.getInt("budget") < 100)
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
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET stability=stability+5, approval=approval-3, political=political+5, budget=budget-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET stability=100 "
                                    + "WHERE stability>100");
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.print("Your police force arrests every petty criminal they could find!");
                        }
                        break;
                    }
                    case "free":
                    {
                        int cost = 100;
                        if(results.getInt("budget") < 100)
                        {
                            writer.print("You do not have enough money!");
                        }
                        else if(results.getInt("political") <= 4)
                        {
                            writer.print("You have no more prisoners to free!");
                        }
                        else if(results.getInt("stability") <= 4)
                        {
                            writer.print("You are not stable enough!");
                        }
                        else
                        {
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET stability=stability-5, approval=approval+3, political=political-5, budget=budget-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET approval=100 "
                                    + "WHERE approval>100");
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.print("Your convicts enjoy their freedom!");
                        }
                        break;
                    }
                    case "university":
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
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET university=university+1, rm=rm-?, oil=oil-?, mg=mg-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, costRm);
                            statement.setInt(2, costOil);
                            statement.setInt(3, costMg);
                            statement.setString(4, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.append("You build a new University!");
                        }
                        break;
                    }
                    /*
                         ** Foreign Policy
                     */
                    case "alignentente":
                    {
                        int cost = 100;
                        if(results.getInt("budget") < 100)
                        {
                            writer.print("You do not have enough money!");
                        }
                        else if(results.getInt("alignment") == -1)
                        {
                            writer.print("You are already aligned with the Entente!");
                        }
                        else
                        {
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET alignment=-1, budget=budget-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.print("You align yourself with the Entente!");
                        }
                        break;
                    }
                    case "alignneutral":
                    {
                        int cost = 100;
                        if(results.getInt("budget") < 100)
                        {
                            writer.print("You do not have enough money!");
                        }
                        else if(results.getInt("alignment") == 0)
                        {
                            writer.print("You are already neutral!");
                        }
                        else
                        {
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET alignment=0, budget=budget-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.print("Your people cheer as you declare your neutrality!");
                        }
                        break;
                    }
                    case "aligncentral":
                    {
                        int cost = 100;
                        if(results.getInt("budget") < 100)
                        {
                            writer.print("You do not have enough money!");
                        }
                        else if(results.getInt("alignment") == 1)
                        {
                            writer.print("You are already aligned with the Central Powers!");
                        }
                        else
                        {
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET alignment=1, budget=budget-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.print("You align yourself with the Central Powers!");
                        }
                        break;
                    }
                    /*
                         ** Military Policy
                     */
                    case "conscript":
                        if(results.getInt("manpower") <= 1)
                        {
                            writer.print("You do not have enough manpower!");
                        }
                        else
                        {
                            int costTraining = results.getInt("training") * (4 / results.getInt("army"));
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET manpower=manpower-2, army=army+2, training=training-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, costTraining);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.print("You conscript thousands of men into your army!");
                        }
                        break;
                    case "train":
                    {
                        int cost = (int) Math.floor(results.getInt("army") * Math.pow(results.getInt("training"), 2) / 100);
                        if(results.getInt("budget") < cost)
                        {
                            writer.print("You do not have enough money!");
                        }
                        else if(results.getInt("training") >= 100)
                        {
                            writer.print("Your men are already fully trained");
                        }
                        else
                        {
                            statement = database.getConnection().prepareStatement("UPDATE clocgame SET training=training+5, budget=budget-? "
                                    + "WHERE sess=?");
                            statement.setInt(1, cost);
                            statement.setString(2, sess);
                            statement.execute();
                            statement.getConnection().close();
                            statement.close();
                            writer.print("You train your men into a fine killing machine!");
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        catch(SQLException e)
        {
            writer.append("Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                check.getConnection().close();
            }
            catch(Exception e)
            {
            }
            try
            {
                check.close();
            }
            catch(Exception e)
            {
            }
            try
            {
                statement.getConnection().close();
            }
            catch(Exception e)
            {
            }
            try
            {
                statement.close();
            }
            catch(Exception e)
            {
            }
            try
            {
                results.close();
            }
            catch(Exception e)
            {
            }
        }
    }
}
