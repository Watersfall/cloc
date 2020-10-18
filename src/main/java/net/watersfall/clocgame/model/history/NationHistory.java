package net.watersfall.clocgame.model.history;

import net.watersfall.clocgame.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NationHistory
{
	private Connection connection;
	private int id;

	public NationHistory(Connection connection, int id)
	{
		this.connection = connection;
		this.id = id;
	}

	public String getNationHistory() throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT gdp AS GDP, growth AS Growth, " +
				"population AS Population, airforce AS 'Airforce Size', navy AS 'Navy Size', army AS 'Army Size', " +
				"casualties AS Casualties FROM nation_history WHERE nation_id=? ORDER BY month");
		statement.setInt(1, this.id);
		return Util.convertToJson(statement.executeQuery());
	}
}
