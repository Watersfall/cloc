package com.watersfall.clocgame.model.city;

import com.watersfall.clocgame.model.TextKey;
import com.watersfall.clocgame.model.UpdatableLongId;
import com.watersfall.clocgame.model.modifier.Modifier;
import com.watersfall.clocgame.model.modifier.Modifiers;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.policies.Policy;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class City extends UpdatableLongId
{
	public static final long EMPLOYMENT_MINE = 10000L;
	public static final long EMPLOYMENT_FACTORY = 100000L;
	public static final long EMPLOYMENT_UNIVERSITY = 50000L;
	public static final long LAND_MINE = 100L;
	public static final long LAND_FACTORY = 500L;
	public static final long LAND_UNIVERSITY = 750L;
	public static final String TABLE_NAME = "cities";

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
	private @Getter String name;
	private @Getter CityType type;
	private @Getter int devastation;
	private @Getter @Setter Nation nation;
	private @Getter CityGarrisonLevel garrisonLevel;

	public City(ResultSet results) throws SQLException
	{
		super(TABLE_NAME, results.getInt("cities.id"));
		this.owner = results.getInt("cities.owner");
		this.capital = results.getBoolean("cities.capital");
		this.coastal = results.getBoolean("cities.coastal");
		this.railroads = results.getInt("cities.railroads");
		this.ports = results.getInt("cities.ports");
		this.barracks = results.getInt("cities.barracks");
		this.ironMines = results.getInt("cities.iron_mines");
		this.coalMines = results.getInt("cities.coal_mines");
		this.oilWells = results.getInt("cities.oil_wells");
		this.industryCivilian = results.getInt("cities.civilian_industry");
		this.industryMilitary = results.getInt("military_industry");
		this.industryNitrogen = results.getInt("cities.nitrogen_industry");
		this.universities = results.getInt("cities.universities");
		this.name = results.getString("cities.name");
		this.type = CityType.valueOf(results.getString("cities.type"));
		this.devastation = results.getInt("cities.devastation");
		this.population = results.getLong("cities.population");
		this.garrisonLevel = CityGarrisonLevel.valueOf(results.getString("cities.garrison_level"));
		this.id = results.getInt("cities.id");
	}

	public void setCapital(boolean capital)
	{
		this.setField("capital", capital);
		this.capital = capital;
	}

	public void setCoastal(boolean coastal)
	{
		this.setField("coastal", coastal);
		this.coastal = coastal;
	}

	public void setRailroads(int railroads)
	{
		if(railroads < 0)
			railroads = 0;
		else if(railroads > 10)
			railroads = 10;
		this.setField("railroads", railroads);
		this.railroads = railroads;
	}

	public void setPorts(int ports)
	{
		if(ports < 0)
			ports = 0;
		else if(ports > 10)
			ports = 10;
		this.setField("ports", ports);
		this.ports = ports;
	}

	public void setBarracks(int barracks)
	{
		if(barracks < 0)
			barracks = 0;
		else if(barracks > 10)
			barracks = 10;
		this.setField("barracks", barracks);
		this.barracks = barracks;
	}

	public void setIronMines(int ironMines)
	{
		if(ironMines < 0)
			ironMines = 0;
		else if(ironMines > 2000000000)
			ironMines = 2000000000;
		this.setField("iron_mines", ironMines);
		this.ironMines = ironMines;
	}

	public void setCoalMines(int coalMines)
	{
		if(coalMines < 0)
			coalMines = 0;
		else if(coalMines > 2000000000)
			coalMines = 2000000000;
		this.setField("coal_mines", coalMines);
		this.coalMines = coalMines;
	}

	public void setOilWells(int oilWells)
	{
		if(oilWells < 0)
			oilWells = 0;
		else if(oilWells > 2000000000)
			oilWells = 2000000000;
		this.setField("oil_wells", oilWells);
		this.oilWells = oilWells;
	}

	public void setIndustryCivilian(int industryCivilian)
	{
		if(industryCivilian < 0)
			industryCivilian = 0;
		else if(industryCivilian > 2000000000)
			industryCivilian = 2000000000;
		this.setField("civilian_industry", industryCivilian);
		this.industryCivilian = industryCivilian;
	}

	public void setIndustryNitrogen(int industryNitrogen)
	{
		if(industryNitrogen < 0)
			industryNitrogen = 0;
		else if(industryNitrogen > 2000000000)
			industryNitrogen = 2000000000;
		this.setField("nitrogen_industry", industryNitrogen);
		this.industryNitrogen = industryNitrogen;
	}

	public void setUniversities(int universities)
	{
		if(universities < 0)
			universities = 0;
		else if(universities > 2000000000)
			universities = 2000000000;
		this.setField("universities", universities);
		this.universities = universities;
	}

	public void setName(String name)
	{
		this.setField("name", name);
		this.name = name;
	}

	public void setType(CityType type)
	{
		this.setField("type", type);
		this.type = type;
	}

	public void setDevastation(int devastation)
	{
		if(devastation < 0)
			devastation = 0;
		else if(devastation > 100)
			devastation = 100;
		this.setField("devastation", devastation);
		this.devastation = devastation;
	}

	public void setPopulation(long population)
	{
		if(population < 100)
			population = 100;
		else if(population > 1000000000000000L)
			population = 1000000000000000L;
		this.setField("population", population);
		this.population = population;
	}

	public void setGarrisonLevel(CityGarrisonLevel level)
	{
		this.garrisonLevel = level;
		this.setField("garrison_level", level);
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

	public LinkedHashMap<TextKey, Double> getPopulationGrowth(Nation nation)
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
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
		map.put(TextKey.Population.BASE, base);
		map.put(TextKey.Population.FOOD_POLICY, foodPolicy);
		map.put(TextKey.Population.MANPOWER_POLICY, manpowerPolicy);
		map.put(TextKey.Population.ECONOMY_POLICY, economyPolicy);
		map.put(TextKey.Population.SIZE, size);
		map.put(TextKey.Population.FAMINE, famine);
		map.put(TextKey.Population.NET, net);
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

	public boolean hasStrike()
	{
		for(Modifier modifier : nation.getModifiers())
		{
			if(modifier.getCity() == this.id)
			{
				Modifiers type = modifier.getType();
				if(type == Modifiers.STRIKE_SENT_ARMY || type == Modifiers.STRIKE_GAVE_IN || type == Modifiers.STRIKE_IGNORED)
				{
					return true;
				}
			}
		}
		return false;
	}

	public Modifiers getStrikeType()
	{
		for(Modifier modifier : nation.getModifiers())
		{
			if(modifier.getCity() == this.id)
			{
				Modifiers type = modifier.getType();
				if(type == Modifiers.STRIKE_SENT_ARMY || type == Modifiers.STRIKE_GAVE_IN || type == Modifiers.STRIKE_IGNORED)
				{
					return type;
				}
			}
		}
		return null;
	}

	public double getStrikeModifier()
	{
		if(this.hasStrike())
			return getStrikeType().getEffects().get(TextKey.Modifiers.RESOURCE_PRODUCTION) / 100.0;
		else
			return 0;
	}


	private LinkedHashMap<TextKey, Double> doMineOutput(LinkedHashMap<TextKey, Double> map, double mines)
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
		map.put(TextKey.Resource.MINES, mines);
		map.put(TextKey.Resource.INFRASTRUCTURE, bonus);
		map.put(TextKey.Resource.DEVASTATION, devastation2);
		map.put(TextKey.Resource.STRIKE, strike);
		map.put(TextKey.Resource.FACTORY_UPKEEP, civilian + military + nitrogen);
		map.put(TextKey.Resource.NET, net);
		map.put(TextKey.Resource.TOTAL_GAIN, total);
		return map;
	}

	private LinkedHashMap<TextKey, Double> doFactoryOutput(LinkedHashMap<TextKey, Double> map, double factories)
	{
		double bonus = this.getRailroads() * 0.1 * factories;
		double total = factories + bonus;
		double devastation2 = -total * (devastation / 100.0);
		total = total * (1 - (devastation / 100.0));
		double strike = total * this.getStrikeModifier();
		double net = total + strike;
		map.put(TextKey.Resource.FACTORY_OUTPUT, factories);
		map.put(TextKey.Resource.INFRASTRUCTURE, bonus);
		map.put(TextKey.Resource.DEVASTATION, devastation2);
		map.put(TextKey.Resource.STRIKE, strike);
		map.put(TextKey.Resource.NET, net);
		map.put(TextKey.Resource.TOTAL_GAIN, total);
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
	public LinkedHashMap<TextKey, Double> getCoalProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
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
	public LinkedHashMap<TextKey, Double> getIronProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
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
	public LinkedHashMap<TextKey, Double> getOilProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
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
	public LinkedHashMap<TextKey, Double> getSteelProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
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
	public LinkedHashMap<TextKey, Double> getNitrogenProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
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
	public LinkedHashMap<TextKey, Double> getResearchProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
		double standard = 2;
		double universities = this.getUniversities();
		double total = universities + standard;
		double devastation2 = -total * (devastation / 100.0);
		total = total * (1 - (devastation / 100.0));
		double strike = total * this.getStrikeModifier();
		double net = total + strike;
		map.put(TextKey.Resource.DEFAULT, standard);
		map.put(TextKey.Resource.UNIVERSITIES, universities);
		map.put(TextKey.Resource.DEVASTATION, devastation2);
		map.put(TextKey.Resource.STRIKE, strike);
		map.put(TextKey.Resource.NET, net);
		map.put(TextKey.Resource.TOTAL_GAIN, total);
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
	public LinkedHashMap<TextKey, Long> getLandUsage()
	{
		LinkedHashMap<TextKey, Long> map = new LinkedHashMap<>();
		map.put(TextKey.Land.MINES, City.LAND_MINE * (this.ironMines + this.coalMines + this.oilWells));
		map.put(TextKey.Land.FACTORIES, City.LAND_FACTORY * (this.industryNitrogen + this.industryMilitary + this.industryCivilian));
		map.put(TextKey.Land.UNIVERSITIES, City.LAND_UNIVERSITY * (this.universities));
		return map;
	}

	public LinkedHashMap<TextKey, Integer> getGarrisonSize()
	{
		LinkedHashMap<TextKey, Integer> map = new LinkedHashMap<>();
		int base = 2000;
		int city = this.garrisonLevel.getModifier();
		int size = (int)(this.getSize().getMinimum() * 0.025);
		map.put(TextKey.Garrison.BASE, base);
		map.put(TextKey.Garrison.CITY_GARRISON_POLICY, city);
		map.put(TextKey.Garrison.CITY_SIZE, size);
		int initial = city + base + size;
		switch(nation.getPolicy().getFortification())
		{
			case UNOCCUPIED_FORTIFICATION:
				initial *= -0.75;
				break;
			case MINIMAL_FUNDING_FORTIFICATION:
				initial *= -0.5;
				break;
			case PARTIAL_FUNDING_FORTIFICATION:
				initial = 0;
				break;
			case FULL_FUNDING_FORTIFICATION:
				initial *= 0.5;
				break;
		}
		map.put(TextKey.Garrison.FORTIFICATION_POLICY, initial);
		map.put(TextKey.Garrison.NET, initial + base + city + size);
		return map;
	}

	public String getUrl()
	{
		return "<a href=\"/cities/" + id + "\"><b>" + this.name + "</b></a>";
	}
}
