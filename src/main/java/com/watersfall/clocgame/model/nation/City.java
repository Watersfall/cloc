package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.constants.Costs;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.ValueException;
import com.watersfall.clocgame.model.CityType;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class City
{
	private @Getter
	int id;
	private @Getter
	int owner;
	private @Getter
	boolean capital;
	private @Getter
	boolean coastal;
	private @Getter
	int railroads;
	private @Getter
	int ports;
	private @Getter
	int barracks;
	private @Getter
	int ironMines;
	private @Getter
	int coalMines;
	private @Getter
	int oilWells;
	private @Getter
	int industryCivilian;
	private @Getter
	int industryMilitary;
	private @Getter
	int industryNitrogen;
	private @Getter
	int universities;
	private @Getter
	String name;
	private @Getter
	CityType type;
	private @Getter
	ResultSet results;

	public City(Connection connection, int id, boolean safe) throws SQLException
	{
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT owner, capital, coastal, railroads, ports, barracks, iron_mines, coal_mines, oil_wells, civilian_industry, military_industry, nitrogen_industry, universities, name, type, id " + "FROM cloc_cities " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT owner, capital, coastal, railroads, ports, barracks, iron_mines, coal_mines, oil_wells, civilian_industry, military_industry, nitrogen_industry, universities, name, type, id " + "FROM cloc_cities " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new CityNotFoundException("No city with that id!");
		}

		this.owner = results.getInt(1);
		this.capital = results.getBoolean(2);
		this.coastal = results.getBoolean(3);
		this.railroads = results.getInt(4);
		this.ports = results.getInt(5);
		this.barracks = results.getInt(6);
		this.ironMines = results.getInt(7);
		this.coalMines = results.getInt(8);
		this.oilWells = results.getInt(9);
		this.industryCivilian = results.getInt(10);
		this.industryMilitary = results.getInt(11);
		this.industryNitrogen = results.getInt(12);
		this.universities = results.getInt(13);
		this.name = results.getString(14);
		this.type = CityType.getByName(results.getString(15));
		this.id = results.getInt(16);
	}

	public void setCapital(boolean capital) throws SQLException
	{
		this.capital = capital;
		results.updateBoolean(2, capital);
	}

	public void setCoastal(boolean coastal) throws SQLException
	{
		this.coastal = coastal;
		results.updateBoolean(3, coastal);
	}

	public void setRailroads(int railroads) throws SQLException
	{
		if(railroads < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else if(railroads > 10)
		{
			throw new ValueException("Can not be higher than 10!");
		}
		this.railroads = railroads;
		results.updateInt(4, railroads);
	}

	public void setPorts(int ports) throws SQLException
	{
		if(ports < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else if(ports > 10)
		{
			throw new ValueException("Can not be higher than 10!");
		}
		this.ports = ports;
		results.updateInt(5, ports);
	}

	public void setBarracks(int barracks) throws SQLException
	{
		if(barracks < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else if(barracks > 10)
		{
			throw new ValueException("Can not be higher than 10!");
		}
		this.barracks = barracks;
		results.updateInt(6, barracks);
	}

	public void setIronMines(int mines) throws SQLException
	{
		if(mines < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		this.ironMines = mines;
		results.updateInt(7, mines);
	}

	public void setCoalMines(int mines) throws SQLException
	{
		if(mines < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		this.coalMines = mines;
		results.updateInt(8, mines);
	}

	public void setOilWells(int wells) throws SQLException
	{
		if(wells < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		this.oilWells = wells;
		results.updateInt(9, wells);
	}

	public void setIndustryCivilian(int industry) throws SQLException
	{
		if(industry < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		this.industryCivilian = industry;
		results.updateInt(10, industry);
	}

	public void setIndustryMilitary(int industry) throws SQLException
	{
		if(industry < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		this.industryMilitary = industry;
		results.updateInt(11, industry);
	}

	public void setIndustryNitrogen(int industry) throws SQLException
	{
		if(industry < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		this.industryNitrogen = industry;
		results.updateInt(12, industry);
	}

	public void setUniversities(int universities) throws SQLException
	{
		if(universities < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		this.universities = universities;
		results.updateInt(13, universities);
	}

	public void setName(String name) throws SQLException
	{
		if(name == null)
		{
			throw new ValueException("Can not be null!");
		}
		else if(name.length() > 32767)
		{
			throw new ValueException("Can not be more than 32,767 characters!");
		}
		this.name = name;
		results.updateString(14, name);
	}

	public void setType(CityType type) throws SQLException
	{
		this.type = type;
		results.updateString(15, type.getName());
	}

	public void update() throws SQLException
	{
		results.updateRow();
	}

	public HashMap<String, Integer> getFactoryCost()
	{
		HashMap<String, Integer> map = new HashMap<>();
		map.put("coal", Costs.FACTORY_BASE_COAL + (Costs.FACTORY_MULT_COAL
				* (this.getIndustryCivilian() + this.getIndustryMilitary() + this.getIndustryNitrogen())));
		map.put("iron", Costs.FACTORY_BASE_IRON + (Costs.FACTORY_MULT_IRON
				* (this.getIndustryCivilian() + this.getIndustryMilitary() + this.getIndustryNitrogen())));
		map.put("steel", Costs.FACTORY_BASE_STEEL + (Costs.FACTORY_MULT_STEEL
				* (this.getIndustryCivilian() + this.getIndustryMilitary() + this.getIndustryNitrogen())));
		return map;
	}

	public HashMap<String, Integer> getUniversityCost()
	{
		HashMap<String, Integer> map = new HashMap<>();
		map.put("coal", Costs.UNIVERSITY_BASE_COAL + (Costs.UNIVERSITY_MULT_COAL
				* (this.getIndustryCivilian() + this.getIndustryMilitary() + this.getIndustryNitrogen())));
		map.put("iron", Costs.UNIVERSITY_BASE_IRON + (Costs.UNIVERSITY_MULT_IRON
				* (this.getIndustryCivilian() + this.getIndustryMilitary() + this.getIndustryNitrogen())));
		map.put("steel", Costs.UNIVERSITY_BASE_STEEL + (Costs.UNIVERSITY_MULT_STEEL
				* (this.getIndustryCivilian() + this.getIndustryMilitary() + this.getIndustryNitrogen())));
		return map;
	}

	public int getMineCost()
	{
		return Costs.MINE_BASE + (Costs.MINE_MULT * (this.getIronMines() + this.getCoalMines()));
	}

	public int getWellCost()
	{
		return Costs.OIL_BASE + (Costs.OIL_MULT * this.getOilWells());
	}

	public int getRailCost()
	{
		return 0;
	}

	public int getPortCost()
	{
		return 0;
	}

	public int getBarrackCost()
	{
		return 0;
	}
}