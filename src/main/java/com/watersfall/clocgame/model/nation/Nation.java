package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.math.Math;
import com.watersfall.clocgame.model.CityType;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.util.Util;
import lombok.AllArgsConstructor;
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
	private @Getter NationArmies armies;
	private @Getter NationPolicy policy;
	private @Getter int defensive;
	private @Getter int offensive;
	private @Getter Treaty treaty;
	private @Getter Connection connection;
	private @Getter boolean safe;

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
		cities = new NationCities(connection, id, safe);
		armies = new NationArmies(connection, id, safe);
		policy = new NationPolicy(connection, id, safe);
		//try{treaty = new Treaty(connection, id, safe);}catch(TreatyNotFoundException e){treaty=null;}
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
	 * Commits all changes made to this object
	 * Put in the main Nation class instead of in the subclasses since they all share the same connection
	 *
	 * @throws SQLException if something TQL related goes wrong
	 */
	public void commit() throws SQLException
	{
		connection.commit();
	}

	public void joinTreaty(Treaty treaty, boolean founder) throws SQLException
	{
		PreparedStatement check = connection.prepareStatement("SELECT * FROM cloc_treaties_members WHERE nation_id=?");
		check.setInt(1, this.id);
		if(check.executeQuery().first())
		{
			PreparedStatement leave = connection.prepareStatement("DELETE FROM cloc_treaties_members WHERE nation_id=?");
			leave.setInt(1, this.id);
			leave.execute();
		}
		PreparedStatement join = connection.prepareStatement("INSERT INTO cloc_treaties_members (alliance_id, nation_id, founder) VALUES (?,?,?)");
		join.setInt(1, treaty.getId());
		join.setInt(2, this.id);
		join.setBoolean(3, founder);
		join.execute();
	}

	public void leaveTreaty() throws SQLException
	{
		PreparedStatement leave = connection.prepareStatement("DELETE FROM cloc_treaties_members WHERE nation_id=?");
		leave.setInt(1, this.id);
	}

	public boolean canDeclareWar(Nation nation)
	{
		return (this.offensive == 0 && nation.getDefensive() == 0)
				&& Region.borders(nation.getForeign().getRegion(), this.getForeign().getRegion())
				&& nation.getMilitary().getWarProtection() <= Util.turn;
	}

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

	public HashMap<String, Integer> getGrowthChange()
	{
		HashMap<String, Integer> map = new HashMap<>();
		int factories = 0;
		int troopsHome = 0;
		int troopsForeign = 0;
		for(City city : cities.getCities().values())
		{
			factories += city.getIndustryCivilian() + city.getIndustryMilitary() + city.getIndustryNitrogen();
		}
		for(Army army : armies.getArmies().values())
		{
			if(army.getRegion() == foreign.getRegion())
			{
				troopsHome += army.getArmy();
			}
			else
			{
				troopsForeign += army.getArmy();
			}
		}
		int home = troopsHome / 20;
		int foreign = troopsForeign / 10;
		map.put("factories", factories);
		map.put("home", home);
		map.put("foreign", foreign);
		map.put("net", factories - home - foreign);
		return map;
	}

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

	public HashMap<String, Integer> getStabilityChange()
	{
		HashMap<String, Integer> map = new HashMap<>();
		map.put("total", 1);
		return map;
	}

	public HashMap<String, HashMap<String, Integer>> getLandUsageByCityAndType()
	{
		HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
		for(City city : this.cities.getCities().values())
		{
			map.put(city.getName(), city.getLandUsage());
		}
		return map;
	}

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

	public int getTotalLandUsage()
	{
		HashMap<String, Integer> map = this.getLandUsageByType();
		int total = 0;
		for(Integer land : map.values())
		{
			total += land;
		}
		return total;
	}

	public int getFreeLand()
	{
		return this.domestic.getLand() - this.getTotalLandUsage();
	}

	public HashMap<String, Integer> getFoodProduction()
	{
		HashMap<String, Integer> map = new HashMap<>();
		map.put("farming", this.getFreeLand() / 250);
		map.put("costs", 0);
		map.put("net", map.get("farming"));
		return map;
	}

	public int getTotalArmySize()
	{
		int soldiers = 0;
		for(Army army : armies.getArmies().values())
		{
			soldiers += army.getArmy();
		}
		return soldiers;
	}

	public long getFreeManpower()
	{
		int soldiers = getTotalArmySize() * 1000;
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
		return manpower - soldiers;
	}

	public void update() throws SQLException
	{
		cosmetic.update();
		domestic.update();
		economy.update();
		foreign.update();
		military.update();
		policy.update();
		for(City city : cities.getCities().values())
		{
			city.update();
		}
		for(Army army : armies.getArmies().values())
		{
			army.update();
		}
	}
}
