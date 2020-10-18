package net.watersfall.clocgame.dao;

import net.watersfall.clocgame.model.military.army.*;
import net.watersfall.clocgame.model.producible.Producibles;

import java.sql.*;

public class ArmyDao extends Dao
{
	private static final String CREATE_EQUIPMENT = "INSERT INTO army_equipment (owner, type, amount) VALUES (?,?,?)";
	private static final String CREATE_BATTALION = "INSERT INTO army_battalions (owner, size, type) VALUES (?,?,?)";
	private static final String DELETE_BATTALION = "DELETE FROM army_battalions WHERE id=?";
	private static final String CREATE_ARMY =
					"INSERT INTO armies (owner, name, training, experience, specialization_type, specialization_amount, location, location_id, priority) " +
					"VALUES (?,?,0,0,?,0,?,?,?)";
	private static final String DELETE_ARMY = "DELETE FROM armies WHERE id=?";

	public ArmyDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public Army getArmyById(int id)
	{
		return null;
	}

	public void createEquipment(long owner, Producibles producible, int amount) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(CREATE_EQUIPMENT);
		statement.setLong(1, owner);
		statement.setString(2, producible.name());
		statement.setInt(3, amount);
		statement.execute();
	}

	public void createBattalion(long owner, BattalionType type) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(CREATE_BATTALION);
		statement.setLong(1, owner);
		statement.setInt(2, 0);
		statement.setString(3, type.name());
		statement.execute();
	}

	public void deleteBattalion(long id) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(DELETE_BATTALION);
		statement.setLong(1, id);
		statement.execute();
	}

	public void createArmy(int owner) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(CREATE_ARMY, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, owner);
		statement.setString(2, "New Army");
		statement.setString(3, ArmySpecialization.NONE.name());
		statement.setString(4, ArmyLocation.NATION.name());
		statement.setInt(5, owner);
		statement.setString(6, Priority.NORMAL.name());
		statement.execute();
		ResultSet results = statement.getGeneratedKeys();
		results.first();
		createBattalion(results.getLong(1), BattalionType.INFANTRY);
	}

	public void deleteArmy(long id) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(DELETE_ARMY);
		statement.setLong(1, id);
		statement.execute();
	}
}
