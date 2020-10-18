package net.watersfall.clocgame.dao;

import net.watersfall.clocgame.model.nation.Production;

import java.sql.*;

public class ProductionDao extends Dao
{
	private static final String DELETE_PRODUCTION_SQL_STATEMENT =
					"DELETE FROM production\n" +
					"WHERE id=?\n";
	private static final String RESET_EFFICIENCY_SQL_STATEMENT =
					"UPDATE factories\n" +
					"SET efficiency=1500\n" +
					"WHERE production_id=?\n";
	private static final String DEFAULT_PRODUCTION_SQL_STATEMENT =
					"INSERT INTO production (owner, production, progress)\n" +
					"VALUES (?, 'MUSKET', 0)\n";
	private static final String DEFAULT_FACTORY_SQL_STATEMENT =
					"UPDATE factories\n" +
					"SET production_id=?\n" +
					"WHERE production_id IS NULL AND owner=?\n" +
					"LIMIT 1\n";
	private static final String ADD_FACTORIES_SQL_STATEMENT =
					"UPDATE factories\n" +
					"SET production_id=?, efficiency=1500\n" +
					"WHERE production_id IS NULL\n" +
					"LIMIT ?\n";
	private static final String REMOVE_FACTORIES_SQL_STATEMENT =
					"UPDATE factories\n" +
					"SET production_id = NULL, efficiency=1500\n" +
					"WHERE production_id=?\n" +
					"ORDER BY efficiency\n" +
					"LIMIT ?\n";


	public ProductionDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public void deleteProductionById(int id) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTION_SQL_STATEMENT);
		PreparedStatement resetEfficiency = connection.prepareStatement(RESET_EFFICIENCY_SQL_STATEMENT);
		statement.setInt(1, id);
		resetEfficiency.setInt(1, id);
		resetEfficiency.execute();
		statement.execute();
	}

	public int createDefaultProduction(int id) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(DEFAULT_PRODUCTION_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, id);
		statement.execute();
		ResultSet key = statement.getGeneratedKeys();
		PreparedStatement factory = connection.prepareStatement(DEFAULT_FACTORY_SQL_STATEMENT);
		key.first();
		factory.setInt(1, key.getInt(1));
		factory.setInt(2, id);
		factory.execute();
		return key.getInt(1);
	}

	public void addFactories(long id, int amount) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(ADD_FACTORIES_SQL_STATEMENT);
		statement.setLong(1, id);
		statement.setInt(2, amount);
		statement.execute();
	}

	public void removeFactories(long id, int amount) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(REMOVE_FACTORIES_SQL_STATEMENT);
		statement.setLong(1, id);
		statement.setInt(2, amount);
		statement.execute();
	}

	public void saveProduction(Production production) throws SQLException
	{
		requireWriteAccess();
		production.update(connection);
	}
}
