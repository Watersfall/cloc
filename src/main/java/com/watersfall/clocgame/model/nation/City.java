package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.constants.Costs;
import com.watersfall.clocgame.constants.ProductionConstants;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.model.CityType;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class City
{
	public static final int EMPLOYMENT_MINE = 10000;
	public static final int EMPLOYMENT_FACTORY = 100000;
	public static final int EMPLOYMENT_UNIVERSITY = 50000;
	public static final int LAND_MINE = 100;
	public static final int LAND_FACTORY = 500;
	public static final int LAND_UNIVERSITY = 750;

	private @Getter int id;
	private @Getter int owner;
	private @Getter boolean capital;
	private @Getter boolean coastal;
	private @Getter int railroads;
	private @Getter int ports;
	private @Getter int barracks;
	private @Getter int ironMines;
	private @Getter int coalMines;
	private @Getter int oilWells;
	private @Getter int industryCivilian;
	private @Getter int industryMilitary;
	private @Getter int industryNitrogen;
	private @Getter int universities;
	private @Getter String name;
	private @Getter CityType type;
	private @Getter ResultSet results;

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
			railroads = 0;
		}
		else if(railroads > 10)
		{
			railroads = 10;
		}
		this.railroads = railroads;
		results.updateInt(4, railroads);
	}

	public void setPorts(int ports) throws SQLException
	{
		if(ports < 0)
		{
			ports = 0;
		}
		else if(ports > 10)
		{
			ports = 10;
		}
		this.ports = ports;
		results.updateInt(5, ports);
	}

	public void setBarracks(int barracks) throws SQLException
	{
		if(barracks < 0)
		{
			barracks = 0;
		}
		else if(barracks > 10)
		{
			barracks = 10;
		}
		this.barracks = barracks;
		results.updateInt(6, barracks);
	}

	public void setIronMines(int mines) throws SQLException
	{
		if(mines < 0)
		{
			mines = 0;
		}
		this.ironMines = mines;
		results.updateInt(7, mines);
	}

	public void setCoalMines(int mines) throws SQLException
	{
		if(mines < 0)
		{
			mines = 0;
		}
		this.coalMines = mines;
		results.updateInt(8, mines);
	}

	public void setOilWells(int wells) throws SQLException
	{
		if(wells < 0)
		{
			wells = 0;
		}
		this.oilWells = wells;
		results.updateInt(9, wells);
	}

	public void setIndustryCivilian(int industry) throws SQLException
	{
		if(industry < 0)
		{
			industry = 0;
		}
		this.industryCivilian = industry;
		results.updateInt(10, industry);
	}

	public void setIndustryMilitary(int industry) throws SQLException
	{
		if(industry < 0)
		{
			industry = 0;
		}
		this.industryMilitary = industry;
		results.updateInt(11, industry);
	}

	public void setIndustryNitrogen(int industry) throws SQLException
	{
		if(industry < 0)
		{
			industry = 0;
		}
		this.industryNitrogen = industry;
		results.updateInt(12, industry);
	}

	public void setUniversities(int universities) throws SQLException
	{
		if(universities < 0)
		{
			universities = 0;
		}
		this.universities = universities;
		results.updateInt(13, universities);
	}

	public void setName(String name) throws SQLException
	{
		if(name == null)
		{
			throw new NullPointerException();
		}
		else if(name.length() > 64)
		{
			throw new IllegalArgumentException("Can not be more than 64 characters!");
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

	/**
	 * Generates a HashMap of the factory cost in this city with values:
	 * <ul>
	 *     <li>coal</li>
	 *     <li>iron</li>
	 *     <li>steel</li>
	 * </ul>
	 * @return A HashMap of the costs
	 */
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

	/**
	 * Generates a HashMap of the university cost in this city with values:
	 * <ul>
	 *     <li>coal</li>
	 *     <li>iron</li>
	 *     <li>steel</li>
	 * </ul>
	 * @return A HashMap of the costs
	 */
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

	/**
	 * Gets the Iron/Coal mine cost for this city
	 * @return The mine cost
	 */
	public int getMineCost()
	{
		return Costs.MINE_BASE + (Costs.MINE_MULT * (this.getIronMines() + this.getCoalMines()));
	}

	/**
	 * Gets the Oil Well cost for this city
	 * @return The well cost
	 */
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

	public int getTotalEmployment()
	{
		int employment = 0;
		employment += (this.coalMines + this.ironMines + this.oilWells) * City.EMPLOYMENT_MINE;
		employment += (this.industryCivilian + this.industryMilitary + this.industryNitrogen) * City.EMPLOYMENT_FACTORY;
		employment += (this.universities) * City.EMPLOYMENT_UNIVERSITY;
		return employment;
	}

	/**
	 * Returns a HashMap with the coal production of this city with keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>mines</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A HashMap of coal production in this city
	 */
	public LinkedHashMap<String, Double> getCoalProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double mines = this.getCoalMines() * ProductionConstants.MINE_PER_WEEK;
		double bonus = this.getRailroads() * ProductionConstants.RAILROAD_BOOST * this.getCoalMines();
		double total = mines + bonus;
		double civilian = -this.getIndustryCivilian() * ProductionConstants.FACTORY_COAL_PER_WEEK;
		double military = -this.getIndustryMilitary() * ProductionConstants.FACTORY_COAL_PER_WEEK;
		double nitrogen = -this.getIndustryMilitary() * ProductionConstants.FACTORY_COAL_PER_WEEK;
		double net = total + civilian + military + nitrogen;
		map.put("total", total);
		map.put("mines", mines);
		map.put("infrastructure", bonus);
		map.put("civilian factory demands", civilian);
		map.put("military factory demands", military);
		map.put("nitrogen plant demands", nitrogen);
		map.put("net", net);
		return map;
	}

	/**
	 * Returns a HashMap with the iron production of this city with keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>mines</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A HashMap of iron production in this city
	 */
	public LinkedHashMap<String, Double> getIronProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double mines = this.getIronMines() * ProductionConstants.MINE_PER_WEEK;
		double bonus = this.getRailroads() * ProductionConstants.RAILROAD_BOOST * this.getIronMines();
		double total = mines + bonus;
		double civilian = -this.getIndustryCivilian() * ProductionConstants.FACTORY_IRON_PER_WEEK;
		double military = -this.getIndustryMilitary() * ProductionConstants.FACTORY_IRON_PER_WEEK;
		double nitrogen = -this.getIndustryMilitary() * ProductionConstants.FACTORY_IRON_PER_WEEK;
		double net = total + civilian + military + nitrogen;
		map.put("total", total);
		map.put("mines", mines);
		map.put("infrastructure", bonus);
		map.put("civilian factory demands", civilian);
		map.put("military factory demands", military);
		map.put("nitrogen plant demands", nitrogen);
		map.put("net", net);
		return map;
	}

	/**
	 * Returns a LinkedHashMap with the oil production of this city with keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>wells</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap of oil production in this city
	 */
	public LinkedHashMap<String, Double> getOilProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double wells = this.getOilWells() * ProductionConstants.MINE_PER_WEEK;
		double bonus = this.getRailroads() * ProductionConstants.RAILROAD_BOOST * this.getOilWells();
		double total = wells + bonus;
		double civilian = -this.getIndustryCivilian() * ProductionConstants.FACTORY_OIL_PER_WEEK;
		double military = -this.getIndustryMilitary() * ProductionConstants.FACTORY_OIL_PER_WEEK;
		double nitrogen = -this.getIndustryMilitary() * ProductionConstants.FACTORY_OIL_PER_WEEK;
		double net = total + civilian + military + nitrogen;
		map.put("total", total);
		map.put("wells", wells);
		map.put("infrastructure", bonus);
		map.put("civilian factory demands", civilian);
		map.put("military factory demands", military);
		map.put("nitrogen plant demands", nitrogen);
		map.put("net", net);
		return map;
	}

	/**
	 * Returns a LinkedHashMap with the steel production of this city with keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap of steel production in this city
	 */
	public LinkedHashMap<String, Double> getSteelProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double factories = this.getIndustryCivilian() * ProductionConstants.STEEL_PER_WEEK;
		double bonus = this.getRailroads() * ProductionConstants.RAILROAD_BOOST * this.getIndustryCivilian();
		double total = factories + bonus;
		double net = total;
		map.put("total", total);
		map.put("factories", factories);
		map.put("infrastructure", bonus);
		map.put("net", net);
		return map;
	}

	/**
	 * Returns a LinkedHashMap with the nitrogen production of this city with keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap of nitrogen production in this city
	 */
	public LinkedHashMap<String, Double> getNitrogenProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double factories = this.getIndustryNitrogen() * ProductionConstants.NITROGEN_PER_WEEK;
		double bonus = this.getRailroads() * ProductionConstants.RAILROAD_BOOST * this.getIndustryNitrogen();
		double total = factories + bonus;
		double net = total;
		map.put("total", total);
		map.put("factories", factories);
		map.put("infrastructure", bonus);
		map.put("net", net);
		return map;
	}

	/**
	 * Returns a LinkedHashMap with the weapons production of this city with keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap of weapons production in this city
	 */
	public LinkedHashMap<String, Double> getWeaponsProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double factories = this.getIndustryMilitary() * ProductionConstants.WEAPONS_PER_WEEK;
		double bonus = this.getRailroads() * ProductionConstants.RAILROAD_BOOST * this.getIndustryMilitary();
		double total = factories + bonus;
		double net = total;
		map.put("total", total);
		map.put("factories", factories);
		map.put("infrastructure", bonus);
		map.put("net", net);
		return map;
	}

	/**
	 * Returns a LinkedHashMap with the research production of this city with keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>default</li>
	 *     <li>universities</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap of research production in this city
	 */
	public LinkedHashMap<String, Double> getResearchProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		//I can't name it default...
		//Damn reserved keywords >:(
		double standard = 2;
		double universities = this.getUniversities() * ProductionConstants.RESEARCH_PER_WEEK;
		double total = universities + standard;
		double net = total;
		map.put("default", standard);
		map.put("total", total);
		map.put("universities", universities);
		map.put("net", net);
		return map;
	}

	/**
	 * Returns a LinkedHashMap with the land usage of this city with keys:
	 * <ul>
	 *     <li>mines</li>
	 *     <li>factories</li>
	 *     <li>universities</li>
	 * </ul>
	 * @return A LinkedHashMap of land usage in this city
	 */
	public LinkedHashMap<String, Integer> getLandUsage()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("mines", City.LAND_MINE * (this.ironMines + this.coalMines + this.oilWells));
		map.put("factories", City.LAND_FACTORY * (this.industryNitrogen + this.industryMilitary + this.industryCivilian));
		map.put("universities", City.LAND_UNIVERSITY * (this.universities));
		return map;
	}
}
