package com.watersfall.clocgame.model.alignment;

import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.producible.ProducibleCategory;
import com.watersfall.clocgame.model.producible.Producibles;
import com.watersfall.clocgame.util.CollectionUtils;
import com.watersfall.clocgame.util.Time;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Alignment extends Updatable
{
	/**
	 * The Producibles that can be purchased from and sold to the alignments
	 */
	public static final List<Producibles> ALLOWED_PRODUCIBLES = CollectionUtils.immutableListOf(
			Producibles.SINGLE_SHOT,
			Producibles.BOLT_ACTION_MANUAL,
			Producibles.BOLT_ACTION_CLIP,
			Producibles.STRAIGHT_PULL,
			Producibles.SEMI_AUTO,
			Producibles.MACHINE_GUN,
			Producibles.ARTILLERY,
			Producibles.TANK,
			Producibles.BIPLANE_FIGHTERS,
			Producibles.TRIPLANE_FIGHTERS,
			Producibles.MONOPLANE_FIGHTERS,
			Producibles.ZEPPELINS,
			Producibles.BOMBERS
	);

	private @Getter Alignments alignment;
	private @Getter @Setter HashMap<Producibles, Long> equipment;
	public Alignment(ResultSet results) throws SQLException
	{
		super("alignments", 0);
		this.alignment = Alignments.valueOf(results.getString("id"));
	}

	public Long getProducible(Producibles producible)
	{
		return this.equipment.getOrDefault(producible, null);
	}

	public void setProducible(Producibles producible, long amount)
	{
		this.equipment.put(producible, amount);
	}

	private long getIdealProducibleStockpile(Producibles producible)
	{
		double modifier = 0.0;
		long amount = 0;
		int year = Time.getCurrentYear();
		if(year <= 1910)
		{
			modifier = 1.0;
		}
		else if(year <= 1913)
		{
			modifier = 1.5;
		}
		else if(year <= 1914)
		{
			modifier = 2.0;
		}
		else if(year <= 1918)
		{
			modifier = 3.0;
		}
		else
		{
			modifier = 2.0;
		}
		if(producible.getProducible().getCategory() == ProducibleCategory.INFANTRY_EQUIPMENT)
		{
			amount = 1000000;
		}
		else if(producible.getProducible().getCategory() == ProducibleCategory.FIGHTER_PLANE)
		{
			amount = 5000;
		}
		else if(producible.getProducible().getCategory() == ProducibleCategory.BOMBER_PLANE)
		{
			amount = 2000;
		}
		else if(producible.getProducible().getCategory() == ProducibleCategory.TANK)
		{
			amount = 1000;
		}
		else if(producible.getProducible().getCategory() == ProducibleCategory.ARTILLERY)
		{
			amount = 100000;
		}
		else
		{
			amount = 100000;
		}
		return (long)(amount * modifier);
	}

	private double getCostModifier(Producibles producible)
	{
		if(getProducible(producible) == null)
		{
			return Double.MAX_VALUE;
		}
		double result = 1.0 / ((double)getProducible(producible) / (double)getIdealProducibleStockpile(producible));
		return result;
	}

	public long getProducibleBuyCost(Producibles producible, Nation nation)
	{
		double modifier = getCostModifier(producible);
		if(modifier <= 10.0)
		{
			modifier = Math.min(modifier, 3.0);
		}
		return (long)(producible.getProducible().getProductionICCost() * getTransactionAmount(producible) * modifier * 1.1);
	}

	public long getProducibleSellCost(Producibles producible, Nation nation)
	{
		double modifier = getCostModifier(producible);
		modifier = Math.min(modifier, 2.0);
		return (long)(producible.getProducible().getProductionICCost() * getTransactionAmount(producible) * modifier * 0.9);
	}

	public int getTransactionAmount(Producibles producible)
	{
		switch(producible.getProducible().getCategory())
		{
			case INFANTRY_EQUIPMENT:
				return 100;
			case FIGHTER_PLANE:
			case RECON_PLANE:
			case ARTILLERY:
				return 10;
			case BOMBER_PLANE:
			case TANK:
				return 5;
			default:
				return 0;
		}
	}

	@Override
	public void update(Connection conn) throws SQLException
	{

	}
}