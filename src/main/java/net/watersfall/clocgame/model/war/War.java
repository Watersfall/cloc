package net.watersfall.clocgame.model.war;

import lombok.Getter;
import lombok.Setter;
import net.watersfall.clocgame.model.nation.Nation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class War
{
	private @Getter @Setter Nation attacker;
	private @Getter @Setter Nation defender;
	private @Getter @Setter int start;
	private @Getter @Setter int end;
	private @Getter @Setter int id;
	private @Getter @Setter int peace;
	private @Getter @Setter Nation winner;
	private @Getter @Setter String name;

	public War(int id, ResultSet results) throws SQLException
	{
		this.start = results.getInt("wars.start");
		this.end = results.getInt("wars.end");
		this.id = id;
		this.peace = results.getInt("wars.peace");
		this.name = results.getString("wars.name");
	}
}
