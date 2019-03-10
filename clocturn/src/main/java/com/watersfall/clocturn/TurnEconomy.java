package com.watersfall.clocturn;

import com.watersfall.clocmath.PopGrowthCalc;

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
            ResultSet resultsPopulation = connection.createStatement().executeQuery("SELECT * FROM cloc_population");

            while(results.next())
            {
                resultsPopulation.next();
                PreparedStatement resources = connection.prepareStatement("UPDATE cloc SET rm=rm+?, oil=oil+?, mg=mg+? WHERE id=?");
                PreparedStatement population = connection.prepareStatement("UPDATE cloc_population SET asian=? WHERE id=?");
                resources.setInt(1, results.getInt("mines") > 0 ? results.getInt("mines") : 0);
                resources.setInt(2, results.getInt("wells") > 0 ? results.getInt("wells") : 0);
                resources.setInt(3, results.getInt("industry") > 0 ? results.getInt("industry") : 0);
                resources.setInt(4, results.getInt("id"));
                population.setInt(1, (int)(resultsPopulation.getInt("asian") * java.lang.Math.pow(java.lang.Math.E, PopGrowthCalc.getPopGrowth(results, resultsPopulation))));
                population.setInt(2, results.getInt("id"));
                resources.execute();
                population.execute();
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
