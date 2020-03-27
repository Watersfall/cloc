package com.watersfall.clocgame.model.message;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationCosmetic;
import com.watersfall.clocgame.text.Responses;
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

	public static ArrayList<Declaration> getDeclarationsPage(Connection conn, int page) throws SQLException
	{
		ArrayList<Declaration> list = new ArrayList<>();
		PreparedStatement statement = conn.prepareStatement(
				"SELECT cloc_declarations.id, sender, sent, content, cloc_cosmetic.* " +
				"FROM cloc_declarations, cloc_cosmetic WHERE cloc_cosmetic.id=sender " +
				"ORDER BY cloc_declarations.id DESC LIMIT 20 OFFSET ?");
		statement.setInt(1, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			NationCosmetic cosmetic = new NationCosmetic(results.getInt("cloc_cosmetic.id"), results);
			Declaration declaration = new Declaration(results.getInt("cloc_declarations.id"), cosmetic,
					results.getInt("sent"), results.getString("content"));
			list.add(declaration);
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
			post.setLong(2, Util.month);
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
		this.sender = new NationCosmetic(results.getInt("sender"), results);
	}

	public Declaration(int id, NationCosmetic sender, int sent, String content)
	{
		this.id = id;
		this.sender = sender;
		this.sent = sent;
		this.content = content;
	}
}
