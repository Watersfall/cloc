package net.watersfall.clocgame.model.nation;

import lombok.Getter;
import lombok.Setter;
import net.watersfall.clocgame.model.UpdatableLongId;
import net.watersfall.clocgame.model.factory.Factory;
import net.watersfall.clocgame.model.policies.Policy;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.util.Time;

import java.util.HashMap;

public class Production extends UpdatableLongId
{
	private static final String TABLE_NAME = "production";
	private @Getter int owner;
	private @Getter @Setter HashMap<Integer, Factory> factories;
	private @Getter Producibles production;
	private @Getter int progress;
	private HashMap<String, Double> requiredResources;
	private @Getter HashMap<String, Double> givenResources;

	public Production(int id, int owner, HashMap<Integer, Factory> factories, Producibles production, int progress, HashMap<String, Double> givenResources)
	{
		super(TABLE_NAME, id);
		this.owner = owner;
		this.factories = factories;
		this.production = production;
		this.progress = progress;
		this.givenResources = givenResources;
		this.requiredResources = new HashMap<>();
	}

	public void setProduction(Producibles production)
	{
		this.production = production;
		this.setField("production", production);
	}

	public void setProgress(int progress)
	{
		this.progress = progress;
		this.setField("progress", progress);
	}

	public void setGivenResources(HashMap<String, Double> map)
	{
		this.givenResources = map;
	}

	public HashMap<String, Double> getRequiredResources()
	{
		this.production.getProducible().getProductionResourceCost().forEach((k, v) -> {
			this.requiredResources.put(k, (double)(v * this.factories.size()));
		});
		return requiredResources;
	}

	public int getEfficiency()
	{
		if(factories.size() == 0)
			return 0;
		int total = 0;
		for(Factory factory : factories.values())
		{
			total += factory.getEfficiency();
		}
		return total / factories.size();
	}

	public double getIc(Policy policy)
	{
		boolean hasAllResources = true;
		for(HashMap.Entry<String, Double> entry : this.getRequiredResources().entrySet())
		{
			if(givenResources.get(entry.getKey()) < entry.getValue())
			{
				hasAllResources = false;
			}
		}
		Double ic = null;
		if(!hasAllResources)
		{
			ic = 0.0;
		}
		else
		{
			int total = 0;
			for(Factory factory : factories.values())
			{
				total += factory.getEfficiency();
			}
			if(policy == Policy.WAR_ECONOMY)
			{
				total *= 1.25;
			}
			ic = total / 10000.0;
		}
		return ic;
	}

	public String getProductionString(Policy policy)
	{
		if(this.getIc(policy) <= 0.0)
		{
			return"No&nbsp;progress";
		}
		double speed = this.getIc(policy) / this.production.getProducible().getProductionICCost();
		if(speed >= 1)
		{
			return String.format("%.2f&nbsp;per&nbsp;day", speed);
		}
		else if((speed = speed * 7) >= 1)
		{
			return String.format("%.2f&nbsp;per&nbsp;week", speed);
		}
		else
		{
			double timeRemaining = (this.production.getProducible().getProductionICCost() - (this.progress / 100.0)) / this.getIc(policy);
			if(timeRemaining > 7)
			{
				timeRemaining = timeRemaining / 7;
				return String.format("Completed&nbsp;in&nbsp;%.2f&nbsp;weeks", timeRemaining);
			}
			else
			{
				return String.format("Completed&nbsp;in&nbsp;%.2f&nbsp;days", timeRemaining);
			}
		}
	}

	public double getMonthlyProduction(Nation nation)
	{
		double ic = this.getIc(nation.getPolicy().getEconomy()) * Time.daysPerMonth[Time.currentMonth];
		return ic / this.production.getProducible().getProductionICCost();
	}
}
