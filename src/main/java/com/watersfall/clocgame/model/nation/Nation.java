package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.action.PolicyActions;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.math.BadMath;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.message.Declaration;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.SqlBuilder;
import com.watersfall.clocgame.util.Util;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Nation
{
	private HashSet<Updatable> updatables;
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
	private @Getter NationNews news;
	private @Getter int defensive;
	private @Getter int offensive;
	private @Getter Treaty treaty;
	private @Getter Connection conn;
	private @Getter boolean safe;
	private @Getter ResultSet results;
	private @Getter int freeFactories;
	private LinkedHashMap<String, Double> coalProduction = null;
	private LinkedHashMap<String, Double> ironProduction = null;
	private LinkedHashMap<String, Double> oilProduction = null;
	private LinkedHashMap<String, Double> steelProduction = null;
	private LinkedHashMap<String, Double> nitrogenProduction = null;
	private LinkedHashMap<String, Double> researchProduction = null;
	private @Getter LinkedHashMap<Integer, Production>production;
	private HashMap<String, LinkedHashMap<String, Double>> allProductions = null;
	private int landUsage = -1;

	/**
	 *
	 * @param conn The SQL conn to use
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
	 * @param conn  The SQL conn to use
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
				"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
				"WHERE " + where);
		ResultSet results = get.executeQuery();
		while(results.next())
		{
			nations.add(new Nation(results.getInt("cloc_login.id"), results));
		}
		return nations;
	}

	/**
	 * Returns an ordered Collection of all nations matching the where clause, sorted by the order clause
	 * @param conn  the SQL conn to use
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
				"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
				"WHERE " + where + "\n" +
				"ORDER BY " + order);
		ResultSet results = get.executeQuery();
		while(results.next())
		{
			nations.add(new Nation(results.getInt("cloc_login.id"), results));
		}
		return nations;
	}

	/**
	 * Returns an ordered Collection of all nations matching the where clause, sorted by the order clause, with a specified limit
	 * @param conn  the SQL conn to use
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
				"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
				"WHERE " + where + "\n" +
				"ORDER BY " + order + "\n" +
				"LIMIT " + limit);
		ResultSet results = get.executeQuery();
		while(results.next())
		{
			nations.add(new Nation(results.getInt("cloc_login.id"), results));
		}
		return nations;
	}

	/**
	 * Primary Nation constructor that creates a Nation from an ID
	 * @param conn The conn object to use
	 * @param id         The id of the nation being loaded
	 * @param safe       Whether the contents of the returned Nation object should be editable
	 * @throws SQLException if something SQL related goes wrong
	 */
	public Nation(Connection conn, int id, boolean safe) throws SQLException
	{
		production = new LinkedHashMap<>();
		updatables = new HashSet<>();
		PreparedStatement get;
		PreparedStatement getProduction;
		PreparedStatement attacker;
		PreparedStatement defender;
		if(safe)
		{
			get = conn.prepareStatement("SELECT * FROM cloc_login\n" +
					"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
					"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
					"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
					"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
					"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
					"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
					"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
					"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
					"WHERE cloc_login.id=? FOR UPDATE");
			getProduction = conn.prepareStatement("SELECT * FROM production WHERE owner=? FOR UPDATE");
			attacker = conn.prepareStatement("SELECT defender FROM cloc_war WHERE attacker=? AND end=-1 FOR UPDATE");
			defender = conn.prepareStatement("SELECT attacker FROM cloc_war WHERE defender=? AND end=-1 FOR UPDATE");
		}
		else
		{
			get = conn.prepareStatement("SELECT * FROM cloc_login\n" +
					"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
					"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
					"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
					"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
					"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
					"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
					"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
					"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
					"WHERE cloc_login.id=?");
			getProduction = conn.prepareStatement("SELECT * FROM production WHERE owner=?");
			attacker = conn.prepareStatement("SELECT defender FROM cloc_war WHERE attacker=? AND end=-1");
			defender = conn.prepareStatement("SELECT attacker FROM cloc_war WHERE defender=? AND end=-1");
		}
		get.setInt(1, id);
		ResultSet main = get.executeQuery();
		main.first();
		this.results = main;
		this.cosmetic = new NationCosmetic(id, main);
		this.domestic = new NationDomestic(id, main);
		this.economy = new NationEconomy(id, main);
		this.foreign = new NationForeign(id, main);
		this.military = new NationMilitary(id, main);
		this.tech = new NationTech(id, main);
		this.policy = new NationPolicy(id, main);
		this.army = new NationArmy(id, main);
		this.news = new NationNews(conn, id, safe);
		this.invites = new NationInvites(conn, id, safe);
		this.cities = new NationCities(conn, id, safe);
		updatables.add(cosmetic);
		updatables.add(domestic);
		updatables.add(economy);
		updatables.add(foreign);
		updatables.add(military);
		updatables.add(tech);
		updatables.add(policy);
		updatables.add(army);
		getProduction.setInt(1, id);
		ResultSet resultsProduction = getProduction.executeQuery();
		int usedFactories = 0;
		while(resultsProduction.next())
		{
			production.put(resultsProduction.getInt("id"), new Production(
					resultsProduction.getInt("id"),
					resultsProduction.getInt("owner"),
					resultsProduction.getInt("factories"),
					resultsProduction.getInt("efficiency"),
					resultsProduction.getString("production"),
					resultsProduction.getInt("progress")
			));
			usedFactories += resultsProduction.getInt("factories");
		}
		this.freeFactories = getTotalMilitaryFactories() - usedFactories;
		this.id = id;
		this.conn = conn;
		this.safe = safe;
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

		PreparedStatement treatyCheck = conn.prepareStatement("SELECT alliance_id FROM cloc_treaties_members " +
				"WHERE nation_id=?");
		treatyCheck.setInt(1, this.id);
		ResultSet resultsTreaty = treatyCheck.executeQuery();
		if(!resultsTreaty.first())
		{
			treaty = null;
		}
		else
		{
			treaty = new Treaty(conn, resultsTreaty.getInt(1), safe);
		}
	}

	/**
	 * Constructs a partial nation from a ResultSet
	 * @param results The ResultSet to build the nation from
	 */
	public Nation(int id, ResultSet results) throws SQLException
	{
		this.id = id;
		this.economy = new NationEconomy(id, results);
		this.domestic = new NationDomestic(id, results);
		this.cosmetic = new NationCosmetic(id, results);
		this.foreign = new NationForeign(id, results);
		this.military = new NationMilitary(id, results);
		this.tech = new NationTech(id, results);
		this.policy = new NationPolicy(id, results);
		this.army = new NationArmy(id, results);
	}

	/**
	 * Commits all changes made to this object
	 * Put in the main Nation class instead of in the subclasses since they all share the same conn
	 *
	 * @throws SQLException if something TQL related goes wrong
	 */
	public void commit() throws SQLException
	{
		conn.commit();
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
			PreparedStatement check = conn.prepareStatement("SELECT * FROM cloc_treaties_members WHERE nation_id=?");
			check.setInt(1, this.id);
			if(check.executeQuery().first())
			{
				leaveTreaty();
			}
			PreparedStatement join = conn.prepareStatement("INSERT INTO cloc_treaties_members (alliance_id, nation_id, founder) VALUES (?,?,?)");
			join.setInt(1, id);
			join.setInt(2, this.id);
			join.setBoolean(3, founder);
			join.execute();
			PreparedStatement deleteInvite = conn.prepareStatement("DELETE FROM cloc_treaty_invites WHERE nation_id=?");
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
		PreparedStatement leave = conn.prepareStatement("DELETE FROM cloc_treaties_members WHERE nation_id=?");
		leave.setInt(1, this.id);
		leave.execute();
		PreparedStatement emptyCheck = conn.prepareStatement("SELECT nation_id FROM cloc_treaties_members WHERE alliance_id=?");
		emptyCheck.setInt(1, this.treaty.getId());
		ResultSet results = emptyCheck.executeQuery();
		if(!results.first() && this.treaty != null)
		{
			this.treaty.delete();
		}
	}

	/**
	 * Checks whether this nation can declare war on the nation passed in, returning null if it can
	 * If it cannot, returns the reason why
	 * @param nation The Nation to check against
	 * @return null if the nation can declare war on the other, the reason why it cannot otherwise
	 */
	public String canDeclareWar(Nation nation)
	{
		if(this.offensive != 0)
		{
			return Responses.cannotWar("alreadyAtWar");
		}
		else if(nation.defensive != 0)
		{
			return Responses.cannotWar("alreadyAtWar2");
		}
		else if(!Region.borders(nation.getForeign().getRegion(), this.getForeign().getRegion()))
		{
			return Responses.cannotWar("noBorder");
		}
		else if(nation.getMilitary().getWarProtection() > 0)
		{
			if(nation.getForeign().getAlignment() == 0)
			{
				return Responses.cannotWar("neutralProtection");
			}
			else
			{
				return (nation.getForeign().getAlignment() == -1) ? Responses.cannotWar("germanProtection") : Responses.cannotWar("frenchProtection");
			}
		}
		else if(this.military.getWarProtection() > 0)
		{
			if(this.getForeign().getAlignment() == 0)
			{
				return Responses.cannotWar("youNeutralProtection");
			}
			else
			{
				return (this.getForeign().getAlignment() == -1) ? Responses.cannotWar("youGermanProtection") : Responses.cannotWar("youFrenchProtection");
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Checks if this nation is at war with another nation
	 * @param nation The nation to check
	 * @return True if they are at war, false if they are not
	 */
	public boolean isAtWarWith(Nation nation)
	{
		return this.defensive == nation.getId() || this.offensive == nation.getId();
	}

	/**
	 * Checks if this nation is in any war
	 * @return True if the nation is at war, false otherwise
	 */
	public boolean isAtWar()
	{
		return defensive != 0 || offensive != 0;
	}

	/**
	 * Declares war on the specified Nation
	 * @param nation The Nation to declare war on
	 * @throws SQLException If a database error occurs
	 */
	public void declareWar(Nation nation) throws SQLException
	{
		if(nation != null && canDeclareWar(nation) == null)
		{
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
	 *     <li>civilian industry</li>
	 *     <li>military industry</li>
	 *     <li>nitrogen industry</li>
	 *     <li>army upkeep</li>
	 *     <li>net</li>
	 * </ul>
	 * @return A HashMap with the growth change of this Nation
	 */
	public LinkedHashMap<String, Integer> getGrowthChange()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
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
		map.put("army upkeep", army);
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
		long employment = 0;
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
		unemployment = BadMath.log((double) (employment - this.domestic.getPopulation()) / (double) this.domestic.getPopulation());
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
	 * Gets the total amount of military factories
	 * @return The total military factories
	 */
	public int getTotalMilitaryFactories()
	{
		int sum = 0;
		for(City city : cities.getCities().values())
		{
			sum += city.getIndustryMilitary();
		}
		return sum;
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
				manpower *= 0.05;
				break;
			case 1:
				manpower *= 0.10;
				break;
			case 2:
				manpower *= 0.175;
				break;
			case 3:
				manpower *= 0.25;
				break;
			case 4:
				manpower *= 0.45;
				break;
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
		long airforce = this.military.getBiplaneFighters() +
				this.military.getTriplaneFighters() +
				this.military.getMonoplaneFighters() +
				this.military.getReconBalloons() +
				this.military.getReconPlanes() +
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
	 * Gets the total equipment of the army
	 * @return The total equipment
	 */
	public int getTotalEquipment()
	{
		return army.getMusket() + army.getRifledMusket() + army.getSingleShot() + army.getNeedleNose() +
				army.getBoltActionManual() + army.getBoltActionClip() + army.getStraightPull() +
				army.getSemiAuto() + army.getMachineGun();
	}

	public LinkedHashMap<String, Integer> getEquipment()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("Muskets", this.army.getMusket());
		map.put("Rifled Muskets", this.army.getRifledMusket());
		map.put("Single Shot Rifles", this.army.getSingleShot());
		map.put("Needle Nose Rifles", this.army.getNeedleNose());
		map.put("Manually Loaded Bolt Action Rifles", this.army.getBoltActionManual());
		map.put("Clip Loaded Bolt Action Rifles", this.army.getBoltActionClip());
		map.put("Straight Pull Rifles", this.army.getStraightPull());
		map.put("Semi-Automatic Rifles", this.army.getSemiAuto());
		map.put("Machine Guns", this.army.getMachineGun());
		return map;
	}

	/**
	 * Calculates the power of an army based on it's size, technology level, training, and artillery
	 * @return The army's power
	 */
	public double getPower()
	{
		double power = 0e0;
		long equipment = army.getSize() * 1000;
		if(equipment > 0)
		{
			long amount = (army.getMachineGun() > equipment) ? equipment : army.getMachineGun();
			power += amount * 13.5;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getSemiAuto() > equipment) ? equipment : army.getSemiAuto();
			power += amount * 12;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getStraightPull() > equipment) ? equipment : army.getStraightPull();
			power += amount * 10.5;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getBoltActionClip() > equipment) ? equipment : army.getBoltActionClip();
			power += amount * 9;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getBoltActionManual() > equipment) ? equipment : army.getBoltActionManual();
			power += amount * 7.5;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getNeedleNose() > equipment) ? equipment : army.getNeedleNose();
			power += amount * 6;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getSingleShot() > equipment) ? equipment : army.getSingleShot();
			power += amount * 4.5;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getRifledMusket() > equipment) ? equipment : army.getRifledMusket();
			power += amount * 3;
			equipment -= amount;
		}
		if(equipment > 0)
		{
			long amount = (army.getMusket() > equipment) ? equipment : army.getMusket();
			power += amount * 1.5;
			equipment -= amount;
		}
		power += army.getArtillery() * 10;
		power += equipment * 0.5;
		power = java.lang.Math.sqrt(power / 1000);
		power *= java.lang.Math.sqrt(army.getTraining() + 1);
		return java.lang.Math.sqrt(power);
	}

	/**
	 * Calculates the ability of this nation to shoot down other planes
	 * @return The fighter power
	 */
	public double getFighterPower()
	{
		int power = 0;
		power += this.military.getBiplaneFighters() +
				this.military.getTriplaneFighters() +
				this.military.getMonoplaneFighters();
		return power;
	}

	/**
	 * Calculates the ability of this nation when bombing targets
	 * @return The bomber power
	 */
	public double getBomberPower()
	{
		int power = 0;
		power += this.military.getBombers();
		power += (this.military.getZeppelins() * 0.5);
		return power;
	}

	public int getTotalShipCount()
	{
		int power = 0;
		power += this.military.getSubmarines();
		power += this.military.getDestroyers();
		power += this.military.getCruisers();
		power += this.military.getPreBattleships();
		power += this.military.getBattleships();
		return power;
	}

	/**
	 * Calculates the general naval power of this country
	 * @return The naval power
	 */
	public double getNavalPower()
	{
		double power = getTotalShipCount();
		power /= 10;
		return power;
	}

	/**
	 * Calculates the amount of casualties this army takes attacking a specified defender
	 * @param defender The army this army is attacking
	 * @return The casualties taken
	 */
	public int getAttackingCasualties(Nation defender)
	{
		double attackPower = this.getPower();
		double defensePower = defender.getPower() * (1 + (double)defender.getArmy().getFortification() / 10);
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
		double defensePower = this.getPower() * (1 + (double)this.getArmy().getFortification() / 10);;
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
		PreparedStatement statement;
		String sql;
		SqlBuilder builder = new SqlBuilder();
		ArrayList<Object> values = new ArrayList<>();
		for(Updatable updatable : updatables)
		{
			if(!updatable.getFields().isEmpty())
			{
				builder.addTables(updatable.getTableName());
				builder.addWheres(updatable.getTableName());
				for(HashMap.Entry<String, Object> entry : updatable.getFields().entrySet())
				{
					builder.addFields(entry.getKey());
					values.add(entry.getValue());
				}
			}
		}
		sql = builder.build();
		statement = conn.prepareStatement(sql);
		int index = 1;
		for(Object object : values)
		{
			if(object instanceof Integer)
				statement.setInt(index, (Integer) object);
			else if(object instanceof Double)
				statement.setDouble(index, (Double) object);
			else if(object instanceof Long)
				statement.setLong(index, (Long)object);
			else
				statement.setString(index, object.toString());
			index++;
		}
		if(index > 1)
		{
			statement.setInt(index, id);
			statement.execute();
		}
		cities.update();
		updateAllProduction();
	}

	/**
	 * Returns the cash-cost of a policy<br>
	 * This contains most policy costs that would show up under policies->category
	 * but does not include any that cost another resource/other resources
	 * @param policy The ID of the policy
	 * @return The cost of the policy
	 */
	public int getPolicyCost(int policy)
	{
		switch(policy)
		{
			case PolicyActions.ID_PROPAGANDA:
				return (int)(this.economy.getGdp() / 2 * (this.domestic.getApproval() / 100.0));
			case PolicyActions.ID_WAR_PROPAGANDA:
				return getPolicyCost(PolicyActions.ID_PROPAGANDA) / 2;
			case PolicyActions.ID_LAND_CLEARANCE:
				return (int)this.economy.getGdp() * 2;
			case PolicyActions.ID_ALIGN:
			case PolicyActions.ID_FREE:
			case PolicyActions.ID_ARREST:
				return 100;
			case PolicyActions.ID_TRAIN:
				return this.army.getSize() * this.army.getSize() * this.army.getTraining() / 200;
			default:
				return 0;
		}
	}

	/**
	 * Returns the costs of a policy as a LinkedHashMap where the key is the resource and the value is the cost
	 * for that resource<br>
	 * Contains the cost of any policy that takes more than one resource, or a single resource that isn't cash
	 * @param policy The ID of the policy
	 * @return A LinkedHashMap of the costs
	 */
	public LinkedHashMap<String, Integer> getPolicyCostMap(int policy)
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		switch(policy)
		{

		}
		return map;
	}

	/**
	 * Checks whether the nation has researched a tech
	 * @param tech The String name of the tech
	 * @return True if it's researched, false otherwise
	 */
	public boolean hasTech(String tech)
	{
		return this.tech.getResearchedTechs().contains(Technologies.valueOf(tech));
	}

	/**
	 * Returns the cost of posting declarations
	 * @return Declaration posting cost
	 */
	public int getDeclarationCost()
	{
		return Declaration.COST;
	}

	/**
	 * Calculates how much this nation's budget increases by for the daily turn change (or 7 times a standard turn)
	 * @return The budget increase
	 */
	public double getBudgetChange()
	{
		return this.economy.getGdp() / 7;
	}

	/**
	 * Runs the daily production tick
	 * @throws SQLException if a database error occurs
	 */
	public void processProduction() throws SQLException
	{
		if(this.production.size() == 0)
		{
			return;
		}
		String statement = "UPDATE cloc_army, cloc_military SET ";
		for(Production production : this.production.values())
		{
			double ic = production.getIc() + (production.getProgress() / 100.0);
			int amount = (int)(ic / production.getProductionAsTechnology().getTechnology().getProductionCost());
			int leftover = (int)((ic - (amount * production.getProductionAsTechnology().getTechnology().getProductionCost())) * 100);
			production.setProgress(leftover);
			statement += production.getProductionAsTechnology().getTechnology().getProductionName() + "=" + production.getProductionAsTechnology().getTechnology().getProductionName() + "+" + amount + ", ";
			int efficiencyGain = (int)(0.1 * (10000.0 / (production.getEfficiency() / 100.0)));
			production.setEfficiency(production.getEfficiency() + efficiencyGain);
			if(production.getEfficiency() > 10000)
			{
				production.setEfficiency(10000);
			}
		}
		statement += "WHERE cloc_army.id=? AND cloc_military.id=? AND cloc_army.id=cloc_military.id";
		statement = statement.replace(", WHERE", " WHERE");
		PreparedStatement update = conn.prepareStatement(statement);
		update.setInt(1, this.id);
		update.setInt(2, this.id);
		update.execute();
	}

	public String getNationUrl()
	{
		return "<a href=\"/nation/" + id + "\">" + this.cosmetic.getNationName() + "</a>";
	}

	/**
	 * Gets one of the nations productions by id
	 * @param id the id of the production
	 * @return The production
	 */
	public Production getProductionById(int id)
	{
		return production.get(id);
	}

	/**
	 * Saved all this nation's productions in the database
	 * @throws SQLException If a database error occurs
	 */
	public void updateAllProduction() throws SQLException
	{
		for(Production production : production.values())
		{
			production.update(this.conn);
		}
	}

	@Override
	public boolean equals(Object object)
	{
		if(object instanceof Nation)
		{
			Nation nation = (Nation)object;
			return nation.getId() == this.getId();
		}
		else
		{
			return false;
		}
	}
}
