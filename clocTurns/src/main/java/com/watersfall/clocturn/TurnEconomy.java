package com.watersfall.clocturn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnEconomy extends Turn
{

    public TurnEconomy(int offset)
    {
        super(offset);
    }

    @Override
    public void doTurn()
    {
        try 
        {
            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM cloc");
            while(results.next())
            {
                PreparedStatement resources = connection.prepareStatement("UPDATE cloc SET rm=rm+?, oil=oil+?, mg=mg+? WHERE id=?");
                resources.setInt(1, results.getInt("mines") > 0 ? results.getInt("mines") : 0);
                resources.setInt(2, results.getInt("wells") > 0 ? results.getInt("wells") : 0);
                resources.setInt(3, results.getInt("industry") > 0 ? results.getInt("industry") : 0);
                resources.setInt(4, results.getInt("id"));
                resources.execute();
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
    }
}
