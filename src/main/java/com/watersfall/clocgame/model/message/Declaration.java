package com.watersfall.clocgame.model.message;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationCosmetic;
import com.watersfall.clocgame.util.Util;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Declaration
{
	public static final int COST = 100;
	private @Getter int id;
	private @Getter NationCosmetic sender;
	private @Getter int sent;
	private @Getter String content;

	public static ArrayList<Declaration> getDeclarations(Connection conn) throws SQLException
	{
		ArrayList<Declaration> list = new ArrayList<>();
		PreparedStatement statement = conn.prepareStatement("SELECT id FROM cloc_declarations ORDER BY id DESC");
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			list.add(new Declaration(conn, results.getInt(1)));
		}
		return list;
	}

	public static String post(Nation nation, String message, Connection conn) throws SQLException
	{
		int cost = COST;
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(message.length() > 2048)
		{
			return Responses.tooLong("Message", 2048);
		}
		else if(message.length() < 1)
		{
			return Responses.nullFields();
		}
		else
		{
			PreparedStatement post = conn.prepareStatement("INSERT INTO cloc_declarations (sender, sent, content) VALUES (?,?,?)");
			post.setInt(1, nation.getId());
			post.setInt(2, Util.turn);
			post.setString(3, message);
			post.execute();
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			return Responses.declaration();
		}
	}

	public Declaration(Connection conn, int id) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM cloc_declarations WHERE id=?");
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		this.id = results.getInt("id");
		this.sent = results.getInt("sent");
		this.content = results.getString("content");
		this.sender = new NationCosmetic(conn, results.getInt("sender"), false);
	}
}
