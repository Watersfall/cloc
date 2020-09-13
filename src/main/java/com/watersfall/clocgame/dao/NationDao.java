package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.CityType;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.alignment.AlignmentTransaction;
import com.watersfall.clocgame.model.alignment.Alignments;
import com.watersfall.clocgame.model.event.Event;
import com.watersfall.clocgame.model.nation.*;
import com.watersfall.clocgame.model.producible.Producibles;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.util.Security;
import com.watersfall.clocgame.util.Time;

import java.sql.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class NationDao extends Dao
{
	private static final String STATS_SQL_STATEMENT =
					"SELECT * FROM cloc_login\n" +
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
					"WHERE cloc_login.id=?\n";
	private static final String NAME_SQL_STATEMENT =
					"SELECT id\n" +
					"FROM cloc_cosmetic\n" +
					"WHERE nation_name=?\n";
	private static final String INVITES_SQL_STATEMENT =
					"SELECT alliance_id, id\n" +
					"FROM cloc_treaty_invites\n" +
					"WHERE nation_id=?\n";
	private static final String NEWS_SQL_STATEMENT =
					"SELECT COUNT(id) AS count, SUM(is_read=0) AS unread\n" +
					"FROM cloc_news\n" +
					"WHERE receiver=?\n";
	private static final String EVENT_SQL_STATEMENT =
					"SELECT *" +
					"FROM events\n" +
					"WHERE owner=?\n";
	private static final String WAR_SQL_STATEMENT =
					"SELECT id, attacker, defender\n" +
					"FROM cloc_war\n" +
					"WHERE (attacker=? OR defender=?) AND end=-1\n";
	private static final String MODIFIERS_SQL_STATEMENT =
					"SELECT * \n" +
					"FROM modifiers \n" +
					"WHERE user=?\n";
	private static final String PRODUCTION_SQL_STATEMENT =
					"SELECT * FROM production\n" +
					"LEFT JOIN factories ON production.id=factories.production_id\n" +
					"WHERE production.owner=?\n";
	private static final String DELETE_NATION_SQL_STATEMENT =
					"DELETE FROM cloc_login\n" +
					"WHERE id=?\n";
	private static final String CREATE_NATION_STATS =
					"INSERT INTO cloc_login (username, email, password, register_ip, last_ip, last_login) VALUES (?,?,?,?,?,?);" +
					"INSERT INTO cloc_army () VALUES ();" +
					"INSERT INTO cloc_cosmetic (nation_name, username, description) VALUES (?,?,?);" +
					"INSERT INTO cloc_domestic (government) VALUES (?);" +
					"INSERT INTO cloc_economy (economic) VALUES (?);" +
					"INSERT INTO cloc_foreign (region) VALUES (?);" +
					"INSERT INTO cloc_military () VALUES ();" +
					"INSERT INTO cloc_policy () VALUES ();" +
					"INSERT INTO cloc_tech () VALUES ();";
	private static final String CREATE_NATION_CITY =
					"INSERT INTO cloc_cities (owner, capital, coastal, name, type)\n" +
					"VALUES (?,?,?,?,?)\n";
	private static final String CREATE_NATION_INDUSTRY =
					"INSERT INTO factories (owner, city_id, production_id)\n" +
					"VALUES (?,?,?)\n";
	private static final String RANKINGS_SQL_STATEMENT =
					"SELECT * FROM cloc_login\n" +
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
					"ORDER BY cloc_economy.gdp DESC, cloc_login.id ASC\n" +
					"LIMIT 20\n" +
					"OFFSET ?\n";

	private static final String REGION_RANKINGS_SQL_STATEMENT =
					"SELECT * FROM cloc_login\n" +
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
					"WHERE cloc_foreign.region=?\n" +
					"ORDER BY cloc_economy.gdp DESC, cloc_login.id ASC\n" +
					"LIMIT 20\n" +
					"OFFSET ?\n";
	private static final String TREATY_MEMBERS_SQL_STATEMENT =
					"SELECT * FROM cloc_login\n" +
					"JOIN cloc_economy ON cloc_login.id = cloc_economy.id\n" +
					"JOIN cloc_domestic ON cloc_login.id = cloc_domestic.id\n" +
					"JOIN cloc_cosmetic ON cloc_login.id = cloc_cosmetic.id\n" +
					"JOIN cloc_foreign ON cloc_login.id = cloc_foreign.id\n" +
					"JOIN cloc_military ON cloc_login.id = cloc_military.id\n" +
					"JOIN cloc_tech ON cloc_login.id = cloc_tech.id\n" +
					"JOIN cloc_policy ON cloc_login.id = cloc_policy.id\n" +
					"JOIN cloc_army ON cloc_login.id = cloc_army.id\n" +
					"RIGHT JOIN cloc_treaties_members treaty_member ON cloc_login.id = treaty_member.nation_id\n" +
					"RIGHT JOIN cloc_treaties treaty ON treaty_member.alliance_id = treaty.id\n" +
					"WHERE treaty.id=?\n" +
					"ORDER BY cloc_login.id\n";
	private static final String ALIGNMENT_TRANSACTIONS =
			"SELECT * FROM alignments_transactions WHERE nation=? ";

	public NationDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}


	public Nation getNationById(int id) throws SQLException
	{
		Nation nation = new Nation(id);
		PreparedStatement getStats, getCities, getInvites, getNews, getEvents, getWars, getModifiers, getProduction, transactionsStatement;
		if(allowWriteAccess)
		{
			transactionsStatement = connection.prepareStatement(ALIGNMENT_TRANSACTIONS + WRITE_ACCESS_SQL_STATEMENT);
			getStats = connection.prepareStatement(STATS_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
			getCities = connection.prepareStatement(CityDao.CITIES_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
			getInvites = connection.prepareStatement(INVITES_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
			getNews = connection.prepareStatement(NEWS_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
			getEvents = connection.prepareStatement(EVENT_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
			getWars = connection.prepareStatement(WAR_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
			getModifiers = connection.prepareStatement(MODIFIERS_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
			getProduction = connection.prepareStatement(PRODUCTION_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			transactionsStatement = connection.prepareStatement(ALIGNMENT_TRANSACTIONS);
			getStats = connection.prepareStatement(STATS_SQL_STATEMENT);
			getCities = connection.prepareStatement(CityDao.CITIES_SQL_STATEMENT);
			getInvites = connection.prepareStatement(INVITES_SQL_STATEMENT);
			getNews = connection.prepareStatement(NEWS_SQL_STATEMENT);
			getEvents = connection.prepareStatement(EVENT_SQL_STATEMENT);
			getWars = connection.prepareStatement(WAR_SQL_STATEMENT);
			getModifiers = connection.prepareStatement(MODIFIERS_SQL_STATEMENT);
			getProduction = connection.prepareStatement(PRODUCTION_SQL_STATEMENT);
		}
		getStats.setInt(1, id);
		getCities.setInt(1, id);
		getInvites.setInt(1, id);
		getNews.setInt(1, id);
		getEvents.setInt(1, id);
		getWars.setInt(1, id);
		getWars.setInt(2, id);
		getModifiers.setInt(1, id);
		getProduction.setInt(1, id);
		ResultSet statsResults = getStats.executeQuery();
		ResultSet citiesResults = getCities.executeQuery();
		ResultSet invitesResults = getInvites.executeQuery();
		ResultSet newsResults = getNews.executeQuery();
		ResultSet eventResults = getEvents.executeQuery();
		ResultSet warResults = getWars.executeQuery();
		ResultSet modifierResults = getModifiers.executeQuery();
		ResultSet productionResults = getProduction.executeQuery();
		if(!statsResults.first())
		{
			throw new NationNotFoundException();
		}
		nation.setLastSeen(statsResults.getLong("last_login"));
		nation.setEconomy(new NationEconomy(id, statsResults));
		nation.setDomestic(new NationDomestic(id, statsResults));
		nation.setArmy(new NationArmy(id, statsResults));
		nation.setMilitary(new NationMilitary(id, statsResults));
		nation.setPolicy(new NationPolicy(id, statsResults));
		nation.setTech(new NationTech(id, statsResults));
		nation.setCosmetic(new NationCosmetic(id, statsResults));
		nation.setForeign(new NationForeign(id, statsResults));
		nation.setLastReadMessage(statsResults.getInt("last_message"));
		HashMap<Integer, City> cities = new HashMap<>();
		while(citiesResults.next())
		{
			City city = new City(citiesResults);
			city.setNation(nation);
			cities.put(citiesResults.getInt("cloc_cities.id"), city);
		}
		nation.setCities(cities);
		ArrayList<Integer> invites = new ArrayList<>();
		while(invitesResults.next())
		{
			invites.add(invitesResults.getInt("alliance_id"));
		}
		nation.setInvites(invites);
		newsResults.first();
		nation.setNewsCount(newsResults.getInt("count"));
		nation.setAnyUnreadNews(newsResults.getInt("unread") > 0);
		ArrayList<Event> events = new ArrayList<>();
		while(eventResults.next())
		{
			events.add(new Event(eventResults));
		}
		nation.setEvents(events);
		nation.setEventCount(events.size());
		WarDao dao = new WarDao(connection, allowWriteAccess);
		while(warResults.next())
		{
			if(warResults.getInt("attacker") == id)
			{
				nation.setOffensive(dao.getWarById(warResults.getInt("cloc_war.id")));
			}
			else
			{
				nation.setDefensive(dao.getWarById(warResults.getInt("cloc_war.id")));
			}
		}
		ArrayList<Modifier> modifiers = new ArrayList<>();
		while(modifierResults.next())
		{
			modifiers.add(new Modifier(modifierResults));
		}
		nation.setModifiers(modifiers);
		if(statsResults.getInt("treaty.id") != 0)
		{
			nation.setTreatyPermissions(new TreatyPermissions(statsResults));
			nation.setTreaty(new Treaty(statsResults));
		}
		int usedFactories = 0;
		LinkedHashMap<Integer, Production> production = new LinkedHashMap<>();
		if(productionResults.first())
		{
			HashMap<String, Double> resources = new HashMap<>();
			resources.put("steel", nation.getEconomy().getSteel());
			resources.put("nitrogen", nation.getEconomy().getNitrogen());
			resources.put("oil", nation.getEconomy().getOil());
			HashMap<Integer, Factory> factories = new HashMap<>();
			boolean next = true;
			while(next)
			{
				usedFactories++;
				if(!production.containsKey(productionResults.getInt("production.id")))
				{
					factories = new HashMap<>();
					production.put(productionResults.getInt("production.id"), new Production(
							productionResults.getInt("production.id"),
							productionResults.getInt("owner"),
							null,
							productionResults.getString("production"),
							productionResults.getInt("progress"),
							null));
					production.get(productionResults.getInt("production.id")).setFactories(factories);
					production.get(productionResults.getInt("production.id")).setGivenResources(new HashMap<>(resources));
				}
				if(productionResults.getInt("production_id") == productionResults.getInt("production.id"))
				{
					factories.put(productionResults.getInt("factories.id"),
							new Factory(productionResults.getInt("factories.id"), productionResults));
				}
				next = productionResults.next();
			}
		}
		nation.setProduction(production);
		nation.setFreeFactories(nation.getTotalMilitaryFactories() - usedFactories);
		nation.setConn(connection);
		MessageDao messageDao = new MessageDao(connection, allowWriteAccess);
		nation.setUnreadMessages(messageDao.getUnreadMessages(nation.getId(), nation.getLastReadMessage()));
		transactionsStatement.setInt(1, nation.getId());
		ResultSet transactions = transactionsStatement.executeQuery();
		EnumMap<Alignments, ArrayList<AlignmentTransaction>> transactionsMap = new EnumMap<>(Alignments.class);
		while(transactions.next())
		{
			Alignments alignment = Alignments.valueOf(transactions.getString("alignment"));
			Producibles producible = Producibles.valueOf(transactions.getString("equipment"));
			AlignmentTransaction transaction = new AlignmentTransaction(alignment, nation, producible, transactions.getLong("amount"), transactions.getLong("month"));
			if(!transactionsMap.containsKey(alignment))
			{
				ArrayList<AlignmentTransaction> list = new ArrayList<>();
				list.add(transaction);
				transactionsMap.put(alignment, list);
			}
			else
			{
				transactionsMap.get(alignment).add(transaction);
			}
		}
		nation.setAlignmentTransactions(transactionsMap);
		return nation;
	}

	public Nation getNationByName(String name) throws SQLException
	{
		PreparedStatement statement;
		if(allowWriteAccess)
		{
			statement = connection.prepareStatement(NAME_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			statement = connection.prepareStatement(NAME_SQL_STATEMENT);
		}
		statement.setString(1, name);
		ResultSet results = statement.executeQuery();
		if(!results.first())
		{
			return null;
		}
		else
		{
			return getNationById(results.getInt(1));
		}
	}

	public Nation getCosmeticNationById(int id) throws SQLException
	{
		Nation nation = new Nation(id);
		PreparedStatement getStats;
		if(allowWriteAccess)
		{
			getStats = connection.prepareStatement(STATS_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			getStats = connection.prepareStatement(STATS_SQL_STATEMENT);
		}
		getStats.setInt(1, id);
		ResultSet statsResults = getStats.executeQuery();
		if(!statsResults.first())
		{
			throw new NationNotFoundException();
		}
		nation.setEconomy(new NationEconomy(id, statsResults));
		nation.setDomestic(new NationDomestic(id, statsResults));
		nation.setArmy(new NationArmy(id, statsResults));
		nation.setMilitary(new NationMilitary(id, statsResults));
		nation.setPolicy(new NationPolicy(id, statsResults));
		nation.setTech(new NationTech(id, statsResults));
		nation.setCosmetic(new NationCosmetic(id, statsResults));
		nation.setForeign(new NationForeign(id, statsResults));
		if(statsResults.getInt("treaty.id") != 0)
		{
			nation.setTreatyPermissions(new TreatyPermissions(statsResults));
			nation.setTreaty(new Treaty(statsResults));
		}
		nation.setConn(connection);
		return nation;
	}

	public Nation getCosmeticNationById(int id, ResultSet results) throws SQLException
	{
		Nation nation = new Nation(id);
		nation.setEconomy(new NationEconomy(id, results));
		nation.setDomestic(new NationDomestic(id, results));
		nation.setArmy(new NationArmy(id, results));
		nation.setMilitary(new NationMilitary(id, results));
		nation.setPolicy(new NationPolicy(id, results));
		nation.setTech(new NationTech(id, results));
		nation.setCosmetic(new NationCosmetic(id, results));
		nation.setForeign(new NationForeign(id, results));
		if(results.getInt("treaty.id") != 0)
		{
			nation.setTreatyPermissions(new TreatyPermissions(results));
			nation.setTreaty(new Treaty(results));
		}
		nation.setConn(connection);
		return nation;
	}

	public void saveNation(Nation nation) throws SQLException
	{
		requireWriteAccess();
		nation.update(connection);
	}

	@SuppressWarnings("JpaQueryApiInspection")
	public int createNation(String username, String password, String nation, String capital, int gov, int econ,
							Region region, String ip) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement create = connection.prepareStatement(CREATE_NATION_STATS, Statement.RETURN_GENERATED_KEYS);
		create.setString(1, username);
		create.setString(2, "");
		create.setString(3, Security.hash(password));
		create.setString(4, ip);
		create.setString(5, ip);
		create.setLong(6, System.currentTimeMillis());
		create.setString(7, nation);
		create.setString(8, username);
		create.setString(9, "Welcome to CLOC! Please change me in the settings.");
		create.setInt(10, gov);
		create.setInt(11, econ);
		create.setString(12, region.name());
		create.execute();
		ResultSet key = create.getGeneratedKeys();
		key.first();
		int id = key.getInt(1);
		PreparedStatement cities = connection.prepareStatement(CREATE_NATION_CITY, Statement.RETURN_GENERATED_KEYS);
		cities.setInt(1, id);
		cities.setBoolean(2, true);
		cities.setBoolean(3, true);
		cities.setString(4, capital);
		cities.setString(5, CityType.FARMING.name());
		cities.execute();
		ResultSet cityKey = cities.getGeneratedKeys();
		cityKey.first();
		PreparedStatement factory = connection.prepareStatement(CREATE_NATION_INDUSTRY);
		factory.setInt(1, id);
		factory.setInt(2, cityKey.getInt(1));
		factory.setNull(3, Types.INTEGER);
		factory.execute();
		return id;
	}

	public void deleteNation(Nation nation) throws SQLException
	{
		deleteNation(nation.getId());
	}

	public void deleteNation(int id) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(DELETE_NATION_SQL_STATEMENT);
		statement.setInt(1, id);
		statement.execute();
	}

	public ArrayList<Nation> getNationPage(int page) throws SQLException
	{
		ArrayList<Nation> list = new ArrayList<>();
		PreparedStatement statement = connection.prepareStatement(RANKINGS_SQL_STATEMENT);
		statement.setInt(1, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			int id = results.getInt("id");
			Nation nation = new Nation(results.getInt("id"));
			nation.setEconomy(new NationEconomy(id, results));
			nation.setDomestic(new NationDomestic(id, results));
			nation.setArmy(new NationArmy(id, results));
			nation.setMilitary(new NationMilitary(id, results));
			nation.setPolicy(new NationPolicy(id, results));
			nation.setTech(new NationTech(id, results));
			nation.setCosmetic(new NationCosmetic(id, results));
			nation.setForeign(new NationForeign(id, results));
			if(results.getInt("treaty.id") != 0)
			{
				nation.setTreaty(new Treaty(results));
				nation.setTreatyPermissions(new TreatyPermissions(results));
			}
			list.add(nation);
		}
		return list;
	}

	public ArrayList<Nation> getNationPage(Region region, int page) throws SQLException
	{
		ArrayList<Nation> list = new ArrayList<>();
		PreparedStatement statement = connection.prepareStatement(REGION_RANKINGS_SQL_STATEMENT);
		statement.setString(1, region.name());
		statement.setInt(2, (page - 1) * 20);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			int id = results.getInt("id");
			Nation nation = new Nation(results.getInt("id"));
			nation.setEconomy(new NationEconomy(id, results));
			nation.setDomestic(new NationDomestic(id, results));
			nation.setArmy(new NationArmy(id, results));
			nation.setMilitary(new NationMilitary(id, results));
			nation.setPolicy(new NationPolicy(id, results));
			nation.setTech(new NationTech(id, results));
			nation.setCosmetic(new NationCosmetic(id, results));
			nation.setForeign(new NationForeign(id, results));
			if(results.getInt("treaty.id") != 0)
			{
				nation.setTreaty(new Treaty(results));
				nation.setTreatyPermissions(new TreatyPermissions(results));
			}
			list.add(nation);
		}
		return list;
	}

	public ArrayList<Nation> getTreatyMembers(int id) throws SQLException
	{
		ArrayList<Nation> list = new ArrayList<>();
		PreparedStatement statement = connection.prepareStatement(TREATY_MEMBERS_SQL_STATEMENT);
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		while(results.next())
		{
			int nationId = results.getInt("id");
			Nation nation = new Nation(results.getInt("id"));
			nation.setEconomy(new NationEconomy(nationId, results));
			nation.setDomestic(new NationDomestic(nationId, results));
			nation.setArmy(new NationArmy(nationId, results));
			nation.setMilitary(new NationMilitary(nationId, results));
			nation.setPolicy(new NationPolicy(nationId, results));
			nation.setTech(new NationTech(nationId, results));
			nation.setCosmetic(new NationCosmetic(nationId, results));
			nation.setForeign(new NationForeign(nationId, results));
			Treaty treaty = new Treaty(results);
			nation.setTreaty(treaty);
			nation.setTreatyPermissions(new TreatyPermissions(results));
			list.add(nation);
		}
		return list;
	}
	
	public void doNationProduction(Nation nation) throws SQLException
	{
		String statement = "";
		for(Production production : nation.getProduction().values())
		{
			if(production.getIc(nation.getPolicy().getEconomy()) > 0)
			{
				production.getRequiredResources().forEach((k, v) -> {
					double amount = v / (double) com.watersfall.clocgame.util.Time.daysPerMonth[Time.currentMonth];
					switch(k)
					{
						case "steel":
							nation.getEconomy().setSteel(nation.getEconomy().getSteel() - amount);
							break;
						case "oil":
							nation.getEconomy().setSteel(nation.getEconomy().getOil() - amount);
							break;
						case "nitrogen":
							nation.getEconomy().setNitrogen(nation.getEconomy().getNitrogen() - amount);
							break;
					}
				});
				double productionIc = production.getIc(nation.getPolicy().getEconomy());
				double ic = productionIc + (production.getProgress() / 100.0);
				int amount = (int) (ic / production.getProductionAsTechnology().getTechnology().getProducibleItem().getProductionICCost());
				int leftover = (int) ((ic - (amount * production.getProductionAsTechnology().getTechnology().getProducibleItem().getProductionICCost())) * 100);
				production.setProgress(leftover);
				statement += Producibles.valueOf(production.getProductionAsTechnology().getTechnology().getProducibleItem()).name().toLowerCase() + "="
						+ Producibles.valueOf(production.getProductionAsTechnology().getTechnology().getProducibleItem()).name().toLowerCase() + "+" + amount + ", ";
			}
		}
		if(!statement.isEmpty())
		{
			statement = "UPDATE cloc_army, cloc_military SET ".concat(statement)
					.concat("WHERE cloc_army.id=? AND cloc_military.id=? AND cloc_army.id=cloc_military.id");
			statement = statement.replace(", WHERE", " WHERE");
			PreparedStatement update = connection.prepareStatement(statement);
			update.setInt(1, nation.getId());
			update.setInt(2, nation.getId());
			update.execute();
		}
	}
}
