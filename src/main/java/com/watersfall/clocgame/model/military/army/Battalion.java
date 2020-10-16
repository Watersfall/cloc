package com.watersfall.clocgame.model.military.army;

import com.watersfall.clocgame.model.UpdatableLongId;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Battalion extends UpdatableLongId
{
	private @Getter long id, owner;
	private @Getter int size;
	private @Getter BattalionType type;
	private @Getter ArrayList<ArmyEquipment> equipment;

	public Battalion(ResultSet results) throws SQLException
	{
		super("army_battalions", results.getLong("army_battalions.id"));
		this.id = results.getLong("army_battalions.id");
		this.owner = results.getLong("army_battalions.owner");
		this.size = results.getInt("army_battalions.size");
		this.type = BattalionType.valueOf(results.getString("army_battalions.type"));
		this.equipment = new ArrayList<>();
	}

	public int getMaxSize()
	{
		return this.type.getSize();
	}

	public int getAttack()
	{
		return type.getAttack();
	}

	public int getDefense()
	{
		return type.getDefense();
	}

	public int getBreakthrough()
	{
		return type.getBreakthrough();
	}

	public double getWeight()
	{
		return 0D;
	}

	public double getStrength()
	{
		return 0D;
	}

	public HashMap<ProducibleCategory, Integer> getRequiredEquipment()
	{
		HashMap<ProducibleCategory, Integer> map = new HashMap<>();
		for(ProducibleCategory category : type.getEquipment().keySet())
		{
			int total = 0;
			for(ArmyEquipment armyEquipment : equipment)
			{
				if(armyEquipment.getEquipment().getProducible().getCategory() == category)
				{
					total += armyEquipment.getAmount();
				}
			}
			int amount = this.type.getEquipment().get(category) - total;
			map.put(category, amount);
		}
		return map;
	}

	public int getNeededManpower()
	{
		return getMaxSize() - size;
	}

	public boolean canBeDamaged()
	{
		return this.size > 0;
	}

	public void damage(double damage)
	{
		this.setField("size", this.size - (int)(this.getMaxSize() * (damage / 15)));
		this.size = (int)this.getField("size");
		if(this.size < 0)
		{
			this.size = 0;
			this.setField("size", 0);
		}
	}

	@Override
	public void update(Connection conn) throws SQLException
	{
		super.update(conn);
		for(ArmyEquipment equipment : equipment)
		{
			equipment.update(conn);
		}
	}
}
