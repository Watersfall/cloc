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
	private @Getter long manpowerLost;
	private @Getter int rebels;
	private @Getter int farmSubsidies;
	private @Getter int farmRegulations;
	private @Getter int farmTechnology;
	private @Getter int farmCollectivization;
	private @Getter int monthsInFamine;

	public NationDomestic(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.land = results.getInt("land");
		this.government = results.getInt("government");
		this.approval = results.getInt("approval");
		this.stability = results.getInt("stability");
		this.manpowerLost = results.getLong("lost_manpower");
		this.rebels = results.getInt("rebels");
		this.farmSubsidies = results.getInt("farm_subsidies");
		this.farmRegulations = results.getInt("farm_regulations");
		this.farmTechnology = results.getInt("farm_technology");
		this.farmCollectivization = results.getInt("farm_collectivization");
		this.monthsInFamine = results.getInt("months_in_famine");
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

	public void setFarmSubsidies(int farm)
	{
		if(farm < 0)
			farm = 0;
		else if(farm > 1000)
			farm = 1000;
		this.addField("farm_subsidies", farm);
		this.farmSubsidies = farm;
	}

	public void setFarmRegulations(int farm)
	{
		if(farm < 0)
			farm = 0;
		else if(farm > 1000)
			farm = 1000;
		this.addField("farm_regulations", farm);
		this.farmRegulations = farm;
	}

	public void setFarmTechnology(int farm)
	{
		if(farm < 0)
			farm = 0;
		else if(farm > 1000)
			farm = 1000;
		this.addField("farm_technology", farm);
		this.farmTechnology = farm;
	}

	public void setFarmCollectivization(int farm)
	{
		if(farm < 0)
			farm = 0;
		else if(farm > 1000)
			farm = 1000;
		this.addField("farm_collectivization", farm);
		this.farmCollectivization = farm;
	}

	public void setMonthsInFamine(int monthsInFamine)
	{
		if(monthsInFamine < 0)
			monthsInFamine = 0;
		this.addField("months_in_famine", monthsInFamine);
		this.monthsInFamine = monthsInFamine;
	}
}
