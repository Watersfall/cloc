package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.math.Math;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.util.Util;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Nation
{
	private @Getter int id;
	private @Getter NationCosmetic cosmetic;
	private @Getter NationDomestic domestic;
	private @Getter NationEconomy economy;
	private @Getter NationForeign foreign;
	private @Getter NationMilitary military;
	private @Getter NationCities cities;
	private @Getter NationArmy army;
	private @Getter NationPolicy policy;
	private @Getter NationTech tech;
	private @Getter NationInvites invites;
	private @Getter int defensive;
	private @Getter int offensive;
	private @Getter Treaty treaty;
	private @Getter Connection connection;
	private @Getter boolean safe;
	private LinkedHashMap<String, Double> coalProduction = null;
	private LinkedHashMap<String, Double> ironProduction = null;
	private LinkedHashMap<String, Double> oilProduction = null;
	private LinkedHashMap<String, Double> steelProduction = null;
	private LinkedHashMap<String, Double> nitrogenProduction = null;
	private LinkedHashMap<String, Double> weaponProduction = null;
	private LinkedHashMap<String, Double> researchProduction = null;
	private HashMap<String, LinkedHashMap<String, Double>> allProductions = null;
	private int landUsage = -1;

	/**
	 *
	 * @param conn The SQL connection to use
	 * @param name The nation name to get
	 * @param safe Whether the returned nation should be safe to write to
	 * @return A nation object of the nation with the specified name
	 * @throws SQLException if a database issue occurs
	 */
	public static Nation getNationByName(Connection conn, String name, boolean safe) throws SQLException, NationNotFoundException
	{
		PreparedStatement read = conn.prepareStatement("SELECT id FROM cloc_cosmetic WHERE nation_name=?");
		read.setString(1, name);
		ResultSet results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that name!");
		}
		else
		{
			return new Nation(conn, results.getInt(1), safe);
		}
	}

	/**
	 * Returns an unordered Collection of all Nations in the database matching the where clause fed into the method
	 * @param conn  The SQL connection to use
	 * @param where the SQL where clause
	 * @return Collection of nations matching where
	 * @throws SQLException if a database issue occurs
	 */
	public static Collection<Nation> getNations(Connection conn, String where) throws SQLException
	{
		Collection<Nation> nations = new HashSet<>();
		PreparedStatement get = conn.prepareStatement("SELECT * FROM cloc_login\n" +
				"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
				"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
				"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
				"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
				"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
				"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
				"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
				"WHERE " + where);
		ResultSet results = get.executeQuery();
		while(results.next())
		{
			nations.add(new Nation(conn, results.getInt(1), false));
		}
		return nations;
	}

	/**
	 * Returns an ordered Collection of all nations matching the where clause, sorted by the order clause
	 * @param conn  the SQL connection to use
	 * @param where the SQL where clause
	 * @param order the SQL order by clause
	 * @return Collection of nations matching where
	 * @throws SQLException if a database issue occurs
	 */
	public static Collection<Nation> getNations(Connection conn, String where, String order) throws SQLException
	{
		Collection<Nation> nations = new ArrayList<>();
		PreparedStatement get = conn.prepareStatement("SELECT * FROM cloc_login\n" +
				"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
				"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
				"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
				"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
				"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
				"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
				"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
				"WHERE " + where + "\n" +
				"ORDER BY " + order);
		ResultSet results = get.executeQuery();
		while(results.next())
		{
			nations.add(new Nation(conn, results.getInt(1), false));
		}
		return nations;
	}

	/**
	 * Returns an ordered Collection of all nations matching the where clause, sorted by the order clause, with a specified limit
	 * @param conn  the SQL connection to use
	 * @param where the SQL where clause
	 * @param order the SQL order by clause
	 * @param limit The SQL limit clause
	 * @return Collection of nations matching where
	 * @throws SQLException if a database issue occurs
	 */
	public static Collection<Nation> getNations(Connection conn, String where, String order, String limit) throws SQLException
	{
		Collection<Nation> nations = new ArrayList<>();
		PreparedStatement get = conn.prepareStatement("SELECT * FROM cloc_login\n" +
				"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
				"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
				"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
				"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
				"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
				"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
				"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
				"WHERE " + where + "\n" +
				"ORDER BY " + order + "\n" +
				"LIMIT " + limit);
		ResultSet results = get.executeQuery();
		while(results.next())
		{
			nations.add(new Nation(conn, results.getInt(1), false));
		}
		return nations;
	}

	/**
	 * Primary Nation constructor that creates a Nation from an ID
	 * @param connection The connection object to use
	 * @param id         The id of the nation being loaded
	 * @param safe       Whether the contents of the returned Nation object should be editable
	 * @throws SQLException if something SQL related goes wrong
	 */
	public Nation(Connection connection, int id, boolean safe) throws SQLException
	{
		cosmetic = new NationCosmetic(connection, id, safe);
		domestic = new NationDomestic(connection, id, safe);
		economy = new NationEconomy(connection, id, safe);
		foreign = new NationForeign(connection, id, safe);
		military = new NationMilitary(connection, id, safe);
		army = new NationArmy(connection, id, safe);
		cities = new NationCities(connection, id, safe);
		policy = new NationPolicy(connection, id, safe);
		tech = new NationTech(connection, id, safe);
		invites = new NationInvites(connection, id, safe);
		this.id = id;
		this.connection = connection;
		this.safe = safe;

		//Wars
		PreparedStatement attacker = connection.prepareStatement("SELECT defender FROM cloc_war WHERE attacker=? AND end=-1");
		PreparedStatement defender = connection.prepareStatement("SELECT attacker FROM cloc_war WHERE defender=? AND end=-1");
		attacker.setInt(1, this.id);
		defender.setInt(1, this.id);
		ResultSet resultsAttacker = attacker.executeQuery();
		ResultSet resultsDefender = defender.executeQuery();
		if(!resultsAttacker.first())
		{
			offensive = 0;
		}
		else
		{
			this.offensive = resultsAttacker.getInt(1);
		}
		if(!resultsDefender.first())
		{
			defensive = 0;
		}
		else
		{
			this.defensive = resultsDefender.getInt(1);
		}

		//Treaty
		PreparedStatement treatyCheck = connection.prepareStatement("SELECT alliance_id FROM cloc_treaties_members " +
				"WHERE nation_id=?");
		treatyCheck.setInt(1, this.id);
		ResultSet resultsTreaty = treatyCheck.executeQuery();
		if(!resultsTreaty.first())
		{
			treaty = null;
		}
		else
		{
			treaty = new Treaty(connection, resultsTreaty.getInt(1), safe);
		}
	}

	/**
	 * Commits all changes made to this object
	 * Put in the main Nation class instead of in the subclasses since they all share the same connection
	 *
	 * @throws SQLException if something TQL related goes wrong
	 */
	public void commit() throws SQLException
	{
		connection.commit();
	}

	/**
	 * Attempts to join a treaty in the standard way:
	 * by checking if this nation has an invite and letting them in if they do
	 * @param treaty The Treaty to join
	 * @param founder Whether this Nation should be roled as founder of the treaty or not
	 * @throws SQLException If a database error occurs
	 */
	public void joinTreaty(Treaty treaty, boolean founder) throws SQLException
	{
		this.joinTreaty(treaty.getId(), founder);
	}

	/**
	 * Attempts to join a treaty in the standard way:
	 * by checking if this nation has an invite and letting them in if they do
	 * @param id The id of the Treaty to join
	 * @param founder Whether this Nation should be roled as founder of the treaty or not
	 * @throws SQLException If a database error occurs
	 */
	public void joinTreaty(Integer id, boolean founder) throws SQLException
	{
		this.joinTreaty(id, founder, false);
	}

	/**
	 * Attempts to join a treaty
	 * @param treaty The Treaty to join
	 * @param founder Whether this nation should be roled as Treaty Founder
	 * @param ignoreInvite Whether this nation should bypass the invite requirement to join
	 * @throws SQLException If a database error occurs
	 */
	public void joinTreaty(Treaty treaty, boolean founder, boolean ignoreInvite) throws SQLException
	{
		this.joinTreaty(treaty.getId(), founder, ignoreInvite);
	}

	/**
	 * Attempts to join a treaty
	 * @param id The id of the Treaty to join
	 * @param founder Whether this nation should be roled as Treaty Founder
	 * @param ignoreInvite Whether this nation should bypass the invite requirement to join
	 * @throws SQLException If a database error occurs
	 */
	public void joinTreaty(Integer id, boolean founder, boolean ignoreInvite) throws SQLException
	{
		if((ignoreInvite) || (this.invites.getInvites().contains(id)))
		{
			PreparedStatement check = connection.prepareStatement("SELECT * FROM cloc_treaties_members WHERE nation_id=?");
			check.setInt(1, this.id);
			if(check.executeQuery().first())
			{
				leaveTreaty();
			}
			PreparedStatement join = connection.prepareStatement("INSERT INTO cloc_treaties_members (alliance_id, nation_id, founder) VALUES (?,?,?)");
			join.setInt(1, id);
			join.setInt(2, this.id);
			join.setBoolean(3, founder);
			join.execute();
			PreparedStatement deleteInvite = connection.prepareStatement("DELETE FROM cloc_treaty_invites WHERE nation_id=?");
			deleteInvite.setInt(1, this.id);
			deleteInvite.execute();
		}
	}

	/**
	 * Leaves the current treaty
	 * @throws SQLException If a database error occurs
	 */
	public void leaveTreaty() throws SQLException
	{
		PreparedStatement leave = connection.prepareStatement("DELETE FROM cloc_treaties_members WHERE nation_id=?");
		leave.setInt(1, this.id);
		leave.execute();
		PreparedStatement emptyCheck = connection.prepareStatement("SELECT nation_id FROM cloc_treaties_members WHERE alliance_id=?");
		emptyCheck.setInt(1, this.treaty.getId());
		ResultSet results = emptyCheck.executeQuery();
		if(!results.first() && this.treaty != null)
		{
			this.treaty.delete();
		}
	}

	/**
	 * Checks whether this nation can declare war on the nation passed in
	 * @param nation The Nation to check against
	 * @return True if the nation can declare war on the other, false otherwise
	 */
	public boolean canDeclareWar(Nation nation)
	{
		return (this.offensive == 0 && nation.getDefensive() == 0)
				&& Region.borders(nation.getForeign().getRegion(), this.getForeign().getRegion())
				&& nation.getMilitary().getWarProtection() <= Util.turn;
	}

	/**
	 * Declares war on the specified Nation
	 * @param nation The Nation to declare war on
	 * @throws SQLException If a database error occurs
	 */
	public void declareWar(Nation nation) throws SQLException
	{
		if(nation != null && canDeclareWar(nation))
		{
			Connection conn = Database.getDataSource().getConnection();
			PreparedStatement declare = conn.prepareStatement("INSERT INTO cloc_war (attacker, defender, start) VALUES (?,?,?)");
			declare.setInt(1, this.id);
			declare.setInt(2, nation.getId());
			declare.setInt(3, Util.turn);
			declare.execute();
			conn.commit();
		}
	}

	/**
	 * Calculates the growth change of this nation, returning a HashMap with keys:
	 * <ul>
	 *     <li>factories</li>
	 *     <li>army</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A HashMap with the growth change of this Nation
	 */
	public HashMap<String, Integer> getGrowthChange()
	{
		HashMap<String, Integer> map = new HashMap<>();
		int factoriesCiv = 0;
		int factoriesMil = 0;
		int factoriesNit = 0;
		int army = this.army.getSize() / -20;
		for(City city : cities.getCities().values())
		{
			factoriesCiv += city.getIndustryCivilian();
			factoriesMil += city.getIndustryMilitary();
			factoriesNit += city.getIndustryNitrogen();
		}
		map.put("civilian industry", factoriesCiv);
		map.put("military industry", factoriesMil);
		map.put("nitrogen industry", factoriesNit);
		map.put("army", army);
		map.put("net", factoriesCiv + factoriesMil + factoriesNit + army);
		return map;
	}

	/**
	 * Calculates out the population growth of this Nation, returning a HashMap with keys:
	 * <ul>
	 *     <li>base</li>
	 *     <li>policies</li>
	 *     <li>unemployment</li>
	 *     <li>total</li>
	 * </ul>
	 * @return A HashMap with the Nations population growth
	 */
	public HashMap<String, Double> getPopulationGrowth()
	{
		HashMap<String, Double> map = new HashMap<>();
		double modifier = 1;
		int employment = 0;
		double unemployment = 0;
		double growth = 0.2;
		if(this.policy.getFood() == 2)
		{
			modifier += 0.25;
		}
		else if(this.policy.getFood() == 0)
		{
			modifier += -0.50;
		}
		if(this.policy.getManpower() == 0)
		{
			modifier += 0.25;
		}
		if(this.policy.getManpower() == 1)
		{
			modifier += 0.10;
		}
		else if(this.policy.getManpower() == 3)
		{
			modifier += -0.1;
		}
		else if(this.policy.getManpower() == 4)
		{
			modifier += -0.25;
		}
		if(this.policy.getEconomy() == 4)
		{
			modifier += -0.1;
		}
		for(City city : cities.getCities().values())
		{
			employment += city.getTotalEmployment();
		}
		modifier += unemployment;
		unemployment = Math.log((double) (employment - this.domestic.getPopulation()) / (double) this.domestic.getPopulation());
		map.put("base", growth);
		map.put("policies", modifier);
		map.put("unemployment", unemployment);
		map.put("total", growth * modifier * unemployment);
		return map;
	}

	/**
	 * Calculates the approval change of this nation and returns a HashMap with keys:
	 * <ul>
	 *     <li>policies</li>
	 *     <li>total</li>
	 * </ul>
	 * @return A HashMap with this Nations approval change
	 */
	public HashMap<String, Integer> getApprovalChange()
	{
		HashMap<String, Integer> map = new HashMap<>();
		int policies = 0;
		if(defensive == 0 && offensive == 0 && policy.getEconomy() > 2)
		{
			policies += -2;
		}
		if(defensive != 0 && offensive != 0 && policy.getEconomy() < 2)
		{
			policies += -2;
		}
		map.put("policies", policies);
		map.put("total", policies);
		return map;
	}

	/**
	 * Calculates the stability change of this nation and returns a HashMap with keys:
	 * <ul>
	 *     <li>total</li>
	 * </ul>
	 * @return A HashMap with this Nations stability change
	 */
	public HashMap<String, Integer> getStabilityChange()
	{
		HashMap<String, Integer> map = new HashMap<>();
		map.put("total", 1);
		return map;
	}

	/**
	 * Calculates the land usage by type of each city in this Nation,
	 * returning a HashMap where each Key is the name of a city,
	 * and the Value is a HashMap where each key is a type, and it's value
	 * is the land usage
	 * @return A HashMap with the land usage
	 */
	public HashMap<String, HashMap<String, Integer>> getLandUsageByCityAndType()
	{
		HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
		for(City city : this.cities.getCities().values())
		{
			map.put(city.getName(), city.getLandUsage());
		}
		return map;
	}

	/**
	 * Calculates the total land used by each city in this Nation, returning a HashMap where
	 * each Key is the name of a city, and the Value is the land usage of that city
	 * @return A HashMap with the land usage by city
	 */
	public HashMap<String, Integer> getLandUsageByCity()
	{
		HashMap<String, Integer> map = new HashMap<>();
		for(City city : this.cities.getCities().values())
		{
			int total = 0;
			for(Integer land : city.getLandUsage().values())
			{
				total += land;
			}
			map.put(city.getName(), total);
		}
		return map;
	}

	/**
	 * Calculates the total land usage by type of this nation, returning a HashMap with the following keys:
	 * <ul>
	 *     <li>mines</li>
	 *     <li>factories</li>
	 *     <li>universities</li>
	 * </ul>
	 * @return HashMap with the land usage of this nation by type
	 */
	public HashMap<String, Integer> getLandUsageByType()
	{
		HashMap<String, Integer> map = new HashMap<>();
		int mines = 0, factories = 0, universities = 0;
		for(City city : this.cities.getCities().values())
		{
			HashMap<String, Integer> land = city.getLandUsage();
			mines += land.get("mines");
			factories += land.get("factories");
			universities += land.get("universities");
		}
		map.put("mines", mines);
		map.put("factories", factories);
		map.put("universities", universities);
		return map;
	}

	/**
	 * Calculates the free land available in this Nation
	 * @return The free land available
	 */
	public int getFreeLand()
	{
		return this.domestic.getLand() - this.getTotalLandUsage();
	}

	/**
	 * Calculates out the food production of this Nation, stored in a HashMap with keys:
	 * <ul>
	 *     <li>farming</li>
	 *     <li>costs</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A HashMap containing food production
	 */
	public HashMap<String, Integer> getFoodProduction()
	{
		HashMap<String, Integer> map = new HashMap<>();
		map.put("farming", this.getFreeLand() / 250);
		map.put("costs", (int)(this.domestic.getPopulation() / 2000));
		map.put("net", map.get("farming") - map.get("costs"));
		return map;
	}

	/**
	 * Calculates the total manpower a nation has
	 * @return The total manpower of the nation
	 */
	public long getTotalManpower()
	{
		long lostManpower = domestic.getManpowerLost();
		long manpower = domestic.getPopulation();
		switch(policy.getManpower())
		{
			case 0:
				manpower *= 0.025;
			case 1:
				manpower *= 0.05;
			case 2:
				manpower *= 0.1;
			case 3:
				manpower *= 0.25;
			case 4:
				manpower *= 0.45;
		}
		return manpower - lostManpower;
	}

	/**
	 * Calculates out the total used manpower buy this nation, stores in a HashMap with keys:
	 * <ul>
	 *     <li>Navy</li>
	 *     <li>Airforce</li>
	 *     <li>Army</li>
	 * </ul>
	 * @return A HashMap containing manpower usage
	 */
	public HashMap<String, Long> getUsedManpower()
	{
		HashMap<String, Long> map = new HashMap<>();
		long navy = this.military.getBattleships() +
				this.military.getCruisers() +
				this.military.getPreBattleships() +
				this.military.getCruisers() +
				this.military.getDestroyers() +
				this.military.getSubmarines();
		navy *= 500;
		long airforce = this.military.getFighters() +
				this.military.getBombers() +
				this.military.getZeppelins();
		airforce *= 50;
		long army = this.army.getSize();
		army *= 1000;
		map.put("Navy", navy);
		map.put("Airforce", airforce);
		map.put("Army", army);
		return map;
	}

	/**
	 * Calculates the amount of free manpower this nation has available
	 * @return The amount of free manpower
	 */
	public long getFreeManpower()
	{
		long soldiers = this.army.getSize() * 1000;
		long manpower = getTotalManpower();
		return manpower - soldiers;
	}

	/**
	 * Calculates the total coal production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>mines</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap containing the total coal production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalCoalProduction()
	{
		if(coalProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			map.put("total", 0e0);
			map.put("mines", 0e0);
			map.put("infrastructure", 0e0);
			map.put("civilian factory demands", 0e0);
			map.put("military factory demands", 0e0);
			map.put("nitrogen plant demands", 0e0);
			map.put("net", 0e0);
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getCoalProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k,v) -> v += entry.getValue());
				}
			}
			coalProduction = map;
		}
		return coalProduction;
	}

	/**
	 * Calculates the total iron production of all cities
	 * Return value cached to save performance on multiple calls
	 * HashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>mines</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A HashMap containing the total iron production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalIronProduction()
	{
		if(ironProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			map.put("total", 0e0);
			map.put("mines", 0e0);
			map.put("infrastructure", 0e0);
			map.put("civilian factory demands", 0e0);
			map.put("military factory demands", 0e0);
			map.put("nitrogen plant demands", 0e0);
			map.put("net", 0e0);
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getIronProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k,v) -> v += entry.getValue());
				}
			}
			ironProduction = map;
		}
		return ironProduction;
	}

	/**
	 * Calculates the total oil production of all cities
	 * Return value cached to save performance on multiple calls
	 * HashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>wells</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap containing the total iron production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalOilProduction()
	{
		if(oilProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getOilProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k,v) -> v += entry.getValue());
				}
			}
			oilProduction = map;
		}
		return oilProduction;
	}

	/**
	 * Calculates the total steel production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap containing the total steel production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalSteelProduction()
	{
		if(steelProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getSteelProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k,v) -> v += entry.getValue());
				}
			}
			steelProduction = map;
		}
		return steelProduction;
	}

	/**
	 * Calculates the total nitrogen production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap containing the total nitrogen production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalNitrogenProduction()
	{
		if(nitrogenProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getNitrogenProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k,v) -> v += entry.getValue());
				}
			}
			nitrogenProduction = map;
		}
		return nitrogenProduction;
	}

	/**
	 * Calculates the total weapons production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap containing the total weapons production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalWeaponsProduction()
	{
		if(weaponProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getWeaponsProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k,v) -> v += entry.getValue());
				}
			}
			weaponProduction = map;
		}
		return weaponProduction;
	}

	/**
	 * Calculates the total research production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A LinkedHashMap containing the total research production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalResearchProduction()
	{
		if(researchProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getResearchProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k,v) -> v += entry.getValue());
				}
			}
			researchProduction = map;
		}
		return researchProduction;
	}

	/**
	 * Calculates the total production of all types for this nation
	 * The production is returned as a HashMap of HashMaps, with the outer HashMap containing the keys:
	 * <ul>
	 *     <li>coal</li>
	 *     <li>iron</li>
	 *     <li>oil</li>
	 *     <li>steel</li>
	 *     <li>nitrogen</li>
	 *     <li>research</li>
	 *     <li>weapons</li>
	 * </ul>
	 * Each of the inner HashMaps has the same keys as their standard getter,
	 * typically containing:
	 * <ul>
	 *     <li>total</li>
	 *     <li>type (i.e. mine, factory)</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * And if it's a type with upkeep costs, it will additionally have the various costs and upkeeps
	 * <ul>
	 *     <li>costs</li>
	 *     <li>upkeep</li>
	 * </ul>
	 * @return A HashMap containing the total weapons production of this Nation
	 */
	public HashMap<String, LinkedHashMap<String, Double>> getAllTotalProductions()
	{
		if(allProductions == null)
		{
			HashMap<String, LinkedHashMap<String, Double>> map = new HashMap<>();
			map.put("coal", this.getTotalCoalProduction());
			map.put("iron", this.getTotalIronProduction());
			map.put("oil", this.getTotalOilProduction());
			map.put("steel", this.getTotalSteelProduction());
			map.put("nitrogen", this.getTotalNitrogenProduction());
			map.put("research", this.getTotalResearchProduction());
			map.put("weapons", this.getTotalWeaponsProduction());
			allProductions = map;
		}
		return allProductions;
	}

	/**
	 * Calculates the total land usage of this Nation
	 * @return The total land usage
	 */
	public int getTotalLandUsage()
	{
		if(landUsage == -1)
		{
			int total = 0;
			for(City city : cities.getCities().values())
			{
				for(Integer integer : city.getLandUsage().values())
				{
					total += integer;
				}
			}
			this.landUsage = total;
		}
		return landUsage;
	}

	/**
	 * Calculates the power of an army based on it's army.getSize(), technology level, army.getTraining(), and artillery
	 * @return The army's power
	 */
	public double getPower()
	{
		double power = army.getSize() * 1000;
		if(power > this.army.getMusket())
		{
			power = power * (this.army.getMusket() / power);
		}
		power = java.lang.Math.sqrt(power / 1000);
		power *= java.lang.Math.sqrt(army.getTraining() + 1);
		power *= java.lang.Math.sqrt(army.getArtillery() + 1);
		return java.lang.Math.sqrt(power);
	}

	/**
	 * Calculates the amount of casualties this army takes attacking a specified defender
	 * @param defender The army this army is attacking
	 * @return The casualties taken
	 */
	public int getAttackingCasualties(Nation defender)
	{
		double attackPower = this.getPower();
		double defensePower = defender.getPower();
		if(attackPower > defensePower)
		{
			defensePower = java.lang.Math.pow(defensePower, 1.95);
			double armyHp = this.army.getSize() * 20.0;
			armyHp -= defensePower;
			return (int)(this.army.getSize() - (armyHp / 20.0));
		}
		else
		{
			defensePower = java.lang.Math.pow(defensePower, 2.0);
			double armyHp = this.army.getSize() * 20.0;
			armyHp -= defensePower;
			return (int)(this.army.getSize() - (armyHp / 20.0));
		}
	}

	/**
	 * Calculates the amount of casualties that this army takes while defending against the specified attacking army
	 * @param attacker The specified attacking army
	 * @return The casualties taken
	 */
	public int getDefendingCasualties(Nation attacker)
	{
		double attackPower = attacker.getPower();
		double defensePower = this.getPower();
		if(attackPower > defensePower)
		{
			attackPower = java.lang.Math.pow(attackPower, 2.0);
			double armyHp = this.army.getSize() * 20.0;
			armyHp -= attackPower;
			return (int)(this.army.getSize() - (armyHp / 20.0));
		}
		else
		{
			attackPower = java.lang.Math.pow(attackPower, 1.95);
			double armyHp = this.army.getSize() * 20.0;
			armyHp -= attackPower;
			return (int)(this.army.getSize() - (armyHp / 20.0));
		}
	}

	public void update() throws SQLException
	{
		cosmetic.update();
		domestic.update();
		economy.update();
		foreign.update();
		military.update();
		army.update();
		policy.update();
		tech.update();
		for(City city : cities.getCities().values())
		{
			city.update();
		}
	}

	/**
	 * Returns the cash-cost of a policy<br>
	 * This contains most policy costs that would show up under policies->category
	 * but does not include any that cost another resource/other resources
	 * @param policy The name of the policy
	 * @return The cost of the policy
	 */
	public int getPolicyCost(String policy)
	{
		switch(policy)
		{
			case "propaganda":
				return (int)(this.economy.getGdp() / 2 * (this.domestic.getApproval() / 100.0));
			case "war_propaganda":
				return getPolicyCost("propaganda") / 2;
			case "land_clearance":
				return (int)this.economy.getGdp() * 2;
			case "align":
			case "free":
			case "crackdown":
				return 100;
			case "training":
				return this.army.getSize() * this.army.getSize() * this.army.getTraining() / 200;
			default:
				return 0;
		}
	}

	/**
	 * Returns the costs of a policy as a LinkedHashMap where the key is the resource and the value is the cost
	 * for that resource<br>
	 * Contains the cost of any policy that takes more than one resource, or a single resource that isn't cash
	 * @param policy The name of the policy
	 * @return A LinkedHashMap of the costs
	 */
	public LinkedHashMap<String, Integer> getPolicyCostMap(String policy)
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		switch(policy)
		{
			case "artillery":
				map.put("Steel", 15);
				map.put("Nitrogen", 7);
				break;
			case "musket":
				map.put("Steel", 5);
				break;
		}
		return map;
	}

	/**
	 * Calculates how much this nation's budget increases by for the daily turn change (or 7 times a standard turn)
	 * @return The budget increase
	 */
	public double getBudgetChange()
	{
		return this.economy.getGdp() / 7;
	}
}
