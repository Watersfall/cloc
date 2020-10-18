package net.watersfall.clocgame.dao;

import net.watersfall.clocgame.model.alignment.Alignment;
import net.watersfall.clocgame.model.alignment.Alignments;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.util.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AlignmentDao extends Dao
{
	private static final String GET_ALIGNMENT = "SELECT * FROM alignments WHERE id=? ";
	private static final String GET_EQUIPMENT = "SELECT * FROM alignments_equipment WHERE alignment=? ";
	private static final String CREATE_TRANSACTION = "INSERT INTO alignments_transactions (alignment, nation, equipment, amount, month) VALUES (?,?,?,?,?) ";
	private static final String CREATE_PRODUCIBLE = "INSERT INTO alignments_equipment (alignment, equipment, amount) VALUES (?,?,?) ";
	private static final String UPDATE_PRODUCIBLE = "UPDATE alignments_equipment SET amount=amount+? WHERE alignment=? AND equipment=? ";

	public AlignmentDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public Alignment getAlignmentById(Alignments id) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_ALIGNMENT);
		statement.setString(1, id.name());
		ResultSet results = statement.executeQuery();
		results.first();
		Alignment alignment = new Alignment(results);
		PreparedStatement equipment = connection.prepareStatement(GET_EQUIPMENT);
		equipment.setString(1, id.name());
		ResultSet resultSet = equipment.executeQuery();
		HashMap<Producibles, Long> map = new HashMap<>();
		while(resultSet.next())
		{
			map.put(Producibles.valueOf(resultSet.getString("equipment")), resultSet.getLong("amount"));
		}
		alignment.setEquipment(map);
		return alignment;
	}

	public void createTransaction(String action, Producibles producible, Alignment alignment, Nation nation) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(CREATE_TRANSACTION);
		statement.setString(1, alignment.getAlignment().name());
		statement.setInt(2, nation.getId());
		statement.setString(3, producible.name());
		if(action.equalsIgnoreCase("buy"))
		{
			statement.setLong(4, -alignment.getTransactionAmount(producible));
		}
		else
		{
			statement.setLong(4, alignment.getTransactionAmount(producible));
		}
		statement.setLong(5, Time.currentMonth);
		statement.execute();
	}

	public void updateProducible(Producibles producible, Alignment alignment, long amount) throws SQLException
	{
		PreparedStatement statement;
		if(alignment.getProducible(producible) == null)
		{
			statement = connection.prepareStatement(CREATE_PRODUCIBLE);
			statement.setString(1, alignment.getAlignment().name());
			statement.setString(2, producible.name());
			statement.setLong(3, amount);
		}
		else
		{
			statement = connection.prepareStatement(UPDATE_PRODUCIBLE);
			statement.setLong(1, amount);
			statement.setString(2, alignment.getAlignment().name());
			statement.setString(3, producible.name());
		}
		statement.execute();
	}
}