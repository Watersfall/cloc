package com.watersfall.clocgame.model.war;

import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;
import lombok.Setter;

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

	public War(int id, ResultSet results) throws SQLException
	{
		this.start = results.getInt("cloc_war.start");
		this.end = results.getInt("cloc_war.end");
		this.id = id;
		this.peace = results.getInt("cloc_war.peace");
	}
}
