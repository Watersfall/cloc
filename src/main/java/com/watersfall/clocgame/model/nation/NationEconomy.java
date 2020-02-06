package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationEconomy extends Updatable
{
	public static final String TABLE_NAME = "cloc_economy";
	private @Getter int economic;
	private @Getter double gdp;
	private @Getter double growth;
	private @Getter double budget;
	private @Getter double iron;
	private @Getter double coal;
	private @Getter double oil;
	private @Getter double food;
	private @Getter double steel;
	private @Getter double nitrogen;
	private @Getter double research;

	public NationEconomy(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.economic = results.getInt("economic");
		this.gdp = results.getDouble("gdp");
		this.growth = results.getDouble("growth");
		this.budget = results.getDouble("budget");
		this.iron = results.getDouble("iron");
		this.coal = results.getDouble("coal");
		this.oil = results.getDouble("oil");
		this.food = results.getDouble("food");
		this.steel = results.getDouble("steel");
		this.nitrogen= results.getDouble("nitrogen");
		this.research = results.getDouble("research");
	}

	public void setEconomic(int economic)
	{
		if(economic < 0)
			economic = 0;
		else if(economic > 100)
			economic = 100;
		this.addField("economic", economic);
		this.economic = economic;
	}

	public void setGdp(double gdp)
	{
		if(gdp < 100)
			gdp = 100;
		else if(gdp >= 999999999999.00)
			gdp = 999999999999.00;
		this.addField("gdp", gdp);
		this.gdp = gdp;
	}

	public void setGrowth(double growth)
	{
		if(growth >= 999999999999.00)
			growth = 999999999999.00;
		this.addField("growth", growth);
		this.growth = growth;
	}

	public void setBudget(double budget)
	{
		if(budget >= 999999999999.00)
			budget = 999999999999.00;
		this.addField("budget", budget);
		this.budget = budget;
	}

	public void setIron(double iron)
	{
		if(iron < 0)
			iron = 0;
		else if(iron >= 999999999999.00)
			iron = 999999999999.00;
		this.addField("iron", iron);
		this.iron = iron;
	}

	public void setCoal(double coal)
	{
		if(coal < 0)
			coal = 0;
		else if(coal >= 999999999999.00)
			coal = 999999999999.00;
		this.addField("coal", coal);
		this.coal = coal;
	}

	public void setOil(double oil)
	{
		if(oil < 0)
			oil = 0;
		else if(oil >= 999999999999.00)
			oil = 999999999999.00;
		this.addField("oil", oil);
		this.oil = oil;
	}

	public void setFood(double food)
	{
		if(food < 0)
			food = 0;
		else if(food >= 999999999999.00)
			food = 999999999999.00;
		this.addField("food", food);
		this.food = food;
	}

	public void setSteel(double steel)
	{
		if(steel < 0)
			steel = 0;
		else if(steel >= 999999999999.00)
			steel = 999999999999.00;
		this.addField("steel", steel);
		this.steel = steel;
	}

	public void setNitrogen(double nitrogen)
	{
		if(nitrogen < 0)
			nitrogen = 0;
		else if(nitrogen >= 999999999999.00)
			nitrogen = 999999999999.00;
		this.addField("nitrogen", nitrogen);
		this.nitrogen = nitrogen;
	}

	public void setResearch(double research)
	{
		if(research < 0)
			research = 0;
		if(research >= 999999999999.00)
			research = 999999999999.00;
		this.addField("research", research);
		this.research = research;
	}
}