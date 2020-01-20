package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationDomestic extends Updatable
{
	public static final String TABLE_NAME = "cloc_domestic";
	private @Getter int land;
	private @Getter int government;
	private @Getter int approval;
	private @Getter int stability;
	private @Getter long population;
	private @Getter long manpowerLost;
	private @Getter int rebels;

	public NationDomestic(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.land = results.getInt("land");
		this.government = results.getInt("government");
		this.approval = results.getInt("approval");
		this.stability = results.getInt("stability");
		this.population = results.getLong("population");
		this.manpowerLost = results.getLong("lost_manpower");
		this.rebels = results.getInt("rebels");
	}

	public void setLand(int land)
	{
		if(land < 0)
			land = 0;
		else if(land > 2000000000)
			land = 2000000000;
		this.addField("land", land);
		this.land = land;
	}

	public void setGovernment(int government)
	{
		if(government < 0)
			government = 0;
		else if(government > 100)
			government = 100;
		this.addField("government", government);
		this.government = government;
	}

	public void setApproval(int approval)
	{
		if(approval < 0)
			approval = 0;
		else if(approval > 100)
			approval = 100;
		this.addField("approval", approval);
		this.approval = approval;
	}

	public void setStability(int stability)
	{
		if(stability < 0)
			stability = 0;
		else if(stability > 100)
			stability = 100;
		this.addField("stability", stability);
		this.stability = stability;
	}

	public void setPopulation(long population)
	{
		if(population < 10000)
			population = 10000;
		else if(population > 1000000000000000L)
			population = 1000000000000000L;
		this.addField("population", population);
		this.population = population;
	}

	public void setManpowerLost(long manpowerLost)
	{
		if(manpowerLost < 0)
			manpowerLost = 0;
		else if(manpowerLost > 1000000000000000L)
			manpowerLost = 1000000000000000L;
		this.addField("lost_manpower", manpowerLost);
		this.manpowerLost = manpowerLost;
	}

	public void setRebels(int rebels)
	{
		if(rebels < 0)
			rebels = 0;
		else if(rebels > 100)
			rebels = 100;
		this.addField("rebels", rebels);
		this.rebels = rebels;
	}
}
