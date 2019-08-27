package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.ValueException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationEconomy extends NationBase
{
	private @Getter
	int economic;
	private @Getter
	double gdp;
	private @Getter
	double growth;
	private @Getter
	double budget;
	private @Getter
	double iron;
	private @Getter
	double coal;
	private @Getter
	double oil;
	private @Getter
	double food;
	private @Getter
	double steel;
	private @Getter
	double nitrogen;
	private @Getter
	double research;

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
		results.updateInt(1, economic);
	}

	public void setGdp(double gdp) throws SQLException
	{
		if(gdp < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(2, gdp);
		}
	}

	public void setGrowth(double growth) throws SQLException
	{
		results.updateDouble(3, growth);
	}

	public void setBudget(double budget) throws SQLException
	{
		results.updateDouble(4, budget);
	}

	public void setIron(double iron) throws SQLException
	{
		if(iron < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(5, iron);
		}
	}

	public void setCoal(double coal) throws SQLException
	{
		if(coal < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(6, coal);
		}
	}

	public void setOil(double oil) throws SQLException
	{
		if(oil < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(7, oil);
		}
	}

	public void setFood(double food) throws SQLException
	{
		if(food < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(8, food);
		}
	}

	public void setSteel(double steel) throws SQLException
	{
		if(steel < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(9, steel);
		}
	}

	public void setNitrogen(double nitrogen) throws SQLException
	{
		if(nitrogen < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(10, nitrogen);
		}
	}

	public void setResearch(double research) throws SQLException
	{
		if(research < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateDouble(11, research);
		}
	}
}
