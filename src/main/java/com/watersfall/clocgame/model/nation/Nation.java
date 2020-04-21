package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.decisions.Decision;
import com.watersfall.clocgame.model.message.Declaration;
import com.watersfall.clocgame.model.military.Bomber;
import com.watersfall.clocgame.model.military.Equipment;
import com.watersfall.clocgame.model.military.Fighter;
import com.watersfall.clocgame.model.military.ReconPlane;
import com.watersfall.clocgame.model.policies.Policy;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;
import com.watersfall.clocgame.model.technology.technologies.single.doctrine.*;
import com.watersfall.clocgame.model.technology.technologies.single.economy.*;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.SqlBuilder;
import com.watersfall.clocgame.util.Time;
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
	private @Getter Nation defensive;
	private @Getter Nation offensive;
	private @Getter Treaty treaty;
	private @Getter Connection conn;
	private @Getter boolean safe;
	private @Getter ResultSet results;
	private @Getter long freeFactories;
	private @Getter long lastLogin;
	private LinkedHashMap<String, Double> coalProduction = null;
	private LinkedHashMap<String, Double> ironProduction = null;
	private LinkedHashMap<String, Double> oilProduction = null;
	private LinkedHashMap<String, Double> steelProduction = null;
	private LinkedHashMap<String, Double> nitrogenProduction = null;
	private LinkedHashMap<String, Double> researchProduction = null;
	private @Getter LinkedHashMap<Integer, Production> production;
	private LinkedHashMap<String, LinkedHashMap<String, Double>> allProductions = null;
	private long landUsage = -1;
	private HashMap<String, Double> totalProductionCosts = null;

	/**
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

	public static Nation getNationById(Connection conn, int id) throws SQLException
	{
		PreparedStatement get = conn.prepareStatement("SELECT * FROM cloc_login\n" +
				"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
				"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
				"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
				"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
				"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
				"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
				"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
				"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
				"LEFT JOIN cloc_treaties_members treaty_member ON cloc_login.id = treaty_member.nation_id\n" +
				"LEFT JOIN cloc_treaties treaty ON treaty_member.alliance_id = treaty.id\n" +
				"WHERE cloc_login.id=?" );
		get.setInt(1, id);
		ResultSet results = get.executeQuery();
		if(!results.first())
		{
			return null;
		}
		else
		{
			return new Nation(id, results);
		}
	}

	/**
	 * Returns an unordered Collection of all Nations in the database matching the where clause fed into the method
	 *
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
				"LEFT JOIN cloc_treaties_members treaty_member ON cloc_login.id = treaty_member.nation_id\n" +
				"LEFT JOIN cloc_treaties treaty ON treaty_member.alliance_id = treaty.id \n" +
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
	 *
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
				"LEFT JOIN cloc_treaties_members treaty_member ON cloc_login.id = treaty_member.nation_id\n" +
				"LEFT JOIN cloc_treaties treaty ON treaty_member.alliance_id = treaty.id \n" +
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
	 *
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
				"LEFT JOIN cloc_treaties_members treaty_member ON cloc_login.id = treaty_member.nation_id\n" +
				"LEFT JOIN cloc_treaties treaty ON treaty_member.alliance_id = treaty.id \n" +
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
	 *
	 * @param conn The conn object to use
	 * @param id   The id of the nation being loaded
	 * @param safe Whether the contents of the returned Nation object should be editable
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
			getProduction = conn.prepareStatement("SELECT * FROM production " +
					"LEFT JOIN factories ON production.id=factories.production_id " +
					"WHERE production.owner=? FOR UPDATE");
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
			getProduction = conn.prepareStatement("SELECT * FROM production " +
					"LEFT JOIN factories ON production.id=factories.production_id " +
					"WHERE production.owner=?");
			attacker = conn.prepareStatement("SELECT defender FROM cloc_war WHERE attacker=? AND end=-1");
			defender = conn.prepareStatement("SELECT attacker FROM cloc_war WHERE defender=? AND end=-1");
		}
		get.setInt(1, id);
		ResultSet main = get.executeQuery();
		main.first();
		this.results = main;
		this.lastLogin = main.getLong("last_login");
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
		HashMap<String, Double> resources = new HashMap<>();
		resources.put("steel", economy.getSteel());
		resources.put("nitrogen", economy.getNitrogen());
		resources.put("oil", economy.getOil());
		int usedFactories = 0;
		boolean any = resultsProduction.first();
		if(any)
		{
			int lastId = resultsProduction.getInt("production_id");
			String lastProduction = resultsProduction.getString("production");
			int lastProgress = resultsProduction.getInt("progress");
			HashMap<Integer, Factory> map = new HashMap<>();
			do
			{
				usedFactories++;
				if(lastId == resultsProduction.getInt("production_id"))
				{
					map.put(resultsProduction.getInt("factories.id"),
							new Factory(resultsProduction.getInt("factories.id"),
									resultsProduction.getInt("owner"),
									resultsProduction.getInt("city_id"),
									resultsProduction.getInt("production_id"),
									resultsProduction.getInt("efficiency")));
				}
				else
				{
					if(!map.isEmpty())
					{
						production.put(lastId, new Production(lastId, this.id, map, lastProduction, lastProgress, resources));
						for(HashMap.Entry<String, Double> entry : production.get(lastId).getRequiredResources().entrySet())
						{
							resources.compute(entry.getKey(), (key, value) -> value = value - entry.getValue());
						}
					}
					map = new HashMap<>();
					map.put(resultsProduction.getInt("id"),
							new Factory(resultsProduction.getInt("id"),
									resultsProduction.getInt("owner"),
									resultsProduction.getInt("city_id"),
									resultsProduction.getInt("production_id"),
									resultsProduction.getInt("efficiency")));
				}
				lastId = resultsProduction.getInt("production_id");
				lastProduction = resultsProduction.getString("production");
				lastProgress = resultsProduction.getInt("progress");
			}
			while(resultsProduction.next());
			production.put(lastId, new Production(lastId, this.id, map, lastProduction, lastProgress, resources));
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
			offensive = null;
		}
		else
		{
			this.offensive = getNationById(conn, resultsAttacker.getInt(1));
		}
		if(!resultsDefender.first())
		{
			defensive = null;
		}
		else
		{
			this.defensive = getNationById(conn, resultsDefender.getInt(1));
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
	 *
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
		if(results.getInt("treaty.id") != 0)
		{
			this.treaty = new Treaty(results.getInt("treaty.id"), results);
		}
	}

	/**
	 * Attempts to join a treaty in the standard way:
	 * by checking if this nation has an invite and letting them in if they do
	 *
	 * @param id      The id of the Treaty to join
	 * @param founder Whether this Nation should be roled as founder of the treaty or not
	 * @throws SQLException If a database error occurs
	 */
	public void joinTreaty(Integer id, boolean founder) throws SQLException
	{
		this.joinTreaty(id, founder, false);
	}

	/**
	 * Attempts to join a treaty
	 *
	 * @param id           The id of the Treaty to join
	 * @param founder      Whether this nation should be roled as Treaty Founder
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
	 *
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
	 *
	 * @param nation The Nation to check against
	 * @return null if the nation can declare war on the other, the reason why it cannot otherwise
	 */
	public String canDeclareWar(Nation nation)
	{
		if(this.offensive != null)
		{
			return Responses.cannotWar("alreadyAtWar");
		}
		else if(nation.defensive != null)
		{
			return Responses.cannotWar("alreadyAtWar2");
		}
		else if(this.isAtWarWith(nation))
		{
			return Responses.cannotWar("alreadyAtWar3");
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

	public void damagePopulation(long amount)
	{
		for(Integer integer : cities.getCities().keySet())
		{
			City city = cities.getCities().get(integer);
			city.setPopulation(city.getPopulation() - (long)((double)city.getPopulation() / (double)this.getTotalPopulation()) * amount);
		}
	}

	/**
	 * Checks if this nation is at war with another nation
	 *
	 * @param nation The nation to check
	 * @return True if they are at war, false if they are not
	 */
	public boolean isAtWarWith(Nation nation)
	{
		return (this.defensive != null && this.defensive.getId() == nation.getId())
				|| (this.offensive != null && this.offensive.getId() == nation.getId());
	}

	/**
	 * Checks if this nation is in any war
	 *
	 * @return True if the nation is at war, false otherwise
	 */
	public boolean isAtWar()
	{
		return defensive != null || offensive != null;
	}

	/**
	 * Declares war on the specified Nation
	 *
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
			declare.setLong(3, Util.month);
			declare.execute();
			conn.commit();
		}
	}

	public String sendPeace(Nation receiver) throws SQLException
	{
		PreparedStatement statement = this.conn.prepareStatement("SELECT attacker, defender, peace FROM cloc_war " +
				"WHERE ((attacker=? AND defender=?) OR (attacker=? AND defender=?)) AND end=-1");
		statement.setInt(1, this.id);
		statement.setInt(2, receiver.id);
		statement.setInt(3, receiver.id);
		statement.setInt(4, this.id);
		ResultSet results = statement.executeQuery();
		results.first();
		if(results.getInt("peace") == -1)
		{
			PreparedStatement sendPeace = this.conn.prepareStatement("UPDATE cloc_war SET peace=? " +
					"WHERE ((attacker=? AND defender=?) OR (attacker=? AND defender=?)) AND end=-1");
			sendPeace.setInt(1, this.id);
			sendPeace.setInt(2, this.id);
			sendPeace.setInt(3, receiver.id);
			sendPeace.setInt(4, receiver.id);
			sendPeace.setInt(5, this.id);
			sendPeace.execute();
			News.sendNews(this.conn, this.id, receiver.id, News.createMessage(News.ID_SEND_PEACE, this.getNationUrl()));
			return Responses.peaceSent();
		}
		else if(results.getInt("peace") == this.id)
		{
			return Responses.peaceAlreadySent();
		}
		else if(results.getInt("peace") == receiver.id)
		{
			PreparedStatement sendPeace = this.conn.prepareStatement("UPDATE cloc_war SET end=? " +
					"WHERE ((attacker=? AND defender=?) OR (attacker=? AND defender=?)) AND end=-1");
			sendPeace.setLong(1, Util.month);
			sendPeace.setInt(2, this.id);
			sendPeace.setInt(3, receiver.id);
			sendPeace.setInt(4, receiver.id);
			sendPeace.setInt(5, this.id);
			sendPeace.execute();
			News.sendNews(this.conn, this.id, receiver.id, News.createMessage(News.ID_PEACE_ACCEPTED, this.getNationUrl()));
			return Responses.peaceAccepted();
		}
		else
		{
			return Responses.genericError();
		}
	}

	/**
	 * Calculates the free land available in this Nation
	 *
	 * @return The free land available
	 */
	public long getFreeLand()
	{
		return this.domestic.getLand() - this.getTotalLandUsage();
	}

	/**
	 * Calculates the total land usage of this Nation
	 *
	 * @return The total land usage
	 */
	public long getTotalLandUsage()
	{
		if(landUsage == -1)
		{
			long total = 0;
			for(City city : cities.getCities().values())
			{
				for(Long longboi : city.getLandUsage().values())
				{
					total += longboi;
				}
			}
			this.landUsage = total;
		}
		return landUsage;
	}

	public HashMap<String, Double> getTotalProductionCosts()
	{
		if(totalProductionCosts == null)
		{
			HashMap<String, Double> map = new HashMap<>();
			for(Production production1 : production.values())
			{
				for(HashMap.Entry<String, Double> entry : production1.getRequiredResources().entrySet())
				{
					if(map.containsKey(entry.getKey()))
					{
						map.compute(entry.getKey(), (key2, value) -> value = value + entry.getValue());
					}
					else
					{
						map.put(entry.getKey(), entry.getValue());
					}
				}
			}
			map.forEach((key, value) -> map.compute(key, (k, v) -> v = v * -1.0));
			totalProductionCosts = map;
		}
		return totalProductionCosts;
	}

	/**
	 * Gets the total amount of military factories in all cities in this nation
	 *
	 * @return The military factory count
	 */
	public long getTotalMilitaryFactories()
	{
		long total = 0;
		for(City city : cities.getCities().values())
		{
			total += city.getIndustryMilitary();
		}
		return total;
	}

	/**
	 * Gets the total amount of factories in all cities in this nation
	 *
	 * @return The factory count
	 */
	public long getTotalFactories()
	{
		long total = 0;
		for(City city : cities.getCities().values())
		{
			total += city.getIndustryMilitary() + city.getIndustryNitrogen() + city.getIndustryCivilian();
		}
		return total;
	}

	public long getTotalPopulation()
	{
		long total = 0;
		for(City city : cities.getCities().values())
		{
			total += city.getPopulation();
		}
		return total;
	}

	/**
	 * Calculates the total manpower a nation has
	 *
	 * @return The total manpower of the nation
	 */
	public long getTotalManpower()
	{
		long lostManpower = domestic.getManpowerLost();
		long manpower = this.getTotalPopulation();
		switch(policy.getManpower())
		{
			case DISARMED_MANPOWER:
				manpower *= 0.05;
				break;
			case VOLUNTEER_MANPOWER:
				manpower *= 0.10;
				break;
			case RECRUITMENT_MANPOWER:
				manpower *= 0.20;
				break;
			case MANDATORY_MANPOWER:
				manpower *= 0.30;
				break;
			case SCRAPING_THE_BARREL_MANPOWER:
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
	 *
	 * @return A HashMap containing manpower usage
	 */
	public LinkedHashMap<String, Long> getUsedManpower()
	{
		LinkedHashMap<String, Long> map = new LinkedHashMap<>();
		long navy = this.military.getBattleships() +
				this.military.getCruisers() +
				this.military.getPreBattleships() +
				this.military.getCruisers() +
				this.military.getDestroyers() +
				this.military.getSubmarines();
		navy *= 500;
		long airforce = this.military.getBiplaneFighters() +
				this.military.getReconPlanes() +
				this.military.getReconBalloons() +
				this.military.getTriplaneFighters() +
				this.military.getMonoplaneFighters() +
				this.military.getBombers() +
				this.military.getZeppelins();
		airforce *= 50;
		long army = this.army.getSize();
		army *= 1000;
		map.put("manpower.navy", -navy);
		map.put("manpower.airforce", -airforce);
		map.put("manpower.army", -army);
		map.put("manpower.net", navy + airforce + army);
		return map;
	}

	/**
	 * Calculates the amount of free manpower this nation has available
	 *
	 * @return The amount of free manpower
	 */
	public long getFreeManpower()
	{
		return this.getTotalManpower() - this.getUsedManpower().get("manpower.net");
	}

	public LinkedHashMap<String, Double> getAllResources()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		map.put("Budget", this.economy.getBudget());
		map.put("Food", this.economy.getFood());
		map.put("Coal", this.economy.getCoal());
		map.put("Iron", this.economy.getIron());
		map.put("Oil", this.economy.getOil());
		map.put("Steel", this.economy.getSteel());
		map.put("Nitrogen", this.economy.getNitrogen());
		map.put("Research", this.economy.getResearch());
		return map;
	}

	private static void extractionEconBoosts(LinkedHashMap<String, Double> map, Policy policy)
	{
		double bonus;
		if(policy == Policy.WAR_ECONOMY || policy == Policy.CIVILIAN_ECONOMY)
		{
			bonus = -0.1;
		}
		else if(policy == Policy.AGRARIAN_ECONOMY)
		{
			bonus = -0.05;
		}
		else if(policy == Policy.INDUSTRY_ECONOMY)
		{
			bonus = -0.15;
		}
		else
		{
			bonus = 0.1;
		}
		computeBonus(map, "resource.economy_type", bonus);
	}

	private static void factoryEconBoosts(LinkedHashMap<String, Double> map, Policy policy)
	{
		double bonus;
		if(policy == Policy.WAR_ECONOMY || policy == Policy.CIVILIAN_ECONOMY || policy == Policy.AGRARIAN_ECONOMY)
		{
			bonus = -0.1;
		}
		else if(policy == Policy.INDUSTRY_ECONOMY)
		{
			bonus = 0.1;
		}
		else
		{
			bonus = -0.15;
		}
		computeBonus(map, "resource.economy_type", bonus);
	}

	public void doStabilityResourceEffect(LinkedHashMap<String, Double> map)
	{
		double stabilityEffect = this.getDomestic().getStability() - 50;
		if(stabilityEffect < 0)
		{
			stabilityEffect = stabilityEffect / (3.0 + 1.0/3.0);
		}
		else
		{
			stabilityEffect = stabilityEffect / 5.0;
		}
		final double stabilityIncrease = (stabilityEffect / 100.0);
		computeBonus(map, "resource.stability", stabilityIncrease);
	}

	private static void computeBonus(LinkedHashMap<String, Double> map, String key, double bonus)
	{
		map.put(key, map.get("resource.total") * bonus);
		map.compute("resource.net", (k, v) -> v += (map.get("resource.total") * bonus));
		if(bonus > 0)
		{
			map.compute("resource.total", (k, v) -> v += v * bonus);
		}

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
	 *
	 * @return A LinkedHashMap containing the total coal production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalCoalProduction()
	{
		if(coalProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getCoalProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			extractionEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
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
	 *
	 * @return A HashMap containing the total iron production of this Nation
	 */
	public LinkedHashMap<String, Double> getTotalIronProduction()
	{
		if(ironProduction == null)
		{
			LinkedHashMap<String, Double> map = new LinkedHashMap<>();
			for(City city : this.getCities().getCities().values())
			{
				LinkedHashMap<String, Double> cityMap = city.getIronProduction();
				for(HashMap.Entry<String, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			extractionEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
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
	 *
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
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			extractionEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			if(getTotalProductionCosts().get("oil") != null)
			{
				map.put("resource.mil_factory_demands", getTotalProductionCosts().get("oil"));
				map.compute("resource.net", (k, v) -> v = v + getTotalProductionCosts().get("oil"));
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
	 *
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
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			factoryEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			if(getTotalProductionCosts().get("steel") != null)
			{
				map.put("resource.mil_factory_demands", getTotalProductionCosts().get("steel"));
				map.compute("resource.net", (k, v) -> v = v + getTotalProductionCosts().get("steel"));
			}
			if(this.hasTech(Technologies.FARMING_MACHINES) || this.hasTech(Technologies.ADVANCED_FARMING_MACHINES))
			{
				double amount = -this.domestic.getLand() / TechnologyFarmingMachines.LAND_PER_STEEL;
				if(this.getPolicy().getFarmingSubsidies() == Policy.NO_SUBSIDIES_FARMING)
				{
					amount *= 0.25;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.REDUCED_SUBSIDIES_FARMING)
				{
					amount *= 0.75;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.SUBSTANTIAL_SUBSIDIES_FARMING)
				{
					amount *= 1.5;
				}
				map.put("resource.farming_demands", amount);
				double finalAmount = amount;
				map.compute("resource.net", (k, v) -> v = v + finalAmount);
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
	 *
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
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			factoryEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			if(getTotalProductionCosts().get("nitrogen") != null)
			{
				map.put("resource.mil_factory_demands", getTotalProductionCosts().get("nitrogen"));
				map.compute("resource.net", (k, v) -> v = v + getTotalProductionCosts().get("nitrogen"));
			}
			if(this.hasTech(Technologies.ARTIFICIAL_FERTILIZER) || this.hasTech(Technologies.ADVANCED_ARTIFICIAL_FERTILIZER))
			{
				double amount = -this.domestic.getLand() / TechnologyArtificialFertilizer.LAND_PER_NITROGEN;
				if(this.getPolicy().getFarmingSubsidies() == Policy.NO_SUBSIDIES_FARMING)
				{
					amount *= 0.25;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.REDUCED_SUBSIDIES_FARMING)
				{
					amount *= 0.75;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.SUBSTANTIAL_SUBSIDIES_FARMING)
				{
					amount *= 1.5;
				}
				map.put("resource.farming_demands", amount);
				double finalAmount = amount;
				map.compute("resource.net", (k, v) -> v = v + finalAmount);
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
	 *
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
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			doStabilityResourceEffect(map);
			researchProduction = map;
		}
		return researchProduction;
	}

	/**
	 * Calculates out the food production of this Nation, stored in a HashMap with keys:
	 * <ul>
	 *     <li>farming</li>
	 *     <li>costs</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A HashMap containing food production
	 */
	public LinkedHashMap<String, Double> getFoodProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double farming = (this.getFreeLand() / 250.0) * this.getBaseFoodProduction().get("farming.net");
		if(farming < 0)
			farming = 0;
		double tech = 0;
		if(this.hasTech(Technologies.ADVANCED_ARTIFICIAL_FERTILIZER) && this.getTotalNitrogenProduction().get("resource.net") + this.getEconomy().getNitrogen() >= 0)
		{
			tech = farming * TechnologyAdvancedArtificialFertilizer.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.ARTIFICIAL_FERTILIZER) && this.getTotalNitrogenProduction().get("resource.net") + this.getEconomy().getNitrogen() >= 0)
		{
			tech = farming * TechnologyArtificialFertilizer.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.BASIC_ARTIFICIAL_FERTILIZER))
		{
			tech = farming * TechnologyBasicArtificialFertilizer.FOOD_GAIN;
		}
		if(this.hasTech(Technologies.ADVANCED_FARMING_MACHINES) && this.getTotalSteelProduction().get("resource.net") + this.getEconomy().getSteel() > 0)
		{
			tech += farming * TechnologyAdvancedFarmingMachines.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.FARMING_MACHINES) && this.getTotalSteelProduction().get("resource.net") + this.getEconomy().getSteel() > 0)
		{
			tech += farming * TechnologyFarmingMachines.FOOD_GAIN;
		}
		if(this.getPolicy().getFarmingSubsidies() == Policy.NO_SUBSIDIES_FARMING)
		{
			tech *= 0.5;
		}
		else if(this.getPolicy().getFarmingSubsidies() == Policy.REDUCED_SUBSIDIES_FARMING)
		{
			tech *= 0.85;
		}
		else if(this.getPolicy().getFarmingSubsidies() == Policy.SUBSTANTIAL_SUBSIDIES_FARMING)
		{
			tech *= 1.5;
		}
		double consumption = -this.getTotalPopulation() / 2000.0;
		double economy = 0.0;
		double food = 0.0;
		double total = farming;
		double net = farming + consumption;
		if(this.policy.getEconomy() == Policy.AGRARIAN_ECONOMY)
		{
			economy = farming * 0.15;
			total += economy;
		}
		else if(this.policy.getEconomy() == Policy.WAR_ECONOMY)
		{
			economy = -farming * 0.1;
		}
		if(this.policy.getFood() == Policy.FREE_FOOD)
		{
			food = consumption * 0.35;
		}
		else if(this.policy.getFood() == Policy.RATIONING_FOOD)
		{
			food = -consumption * 0.35;
		}
		net += food + economy + tech;
		map.put("resource.farming", farming);
		map.put("resource.technology", tech);
		map.put("resource.consumption", consumption);
		map.put("resource.economy_type", economy);
		map.put("resource.food_type", food);
		map.put("resource.net", net);
		map.put("resource.total", total);
		doStabilityResourceEffect(map);
		return map;
	}

	/**
	 * Calculates the total production of all types for this nation
	 * The production is returned as a HashMap of HashMaps, with the outer HashMap containing the keys:
	 * <ul>
	 *     <li>budget</li>
	 *     <li>food</li>
	 *     <li>coal</li>
	 *     <li>iron</li>
	 *     <li>oil</li>
	 *     <li>steel</li>
	 *     <li>nitrogen</li>
	 *     <li>research</li>
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
	 *
	 * @return A HashMap containing the total weapons production of this Nation
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Double>> getAllTotalProductions()
	{
		if(allProductions == null)
		{
			LinkedHashMap<String, LinkedHashMap<String, Double>> map = new LinkedHashMap<>();
			LinkedHashMap<String, Double> budget = new LinkedHashMap<>();
			budget.put("resource.gdp", this.getBudgetChange());
			map.put("Budget", budget);
			map.put("Food", this.getFoodProduction());
			map.put("Coal", this.getTotalCoalProduction());
			map.put("Iron", this.getTotalIronProduction());
			map.put("Oil", this.getTotalOilProduction());
			map.put("Steel", this.getTotalSteelProduction());
			map.put("Nitrogen", this.getTotalNitrogenProduction());
			map.put("Research", this.getTotalResearchProduction());
			allProductions = map;
		}
		return allProductions;
	}

	public LinkedHashMap<String, Double> getBaseFoodProduction()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double base = 1.0;
		double tech = domestic.getFarmTechnology() / 100.0;
		map.put("farming.base", base);
		map.put("farming.total", base);
		map.put("farming.net", base);
		return map;
	}

	/**
	 * Gets the total equipment of the army
	 * @return The total equipment
	 */
	public long getTotalInfantryEquipment()
	{
		long total = 0;
		for(Equipment equipment : Equipment.getInfantryEquipment())
		{
			total += this.getEquipment(equipment);
		}
		return total;
	}

	public long getTotalArmor()
	{
		long total = 0;
		for(Equipment equipment : Equipment.getArmor())
		{
			total += this.getEquipment(equipment);
		}
		return total;
	}

	public long getTotalArtillery()
	{
		long total = 0;
		for(Equipment equipment : Equipment.getArtillery())
		{
			total += this.getEquipment(equipment);
		}
		return total;
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

	public int getFighterCount()
	{
		int total = 0;
		for(Fighter fighter : Fighter.values())
		{
			total += this.getFighter(fighter);
		}
		return total;
	}

	public int getBomberCount()
	{
		int total = 0;
		for(Bomber bomber : Bomber.values())
		{
			total += this.getBomber(bomber);
		}
		return total;
	}

	public int getReconCount()
	{
		int total = 0;
		for(ReconPlane plane : ReconPlane.values())
		{
			total += this.getReconPlane(plane);
		}
		return total;
	}

	public int getEquipment(Equipment equipment)
	{
		switch(equipment)
		{
			case MUSKET:
				return this.getArmy().getMusket();
			case RIFLED_MUSKET:
				return this.getArmy().getRifledMusket();
			case SINGLE_SHOT_RIFLE:
				return this.getArmy().getSingleShot();
			case NEEDLE_NOSE:
				return this.getArmy().getNeedleNose();
			case BOLT_ACTION_MANUAL:
				return this.getArmy().getBoltActionManual();
			case BOLT_ACTION_CLIP:
				return this.getArmy().getBoltActionClip();
			case STRAIGHT_PULL_RIFLE:
				return this.getArmy().getStraightPull();
			case SEMI_AUTOMATIC:
				return this.getArmy().getSemiAuto();
			case MACHINE_GUN:
				return this.getArmy().getMachineGun();
			case ARTILLERY:
				return this.getArmy().getArtillery();
			case TANK:
				return this.getArmy().getTank();
			default:
				return 0;

		}
	}

	public int getFighter(Fighter fighter)
	{
		switch(fighter)
		{
			case BIPLANE_FIGHTER:
				return this.getMilitary().getBiplaneFighters();
			case TRIPLANE_FIGHTER:
				return this.getMilitary().getTriplaneFighters();
			case MONOPLANE_FIGHTER:
				return this.getMilitary().getMonoplaneFighters();
			default:
				return 0;
		}
	}

	public void setFighter(Fighter fighter, int value)
	{
		switch(fighter)
		{
			case BIPLANE_FIGHTER:
				this.getMilitary().setBiplaneFighters(value);
				break;
			case TRIPLANE_FIGHTER:
				this.getMilitary().setTriplaneFighters(value);
				break;
			case MONOPLANE_FIGHTER:
				this.getMilitary().setMonoplaneFighters(value);
				break;
		}
	}

	public int getBomber(Bomber bomber)
	{
		switch(bomber)
		{
			case ZEPPELIN_BOMBER:
				return this.getMilitary().getZeppelins();
			case BOMBER:
				return this.getMilitary().getBombers();
			default:
				return 0;
		}
	}

	public void setBomber(Bomber bomber, int value)
	{
		switch(bomber)
		{
			case ZEPPELIN_BOMBER:
				this.getMilitary().setZeppelins(value);
				break;
			case BOMBER:
				this.getMilitary().setBombers(value);
				break;
		}
	}

	public int getReconPlane(ReconPlane plane)
	{
		switch(plane)
		{
			case RECON_BALLOON:
				return this.getMilitary().getReconBalloons();
			case RECON_PLANE:
				return this.getMilitary().getReconPlanes();
			default:
				return 0;
		}
	}

	/**
	 * Calculates the power of an army based on it's army.getSize(), technology level, army.getTraining(), and artillery
	 * @return The army's power
	 */
	public double getPower()
	{
		double power = 0e0;
		long requiredEquipment = (long)army.getSize() * 1000L;
		for(Equipment equipment : Equipment.getInfantryEquipment())
		{
			if(requiredEquipment > 0)
			{
				if(this.getEquipment(equipment) > 0)
				{
					if(this.getEquipment(equipment) > requiredEquipment)
					{
						power += requiredEquipment * equipment.getPower();
						requiredEquipment = 0;
					}
					else
					{
						power += this.getEquipment(equipment) * equipment.getPower();
						requiredEquipment -= this.getEquipment(equipment);
					}
				}
			}
		}
		//Sticks and stones are better than nothing
		power += requiredEquipment * 0.5;
		power *= java.lang.Math.sqrt(army.getTraining() + 1);
		return Math.sqrt(power);
	}

	/**
	 * Calculates the ability of this nations army to break through defenses
	 * and generally attack better
	 * <br>
	 * Represented as a percentage, at 0% ignores no defender bonuses, at 100% ignores all defender bonuses
	 * @return The army's breakthrough
	 */
	public double getBreakthrough()
	{
		int max = this.getArmy().getSize() * 5;
		long currentTanks = this.getTotalArmor();
		if(currentTanks > max)
		{
			currentTanks = max;
		}
		double ratio = (double)currentTanks / (double)max;
		long currentArtillery = this.getTotalArtillery();
		if(currentArtillery > max)
		{
			currentArtillery = max;
		}
		double artillery = ((double)currentArtillery / (double)max);
		artillery /= 2;
		double recon = 1;
		int maxRecon = max / 5;
		int currentRecon = this.getReconCount();
		if(currentRecon > maxRecon)
		{
			currentRecon = maxRecon;
		}
		List<ReconPlane> list = Arrays.asList(ReconPlane.values());
		Collections.reverse(list);
		for(ReconPlane plane : list)
		{
			if(this.getReconPlane(plane) > currentRecon)
			{
				recon += (currentRecon * plane.getPower());
				currentRecon = 0;
			}
			else
			{
				recon += (this.getReconPlane(plane) * plane.getPower());
				currentRecon -= this.getReconPlane(plane);
			}
		}
		recon = 1 + recon / maxRecon;
		artillery *= recon;
		ratio += artillery;
		return ratio;
	}

	/**
	 * Calculated the ability of this nations army to resist attacks
	 * and generally better defense
	 * <br>
 *     Represented as a percentage, 0% adds no defense bonus, 100% doubles defender strength
	 * @return The army's defense
	 */
	public double getDefense()
	{
		double ratio = this.getArmy().getFortification() / 10000.0;
		return 1 + ratio;
	}

	public int getMinimumFortificationLevel()
	{
		return 0;
	}

	public int getMaximumFortificationLevel()
	{
		return getMaximumFortificationLevelMap().get("fortification_max.net");
	}

	public LinkedHashMap<String, Integer> getMaximumFortificationLevelMap()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		int tech = 2000;
		if(this.hasTech(Technologies.BASIC_TRENCHES))
			tech += TechnologyTrenches.BONUS * 100;
		if(this.hasTech(Technologies.BASIC_FORTIFICATIONS))
			tech += TechnologyFortifications.BONUS * 100;
		if(this.hasTech(Technologies.ADVANCED_TRENCHES))
			tech += TechnologyAdvancedTrenches.BONUS * 100;
		if(this.hasTech(Technologies.ADVANCED_FORTIFICATIONS))
			tech += TechnologyReinforcedConcrete.BONUS * 100;
		if(this.hasTech(Technologies.MOBILE_DEFENSE))
			tech += TechnologyMobileDefense.BONUS * 100;
		int policy = tech;
		switch(this.policy.getFortification())
		{
			case UNOCCUPIED_FORTIFICATION:
				policy *= 0.15;
				break;
			case MINIMAL_FUNDING_FORTIFICATION:
				policy *= 0.4;
				break;
			case PARTIAL_FUNDING_FORTIFICATION:
				policy *= 0.75;
				break;
		}
		policy = tech - policy;
		policy = -policy;
		int net = policy + tech;
		map.put("fortification_max.tech", tech);
		map.put("fortification_max.policy", policy);
		map.put("fortification_max.net", net);
		return map;
	}

	/**
	 * Calculates the ability of this nation to shoot down other planes
	 * @param attackingOtherAirforce True if calculating the power of this airforce to
	 *                               fight other airforces specifically
	 * @return The fighter power
	 */
	public double getFighterPower(boolean attackingOtherAirforce)
	{
		double power = 0;
		for(Fighter fighter : Fighter.values())
		{
			power += (this.getFighter(fighter) * fighter.getPower());
		}
		if(attackingOtherAirforce)
		{
			for(Bomber bomber : Bomber.values())
			{
				power += (this.getBomber(bomber) * bomber.getBombingPower() / 2);
			}
		}
		for(Bomber bomber : Bomber.values())
		{
			power += (this.getBomber(bomber) * bomber.getDefense());
		}
		return power / 10;
	}

	/**
	 * Calculates the ability of this nation when bombing targets
	 * @return The bomber power
	 */
	public double getBomberPower()
	{
		double power = 0;
		for(Bomber bomber : Bomber.values())
		{
			power += (this.getBomber(bomber) * bomber.getBombingPower());
		}
		return power / 10;
	}

	public long getTotalShipCount()
	{
		long power = 0;
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
	 * @param ourPower the power of our army
	 * @param theirPower the power of the other army
	 * @return The casualties taken
	 */
	public int getCasualties(double ourPower, double theirPower)
	{
		double powerDiff = theirPower / ourPower;
		double armyHp = this.army.getSize() * 100.0;
		armyHp -= (powerDiff * theirPower);
		return (int)(this.army.getSize() - (armyHp / 100.0));
	}

	/**
	 * Returns the cash-cost of a decision<br>
	 * This contains most policy costs that would show up under decisions->category
	 * but does not include any that cost another resource/other resources
	 * @param decision The ID of the decision
	 * @return The cost of the policy
	 */
	public long getDecisionCost(Decision decision)
	{
		switch(decision)
		{
			case PROPAGANDA:
				return (long)(this.economy.getGdp() / 2L * (this.domestic.getApproval() / 100.0));
			case WAR_PROPAGANDA:
				return getDecisionCost(Decision.PROPAGANDA) / 2L;
			case LAND_CLEARANCE:
				return (long)(this.economy.getGdp() * 2L);
			case ALIGN_CENTRAL_POWERS:
			case ALIGN_ENTENTE:
			case ALIGN_NEUTRAL:
			case PARDON_CRIMINALS:
			case INCREASE_ARREST_QUOTAS:
				return 100;
			case TRAIN:
				return (long)this.army.getSize() * (long)this.army.getSize() * (long)this.army.getTraining() / 200L;
			case FORM_TREATY:
				return 500;
			case FORTIFY:
				return (long)(this.getMaximumFortificationLevel() / 100.0 * ((double)this.getArmy().getFortification() / (double)this.getMaximumFortificationLevel()));
			default:
				return 0;
		}
	}

	public String getDecisionCostDisplayString(Decision decision)
	{
		long cost = getDecisionCost(decision);
		if(cost == 0)
		{
			return "Free";
		}
		else
		{
			switch(decision)
			{
				case FORTIFY:
					return Util.formatNumber(cost) + " Steel";
				default:
					return "$" + Util.formatNumber(cost);
			}
		}
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
	 * Checks whether the nation has researched a tech
	 * @param tech The Technology
	 * @return True if it's researched, false otherwise
	 */
	public boolean hasTech(Technology tech)
	{
		return this.tech.getResearchedTechs().contains(tech.getTechnology());
	}

	/**
	 * Checks whether the nation has researched a tech
	 * @param tech The Technology
	 * @return True if it's researched, false otherwise
	 */
	public boolean hasTech(Technologies tech)
	{
		return this.tech.getResearchedTechs().contains(tech);
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
	 * Gets one of the nations productions by id
	 * @param id the id of the production
	 * @return The production
	 */
	public Production getProductionById(int id)
	{
		return production.get(id);
	}

	public String getNationUrl()
	{
		return "<a href=\"/nation/" + id + "\"><b>" + this.cosmetic.getNationName() + "</b></a>";
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
		String statement = "";
		for(Production production : this.production.values())
		{
			if(production.getIc(this.getPolicy().getEconomy()) > 0)
			{
				production.getRequiredResources().forEach((k, v) -> {
					double amount = v / (double) Time.daysPerMonth[Util.currentMonth];
					switch(k)
					{
						case "steel":
							this.getEconomy().setSteel(this.getEconomy().getSteel() - amount);
							break;
						case "oil":
							this.getEconomy().setSteel(this.getEconomy().getOil() - amount);
							break;
						case "nitrogen":
							this.getEconomy().setNitrogen(this.getEconomy().getNitrogen() - amount);
							break;
					}
				});
				double productionIc = production.getIc(this.policy.getEconomy());
				double ic = productionIc + (production.getProgress() / 100.0);
				int amount = (int) (ic / production.getProductionAsTechnology().getTechnology().getProductionICCost());
				int leftover = (int) ((ic - (amount * production.getProductionAsTechnology().getTechnology().getProductionICCost())) * 100);
				production.setProgress(leftover);
				statement += production.getProductionAsTechnology().getTechnology().getProductionName() + "="
						+ production.getProductionAsTechnology().getTechnology().getProductionName() + "+" + amount + ", ";
			}
		}
		if(!statement.isEmpty())
		{
			statement = "UPDATE cloc_army, cloc_military SET ".concat(statement)
					.concat("WHERE cloc_army.id=? AND cloc_military.id=? AND cloc_army.id=cloc_military.id");
			statement = statement.replace(", WHERE", " WHERE");
			PreparedStatement update = conn.prepareStatement(statement);
			update.setInt(1, this.id);
			update.setInt(2, this.id);
			update.execute();
		}
	}

	public LinkedHashMap<String, Double> getFortificationChange()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double base = Math.min(
				this.army.getFortification() + (0.1 * ((double)this.getMaximumFortificationLevel() / (this.army.getFortification() / 100.0))),
				this.getMaximumFortificationLevel()
		);
		base -= this.getArmy().getFortification();
		if(base > 50)
		{
			base = 50;
		}
		if(base < -100)
		{
			base = -100;
		}
		double bonus = 0;
		if(base > 0)
		{
			switch(this.policy.getFortification())
			{
				case UNOCCUPIED_FORTIFICATION:
					bonus = -base * 1.25;
					break;
				case MINIMAL_FUNDING_FORTIFICATION:
					bonus = -base * 0.5;
					break;
				case PARTIAL_FUNDING_FORTIFICATION:
					break;
				case FULL_FUNDING_FORTIFICATION:
					bonus = base * 0.25;
					break;
				case MAX_FORTIFICATION:
					bonus = base * 0.5;
			}
			map.put("fortification.base_above", base);
		}
		else if(base < 0)
		{
			switch(this.policy.getFortification())
			{
				case UNOCCUPIED_FORTIFICATION:
					bonus = base;
					break;
				case MINIMAL_FUNDING_FORTIFICATION:
					bonus = base * 0.5;
					break;
				case PARTIAL_FUNDING_FORTIFICATION:
					break;
				case FULL_FUNDING_FORTIFICATION:
					bonus = -base * 0.25;
					break;
				case MAX_FORTIFICATION:
					bonus = -base * 0.5;
			}
			map.put("fortification.base_below", base);
		}
		double net = base + bonus;
		map.put("fortification.bonus", bonus);
		map.put("fortification.net", net);
		return map;
	}

	public LinkedHashMap<String, Integer> getApprovalChange()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		int famine = (int)this.getFamineLevel();
		if(famine < 0)
		{
			map.put("approval.famine", famine);
		}
		map.put("approval.net", famine);
		return map;
	}

	public LinkedHashMap<String, Integer> getStabilityChange()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		int approval = this.domestic.getApproval() / 20 - 2;
		int famine = (int)this.getFamineLevel();
		int growth = 0;
		if(this.getEconomy().getGrowth() < 0)
		{
			growth = -1;
		}
		if(this.getEconomy().getGrowth() < -5)
		{
			growth = -2;
		}
		if(this.getEconomy().getGrowth() < -10)
		{
			growth = -5;
		}
		if(this.getEconomy().getGrowth() < -20)
		{
			growth = -10;
		}
		if(this.getEconomy().getGrowth() < -50)
		{
			growth = -20;
		}
		if(approval < 0)
		{
			map.put("stability.lowApproval", approval);
		}
		else
		{
			map.put("stability.approval", approval);
		}
		if(famine < 0)
		{
			map.put("stability.famine", famine);
		}
		map.put("stability.growth", growth);
		map.put("stability.net", approval + famine + growth);
		return map;
	}

	public double getFamineLevel()
	{
		double food = this.economy.getFood() + this.getFoodProduction().get("resource.net");
		if(food > 0)
		{
			return 0;
		}
		else
		{
			return -Math.sqrt(Math.abs(food)) * (Math.min(1, (this.getDomestic().getMonthsInFamine() + 1.0) / 10.0));
		}
	}

	public LinkedHashMap<String, Long> getGrowthChange()
	{
		LinkedHashMap<String, Long> map = new LinkedHashMap<>();
		long factories = this.getTotalFactories();
		long military = -1 * this.getUsedManpower().get("manpower.net") / 20000;
		long overMaxManpower = 0;
		if(this.getFreeManpower() < 0)
		{
			overMaxManpower = this.getFreeManpower() / 1000;
		}
		long conscription = economy.getRecentDeconscription() - economy.getRecentConscription();
		long fortification = -this.getArmy().getFortification() / 500;
		if(conscription > 0)
		{
			conscription = (long)((conscription + 1) * 0.75);
			map.put("growth.deconscription", conscription);
		}
		else
		{
			conscription = (long)(conscription * 1.15);
			map.put("growth.conscription", conscription);
		}
		switch(this.policy.getFortification())
		{
			case UNOCCUPIED_FORTIFICATION:
				fortification = 0;
				break;
			case MINIMAL_FUNDING_FORTIFICATION:
				fortification *= 0.5;
				break;
			case FULL_FUNDING_FORTIFICATION:
				fortification *= 1.25;
				break;
			case MAX_FORTIFICATION:
				fortification *= 2;
		}
		map.put("growth.factories", factories);
		map.put("growth.military", military);
		map.put("growth.over_max_manpower", overMaxManpower);
		map.put("growth.fortification", fortification);
		map.put("growth.net", factories + military + conscription + fortification + overMaxManpower);
		return map;
	}

	public LinkedHashMap<String, Double> getPopulationGrowth()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		double base = 2;
		double foodPolicy = 0;
		double manpowerPolicy = 0;
		double economyPolicy = 0;
		if(this.policy.getFood() == Policy.FREE_FOOD)
		{
			foodPolicy = 1.5;
		}
		else if(this.policy.getFood() == Policy.RATIONING_FOOD)
		{
			foodPolicy = -2.5;
		}
		if(this.policy.getManpower() == Policy.DISARMED_MANPOWER)
		{
			manpowerPolicy = 2.5;
		}
		else if(this.policy.getManpower() == Policy.VOLUNTEER_MANPOWER)
		{
			manpowerPolicy = 1;
		}
		else if(this.policy.getManpower() == Policy.MANDATORY_MANPOWER)
		{
			manpowerPolicy = -1;
		}
		else if(this.policy.getManpower() == Policy.SCRAPING_THE_BARREL_MANPOWER)
		{
			manpowerPolicy = -2.5;
		}
		if(this.policy.getEconomy() == Policy.CIVILIAN_ECONOMY)
		{
			economyPolicy = 1.5;
		}
		else if(this.policy.getEconomy() == Policy.WAR_ECONOMY)
		{
			economyPolicy = -1.5;
		}
		map.put("population.base", base);
		map.put("population.foodPolicy", foodPolicy);
		map.put("population.manpowerPolicy", manpowerPolicy);
		map.put("population.economy_policy", economyPolicy);
		map.put("population.net", base + foodPolicy + manpowerPolicy + economyPolicy);
		return map;
	}

	public long getRequiredSizeForNextCity()
	{
		return 500000L * (long)Math.pow(2, cities.getCities().size() - 1);
	}

	public boolean canMakeNewCity()
	{
		return this.getTotalPopulation() >= getRequiredSizeForNextCity();
	}

	public City getLargestCity()
	{
		long max = 0;
		int cityId = 0;
		for(City city : cities.getCities().values())
		{
			if(city.getPopulation() > max)
			{
				max = city.getPopulation();
				cityId = city.getId();
			}
		}
		return cities.getCities().get(cityId);
	}

	public LinkedHashMap<String, LinkedHashMap<String, Long>> getLandUsage()
	{
		LinkedHashMap<String, LinkedHashMap<String, Long>> map = new LinkedHashMap<>();
		for(City city : cities.getCities().values())
		{
			map.put(city.getName(), city.getLandUsage());
		}
		return map;
	}

	public String getDisplayString(String key)
	{
		switch(key)
		{
			case "population.base":
				return "% from Base Growth";
			case "population.foodPolicy":
				return "% from Food Policy";
			case "population.manpowerPolicy":
				return "% from Conscription Policy";
			case "population.economy_policy":
				return "% from Economic Policy";
			case "population.size":
				return "% from city size";
			case "population.net":
				return "% total growth per month";
			case "population.famine":
				return "% per month from famine";
			case "growth.factories":
				return " per month from factories";
			case "growth.military":
				return " per month from military upkeep";
			case "growth.deconscription":
				return " from recent deconscription";
			case "growth.conscription":
				return " from recent conscription";
			case "growth.fortification":
				return " from fortification upkeep";
			case "growth.over_max_manpower":
				return " from being over manpower limit";
			case "growth.net":
				return " change per month";
			case "stability.net":
			case "approval.net":
			case "fortification.net":
				return "% change per month";
			case "stability.lowApproval":
				return "% per month from low approval";
			case "stability.approval":
				return "% per month from high approval";
			case "stability.growth":
				return "% from low growth";
			case "stability.famine":
			case "approval.famine":
				return "% per months from famine";
			case "approval.policies":
				return "% per month from policies";
			case "manpower.army":
				return " in the Army";
			case "manpower.navy":
				return " in the Navy";
			case "manpower.airforce":
				return " in the Airforce";
			case "manpower.net":
				return " total deployed";
			case "land.mines":
				return " from Mines";
			case "land.factories":
				return " from Factories";
			case "land.universities":
				return " from Universities";
			case "resource.gdp":
				return " from GDP";
			case "resource.factoryUpkeep":
				return " from factory demands";
			case "resource.factoryProduction":
				return " from factories";
			case "resource.mines":
				return " from mining";
			case "resource.wells":
				return " from oil wells";
			case "resource.infrastructure":
				return " from infrastructure";
			case "resource.farming":
				return " from farming";
			case "resource.consumption":
				return " from consumption";
			case "resource.net":
				return " net production";
			case "resource.totalGain":
				return " total production";
			case "resource.totalLoss":
				return " total upkeep";
			case "resource.devastation":
				return " from devastation";
			case "resource.default":
				return " from population";
			case "resource.economy_type":
				return " from economic focus";
			case "resource.food_type":
				return " from food policy";
			case "resource.mil_factory_demands":
				return " from military factory demands";
			case "resource.technology":
				return " from technology";
			case "resource.farming_demands":
				return " from farm upkeep";
			case "resource.stability":
				return " from stability";
			case "resource.strike":
				return " from strikes";
			case "farming.base":
				return " from default";
			case "farming.net":
				return "";
			case "farming.regulations":
				return " from current regulations";
			case "farming.deregulation":
				return " from deregulation";
			case "farming.subsidies":
				return " from subsidies";
			case "farming.collectives":
				return " from collectivization";
			case "farming.tech":
				return " from technology";
			case "fortification.base_above":
				return " from being below max fortification";
			case "fortification.base_below":
				return " from being above max fortification";
			case "fortification.bonus":
				return " from fortification policies";
			case "fortification_max.tech":
				return "% from tech";
			case "fortification_max.policy":
				return "% from policies";
			default:
				return key;
		}
	}

	/**
	 * Writes the updated fields in this nation to the database
	 * @throws SQLException if a database error occurs
	 */
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
	 * Invites this Nation to the Treaty with the specified id
	 * @param id The Treaty id to invite to
	 * @return The displayable response message
	 * @throws SQLException If a database error occurs
	 */
	public String inviteToTreaty(Integer id, Nation inviter) throws SQLException
	{
		PreparedStatement check = conn.prepareStatement("SELECT id FROM cloc_treaty_invites WHERE nation_id=? AND alliance_id=?");
		check.setInt(1, this.id);
		check.setInt(2, id);
		ResultSet results = check.executeQuery();
		if(results.first())
		{
			return Responses.alreadyInvited();
		}
		else
		{
			PreparedStatement invite = conn.prepareStatement("INSERT INTO cloc_treaty_invites (alliance_id, nation_id) VALUES (?,?)");
			invite.setInt(1, id);
			invite.setInt(2, this.id);
			invite.execute();
			String message = News.createMessage(News.ID_TREATY_INVITE, inviter.getNationUrl(), inviter.getTreaty().getTreatyUrl());
			News.sendNews(conn, inviter.getId(), this.getId(), message);
			return Responses.invited();
		}
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