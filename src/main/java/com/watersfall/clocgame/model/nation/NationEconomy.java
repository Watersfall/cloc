package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationEconomy extends NationBase
{
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

	public NationEconomy(int economic, double gdp, double growth, double budget, double iron, double coal, double oil, double food, double steel, double nitrogen, double research)
	{
		this.economic = economic;
		this.gdp = gdp;
		this.growth = growth;
		this.budget = budget;
		this.iron = iron;
		this.coal = coal;
		this.oil = oil;
		this.food = food;
		this.steel = steel;
		this.nitrogen = nitrogen;
		this.research = research;
	}

	public NationEconomy(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.results = results;
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

	public NationEconomy(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT economic, gdp, growth, budget, iron, coal, oil, food, steel, nitrogen, research, id " + "FROM cloc_economy " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT economic, gdp, growth, budget, iron, coal, oil, food, steel, nitrogen, research, id " + "FROM cloc_economy " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.connection = connection;
			this.id = id;
			this.safe = safe;
			this.economic = results.getInt(1);
			this.gdp = results.getDouble(2);
			this.growth = results.getDouble(3);
			this.budget = results.getDouble(4);
			this.iron = results.getDouble(5);
			this.coal = results.getDouble(6);
			this.oil = results.getDouble(7);
			this.food = results.getDouble(8);
			this.steel = results.getDouble(9);
			this.nitrogen = results.getDouble(10);
			this.research = results.getDouble(11);
		}
	}

	public void setEconomic(int economic) throws SQLException
	{
		if(economic < 0)
		{
			economic = 0;
		}
		else if(economic > 100)
		{
			economic = 100;
		}
		this.economic = economic;
		results.updateInt("economic", economic);
	}

	public void setGdp(double gdp) throws SQLException
	{
		if(gdp < 0)
		{
			gdp = 0;
		}
		this.gdp = gdp;
		results.updateDouble("gdp", gdp);
	}

	public void setGrowth(double growth) throws SQLException
	{
		this.growth = growth;
		results.updateDouble("growth", growth);
	}

	public void setBudget(double budget) throws SQLException
	{
		this.budget = budget;
		results.updateDouble("budget", budget);
	}

	public void setIron(double iron) throws SQLException
	{
		if(iron < 0)
		{
			iron = 0;
		}
		this.iron = iron;
		results.updateDouble("iron", iron);
	}

	public void setCoal(double coal) throws SQLException
	{
		if(coal < 0)
		{
			coal = 0;
		}
		this.coal = coal;
		results.updateDouble("coal", coal);
	}

	public void setOil(double oil) throws SQLException
	{
		this.oil = oil;
		results.updateDouble("oil", oil);
	}

	public void setFood(double food) throws SQLException
	{
		if(food < 0)
		{
			food = 0;
		}
		this.food = food;
		results.updateDouble("food", food);
	}

	public void setSteel(double steel) throws SQLException
	{
		if(steel < 0)
		{
			steel = 0;
		}
		this.steel = steel;
		results.updateDouble("steel", steel);
	}

	public void setNitrogen(double nitrogen) throws SQLException
	{
		if(nitrogen < 0)
		{
			nitrogen = 0;
		}
		this.nitrogen = nitrogen;
		results.updateDouble("nitrogen", nitrogen);
	}

	public void setResearch(double research) throws SQLException
	{
		if(research < 0)
		{
			research = 0;
		}
		this.research = research;
		results.updateDouble("research", research);
	}
}
