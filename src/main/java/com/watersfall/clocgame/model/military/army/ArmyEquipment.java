package com.watersfall.clocgame.model.military.army;

import com.watersfall.clocgame.model.UpdatableLongId;
import com.watersfall.clocgame.model.producible.Producibles;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArmyEquipment extends UpdatableLongId
{
	private @Getter long id, owner;
	private @Getter int amount;
	private @Getter Producibles equipment;

	public ArmyEquipment(ResultSet results) throws SQLException
	{
		super("army_equipment", results.getLong("army_equipment.id"));
		this.id = results.getLong("army_equipment.id");
		this.owner = results.getLong("army_equipment.owner");
		this.amount = results.getInt("army_equipment.amount");
		this.equipment = Producibles.valueOf(results.getString("army_equipment.type"));
	}
}
