package com.watersfall.clocturn;

import com.watersfall.clocmath.math.PopGrowthMath;

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

			}
		}
		catch(SQLException ex)
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
