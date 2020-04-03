package com.watersfall.clocgame.model.war;

import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.military.LogType;
import lombok.Getter;

import java.sql.*;

public class Log
{
	private @Getter int id;
	private @Getter int attacker;
	private @Getter Region region;
	private @Getter LogType type;
	private @Getter int amount;

	private Log(int id, int attacker, Region region, LogType type, int amount)
	{
		this.id = id;
		this.attacker = attacker;
		this.region = region;
		this.type = type;
		this.amount = amount;
	}

	public static Log createLog(Connection conn, int attacker, Region region, LogType type, int amount) throws SQLException
	{
		PreparedStatement create = conn.prepareStatement("INSERT INTO cloc_war_logs (attacker, region, type, amount) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		create.setInt(1, attacker);
		create.setString(2, region.getName());
		create.setString(3, type.getName());
		create.setInt(4, amount);
		create.execute();
		ResultSet keys = create.getGeneratedKeys();
		if(!keys.first())
		{
			return null;
		}
		else
		{
			return new Log(keys.getInt(1), attacker, region, type, amount);
		}
	}

	public static boolean checkLog(Connection connection, int attacker, Region region, LogType type) throws SQLException
	{
		PreparedStatement check = connection.prepareStatement("SELECT id FROM cloc_war_logs WHERE attacker=? AND region=? AND type=?");
		check.setInt(1, attacker);
		check.setString(2, region.getName());
		check.setString(3, type.getName());
		ResultSet results = check.executeQuery();
		return results.first();
	}
}
