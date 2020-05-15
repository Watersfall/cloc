package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.CitySize;
import com.watersfall.clocgame.model.CityType;
import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.policies.Policy;
import lombok.Getter;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class City extends Updatable
{
	public static final long EMPLOYMENT_MINE = 10000L;
	public static final long EMPLOYMENT_FACTORY = 100000L;
	public static final long EMPLOYMENT_UNIVERSITY = 50000L;
	public static final long LAND_MINE = 100L;
	public static final long LAND_FACTORY = 500L;
	public static final long LAND_UNIVERSITY = 750L;
	public static final String TABLE_NAME = "cloc_cities";

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
	private @Getter long population;
	private @Getter HashMap<Integer, Factory> militaryFactories;
	private @Getter String name;
	private @Getter CityType type;
	private @Getter int devastation;
	private @Getter int strikeLevel;
	private @Getter int strikeLength;

	public static City getCity(Connection conn, int id) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT cloc_cities.*, COALESCE(military_industry, 0) AS military_industry " +
				"FROM cloc_cities " +
				"LEFT JOIN ( " +
				"SELECT city_id, COUNT(id) " +
				"AS military_industry FROM factories GROUP BY city_id ) military_industry " +
				"ON military_industry.city_id=cloc_cities.id " +
				"WHERE cloc_cities.id=? FOR UPDATE");
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		return new City(id, results);
	}

	public City(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.owner = results.getInt("cloc_cities.owner");
		this.capital = results.getBoolean("cloc_cities.capital");
		this.coastal = results.getBoolean("cloc_cities.coastal");
		this.railroads = results.getInt("cloc_cities.railroads");
		this.ports = results.getInt("cloc_cities.ports");
		this.barracks = results.getInt("cloc_cities.barracks");
		this.ironMines = results.getInt("cloc_cities.iron_mines");
		this.coalMines = results.getInt("cloc_cities.coal_mines");
		this.oilWells = results.getInt("cloc_cities.oil_wells");
		this.industryCivilian = results.getInt("cloc_cities.civilian_industry");
		this.industryMilitary = results.getInt("military_industry");
		this.industryNitrogen = results.getInt("cloc_cities.nitrogen_industry");
		this.universities = results.getInt("cloc_cities.universities");
		this.name = results.getString("cloc_cities.name");
		this.type = CityType.valueOf(results.getString("cloc_cities.type"));
		this.devastation = results.getInt("cloc_cities.devastation");
		this.population = results.getLong("cloc_cities.population");
		this.id = results.getInt("cloc_cities.id");
		this.strikeLevel = results.getInt("cloc_cities.strike_level");
		this.strikeLength = results.getInt("cloc_cities.months_on_strike");
		this.results = results;
	}

	public void buildMilitaryIndustry(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("INSERT INTO factories (owner, city_id, production_id) VALUES (?,?,?)");
		statement.setInt(1, this.owner);
		statement.setInt(2, this.id);
		statement.setNull(3, Types.INTEGER);
		statement.execute();
	}

	public void closeMilitaryIndustry(Connection conn) throws SQLException
	{
		if(this.industryMilitary > 0)
		{
			PreparedStatement statement = conn.prepareStatement("DELETE FROM factories WHERE city_id=? ORDER BY production_id LIMIT 1");
			statement.setInt(1, this.id);
			statement.execute();
		}
	}

	public void setCapital(boolean capital)
	{
		this.addField("capital", capital);
		this.capital = capital;
	}

	public void setCoastal(boolean coastal)
	{
		this.addField("coastal", coastal);
		this.coastal = coastal;
	}

	public void setRailroads(int railroads)
	{
		if(railroads < 0)
			railroads = 0;
		else if(railroads > 10)
			railroads = 10;
		this.addField("railroads", railroads);
		this.railroads = railroads;
	}

	public void setPorts(int ports)
	{
		if(ports < 0)
			ports = 0;
		else if(ports > 10)
			ports = 10;
		this.addField("ports", ports);
		this.ports = ports;
	}

	public void setBarracks(int barracks)
	{
		if(barracks < 0)
			barracks = 0;
		else if(barracks > 10)
			barracks = 10;
		this.addField("barracks", barracks);
		this.barracks = barracks;
	}

	public void setIronMines(int ironMines)
	{
		if(ironMines < 0)
			ironMines = 0;
		else if(ironMines > 2000000000)
			ironMines = 2000000000;
		this.addField("iron_mines", ironMines);
		this.ironMines = ironMines;
	}

	public void setCoalMines(int coalMines)
	{
		if(coalMines < 0)
			coalMines = 0;
		else if(coalMines > 2000000000)
			coalMines = 2000000000;
		this.addField("coal_mines", coalMines);
		this.coalMines = coalMines;
	}

	public void setOilWells(int oilWells)
	{
		if(oilWells < 0)
			oilWells = 0;
		else if(oilWells > 2000000000)
			oilWells = 2000000000;
		this.addField("oil_wells", oilWells);
		this.oilWells = oilWells;
	}

	public void setIndustryCivilian(int industryCivilian)
	{
		if(industryCivilian < 0)
			industryCivilian = 0;
		else if(industryCivilian > 2000000000)
			industryCivilian = 2000000000;
		this.addField("civilian_industry", industryCivilian);
		this.industryCivilian = industryCivilian;
	}

	public void setIndustryNitrogen(int industryNitrogen)
	{
		if(industryNitrogen < 0)
			industryNitrogen = 0;
		else if(industryNitrogen > 2000000000)
			industryNitrogen = 2000000000;
		this.addField("nitrogen_industry", industryNitrogen);
		this.industryNitrogen = industryNitrogen;
	}

	public void setUniversities(int universities)
	{
		if(universities < 0)
			universities = 0;
		else if(universities > 2000000000)
			universities = 2000000000;
		this.addField("universities", universities);
		this.universities = universities;
	}

	public void setName(String name)
	{
		this.addField("name", name);
		this.name = name;
	}

	public void setType(CityType type)
	{
		this.addField("type", type);
		this.type = type;
	}

	public void setDevastation(int devastation)
	{
		if(devastation < 0)
			devastation = 0;
		else if(devastation > 100)
			devastation = 100;
		this.addField("devastation", devastation);
		this.devastation = devastation;
	}

	public void setPopulation(long population)
	{
		if(population < 100)
			population = 100;
		else if(population > 1000000000000000L)
			population = 1000000000000000L;
		this.addField("population", population);
		this.population = population;
	}

	public void setStrikeLevel(int strikeLevel)
	{
		if(strikeLevel < 0)
			strikeLevel = 0;
		else if(strikeLevel > 10)
			strikeLevel = 10;
		this.addField("strike_level", strikeLevel);
		this.strikeLevel = strikeLevel;
	}

	public void setStrikeLength(int strikeLength)
	{
		if(strikeLength < 0)
			strikeLength = 0;
		this.addField("months_on_strike", strikeLength);
		this.strikeLength = strikeLength;
	}

	@Override
	public Object getByName(String name) throws SQLException
	{
		if(this.fields.get(name) != null)
		{
			return this.fields.get(name);
		}
		else
		{
			switch(name)
			{
				case "oil_wells":
					return this.oilWells;
				case "iron_mines":
					return this.ironMines;
				case "coal_mines":
					return this.coalMines;
				case "civilian_industry":
					return this.industryCivilian;
				case "nitrogen_industry":
					return this.industryNitrogen;
				case "military_industry":
					return this.industryMilitary;
				case "name":
					return this.name;
				case "ports":
					return this.ports;
				case "railroads":
					return this.railroads;
				case "barracks":
					return this.barracks;
				case "population":
					return this.population;
				case "universities":
					return this.universities;
				default:
					return 0;
			}
		}
	}

	public CitySize getSize()
	{
		if(this.population > CitySize.ECUMENOPOLIS.getMinimum())
			return CitySize.ECUMENOPOLIS;
		else if(this.population > CitySize.MEGALOPOLIS.getMinimum())
			return CitySize.MEGALOPOLIS;
		else if(this.population > CitySize.METROPOLIS.getMinimum())
			return CitySize.METROPOLIS;
		else if(this.population > CitySize.LARGE_CITY.getMinimum())
			return CitySize.LARGE_CITY;
		else if(this.population > CitySize.CITY.getMinimum())
			return CitySize.CITY;
		else if(this.population > CitySize.LARGE_TOWN.getMinimum())
			return CitySize.LARGE_TOWN;
		else if(this.population > CitySize.TOWN.getMinimum())
			return CitySize.TOWN;
		else if(this.population > CitySize.VILLAGE.getMinimum())
			return CitySize.VILLAGE;
		else
			return CitySize.BRUH_WHAT;
	}

	public int getBuildSlots()
	{
		return (int)(this.getSize().getBuildSlots() * (1.0 + (this.railroads / 20.0)));
	}

	public int getUsedSlots()
	{
		return this.ironMines + this.coalMines + this.oilWells + this.industryCivilian + this.industryMilitary
				+ this.industryNitrogen + this.universities;
	}

	public int getFactoryCount()
	{
		return this.industryCivilian + this.industryMilitary + this.industryNitrogen + this.universities;
	}

	public int getMineCount()
	{
		return  this.coalMines + this.ironMines + this.oilWells;
	}

	public int getFreeSlots()
	{
		return getBuildSlots() - getUsedSlots();
	}

	public LinkedHashMap<String, Double> getPopulationGrowth(Nation nation)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double base = 0.5;
		double foodPolicy = 0;
		double manpowerPolicy = 0;
		double economyPolicy = 0;
		double size = 0;
		double famine = nation.getFamineLevel();
		if(nation.getPolicy().getFood() == Policy.FREE_FOOD)
		{
			foodPolicy = 0.15;
		}
		else if(nation.getPolicy().getFood() == Policy.RATIONING_FOOD)
		{
			foodPolicy = -0.25;
		}
		if(nation.getPolicy().getManpower() == Policy.DISARMED_MANPOWER)
		{
			manpowerPolicy = 0.25;
		}
		else if(nation.getPolicy().getManpower() == Policy.VOLUNTEER_MANPOWER)
		{
			manpowerPolicy = 0.1;
		}
		else if(nation.getPolicy().getManpower() == Policy.MANDATORY_MANPOWER)
		{
			manpowerPolicy = -0.1;
		}
		else if(nation.getPolicy().getManpower() == Policy.SCRAPING_THE_BARREL_MANPOWER)
		{
			manpowerPolicy = -0.25;
		}
		if(nation.getPolicy().getEconomy() == Policy.CIVILIAN_ECONOMY)
		{
			economyPolicy = 0.15;
		}
		else if(nation.getPolicy().getEconomy() == Policy.WAR_ECONOMY)
		{
			economyPolicy = -0.15;
		}
		size = this.getSize().getPopGrowthBonus();
		double net = base + foodPolicy + manpowerPolicy + economyPolicy + size;
		if(famine < 0)
		{
			famine = Math.min(-net, famine);
		}
		net += famine;
		map.put("population.base", base);
		map.put("population.foodPolicy", foodPolicy);
		map.put("population.manpowerPolicy", manpowerPolicy);
		map.put("population.economy_policy", economyPolicy);
		map.put("population.size", size);
		map.put("population.famine", famine);
		map.put("population.net", net);
		return map;
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
		map.put("coal", (int)(50 * (1.0 + this.getFactoryCount() / 5.0)));
		map.put("iron", (int)(50 * (1.0 + this.getFactoryCount() / 5.0)));
		map.put("steel", (int)(5 * (1.0 + this.getFactoryCount() / 5.0)));
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
		map.put("coal", (int)(100 * (1.0 + this.getFactoryCount() / 5.0)));
		map.put("iron", (int)(100 * (1.0 + this.getFactoryCount() / 5.0)));
		map.put("steel", (int)(10 * (1.0 + this.getFactoryCount() / 5.0)));
		return map;
	}

	/**
	 * Gets the Iron/Coal mine cost for this city
	 * @return The mine cost
	 */
	public int getMineCost()
	{
		return (int)(1000 * (1.0 + this.getMineCount() / 5.0));
	}

	/**
	 * Gets the Oil Well cost for this city
	 * @return The well cost
	 */
	public int getWellCost()
	{
		return (int)(1500 * (1.0 + this.getMineCount() / 5.0));
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

	public long getTotalEmployment()
	{
		long employment = 0;
		employment += (long)(this.coalMines + this.ironMines + this.oilWells) * City.EMPLOYMENT_MINE;
		employment += (long)(this.industryCivilian + this.industryMilitary + this.industryNitrogen) * City.EMPLOYMENT_FACTORY;
		employment += (long)(this.universities) * City.EMPLOYMENT_UNIVERSITY;
		return employment;
	}

	public double getStrikeModifier()
	{
		if(this.strikeLength > 0)
			return -strikeLevel / 10.0;
		else
			return 0;
	}


	private LinkedHashMap<String, Double> doMineOutput(LinkedHashMap<String, Double> map, double mines)
	{
		double bonus = this.getRailroads() * 0.1 * mines;
		double total = mines + bonus;
		double devastation2 = -total * (devastation / 100.0);
		total = total * (1 - (devastation / 100.0));
		double civilian = -this.getIndustryCivilian();
		double military = -this.getIndustryMilitary();
		double nitrogen = -this.getIndustryNitrogen();
		double strike = total * this.getStrikeModifier();
		double net = total + civilian + military + nitrogen + strike;
		map.put("resource.mines", mines);
		map.put("resource.infrastructure", bonus);
		map.put("resource.devastation", devastation2);
		map.put("resource.strike", strike);
		map.put("resource.factoryUpkeep", civilian + military + nitrogen);
		map.put("resource.net", net);
		map.put("resource.total", total);
		return map;
	}

	private LinkedHashMap<String, Double> doFactoryOutput(LinkedHashMap<String, Double> map, double factories)
	{
		double bonus = this.getRailroads() * 0.1 * factories;
		double total = factories + bonus;
		double devastation2 = -total * (devastation / 100.0);
		total = total * (1 - (devastation / 100.0));
		double strike = total * this.getStrikeModifier();
		double net = total + strike;
		map.put("resource.factoryProduction", factories);
		map.put("resource.infrastructure", bonus);
		map.put("resource.devastation", devastation2);
		map.put("resource.strike", strike);
		map.put("resource.net", net);
		map.put("resource.total", total);
		return map;
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
		double mines = this.getCoalMines() * 10;
		return doMineOutput(map, mines);
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
		double mines = this.getIronMines() * 10;
		return doMineOutput(map, mines);
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
		double wells = this.getOilWells() * 10;
		return doMineOutput(map, wells);
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
		double factories = this.getIndustryCivilian() * 5;
		return doFactoryOutput(map, factories);
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
		double factories = this.getIndustryNitrogen() * 5;
		return doFactoryOutput(map, factories);
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
		double universities = this.getUniversities();
		double total = universities + standard;
		double devastation2 = -total * (devastation / 100.0);
		total = total * (1 - (devastation / 100.0));
		double strike = total * this.getStrikeModifier();
		double net = total + strike;
		map.put("resource.default", standard);
		map.put("resource.universities", universities);
		map.put("resource.devastation", devastation2);
		map.put("resource.strike", strike);
		map.put("resource.net", net);
		map.put("resource.total", total);
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
	public LinkedHashMap<String, Long> getLandUsage()
	{
		LinkedHashMap<String, Long> map = new LinkedHashMap<>();
		map.put("land.mines", City.LAND_MINE * (this.ironMines + this.coalMines + this.oilWells));
		map.put("land.factories", City.LAND_FACTORY * (this.industryNitrogen + this.industryMilitary + this.industryCivilian));
		map.put("land.universities", City.LAND_UNIVERSITY * (this.universities));
		return map;
	}
}
