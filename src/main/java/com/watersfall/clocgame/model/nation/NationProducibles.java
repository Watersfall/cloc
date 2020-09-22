package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.UpdatableIntId;
import com.watersfall.clocgame.model.producible.Producibles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;

public class NationProducibles extends UpdatableIntId
{
	private EnumMap<Producibles, Integer> producibles;

	public NationProducibles(ResultSet results) throws SQLException
	{
		super("nation_producibles", results.getInt("login.id"));
		producibles = new EnumMap<>(Producibles.class);
		for(Producibles producible : Producibles.values())
		{
			producibles.put(producible, results.getInt(producible.name().toLowerCase()));
		}
	}

	public int getProducible(Producibles producible)
	{
		return producibles.get(producible);
	}

	public void setProducible(Producibles producible, int value)
	{
		producibles.put(producible, value);
		fields.put(producible.name().toLowerCase(), value);
	}
}
