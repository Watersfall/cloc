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
			ResultSet resultsEconomy = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM cloc_economy");
			ResultSet resultsDomestic = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM cloc_domestic");
			while(resultsEconomy.next() && resultsDomestic.next())
			{
				resultsEconomy.updateInt("oil", resultsEconomy.getInt("oil_wells") + resultsEconomy.getInt("oil"));
				resultsEconomy.updateInt("coal", resultsEconomy.getInt("coal_mines") + resultsEconomy.getInt("coal"));
				resultsEconomy.updateInt("iron", resultsEconomy.getInt("iron_mines") + resultsEconomy.getInt("iron"));
				resultsEconomy.updateInt("manufactured", resultsEconomy.getInt("civilian_industry") + resultsEconomy.getInt("manufactured"));
				resultsDomestic.updateInt("population", (int)(resultsDomestic.getInt("population") + (resultsDomestic.getInt("population") * PopGrowthMath.getPopGrowth(resultsEconomy, resultsDomestic))));
				resultsEconomy.updateRow();
				resultsDomestic.updateRow();
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
