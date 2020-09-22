package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.message.Declaration;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationCosmetic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeclarationDao extends Dao
{
	private static final String POST_DECLARATION_SQL_STATEMENT =
					"INSERT INTO declarations (sender, content, sent)\n" +
					"VALUES (?,?,?)";

	private static final String DECLARATION_PAGE_SQL_STATEMENT =
					"SELECT declarations.id, sender, sent, content, nation_cosmetic.* " +
					"FROM declarations, nation_cosmetic WHERE nation_cosmetic.id=sender " +
					"ORDER BY declarations.id DESC LIMIT 20 OFFSET ?";

	public DeclarationDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public ArrayList<Declaration> getDeclarationPage(int page) throws SQLException
	{
		ArrayList<Declaration> list = new ArrayList<>();
		PreparedStatement statement = connection.prepareStatement(DECLARATION_PAGE_SQL_STATEMENT);
		statement.setInt(1, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			Nation nation = new Nation(results.getInt("nation_cosmetic.id"));
			nation.setCosmetic(new NationCosmetic(results.getInt("nation_cosmetic.id"), results));
			Declaration declaration = new Declaration(results.getInt("declarations.id"), nation,
					results.getLong("sent"), results.getString("content"));
			list.add(declaration);
		}
		return list;
	}

	public void createDeclaration(Nation nation, String message) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(POST_DECLARATION_SQL_STATEMENT);
		statement.setInt(1, nation.getId());
		statement.setString(2, message);
		statement.setLong(3, System.currentTimeMillis());
		statement.execute();
	}
}
