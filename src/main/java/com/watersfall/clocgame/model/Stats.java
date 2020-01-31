package com.watersfall.clocgame.model;

import com.watersfall.clocgame.database.Database;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Stats
{
	private @Getter long totalNations;
	private @Getter long totalArmies;
	private @Getter long totalCivilianFactories;
	private @Getter long totalUniversities;
	private @Getter long totalMilitaryFactories;
	private @Getter long totalCoalMines;
	private @Getter long totalIronMines;
	private @Getter long totalOilWells;
	private @Getter long totalPopulation;
	private @Getter long totalNeutralNations;
	private @Getter long totalEntenteNations;
	private @Getter long totalCentralPowersNations;
	private HashMap<Integer, TreatyStats> treatyMap;

	private static Stats stats = null;

	public static Stats getInstance()
	{
		if(stats == null)
		{
			stats = new Stats();
		}
		return stats;
	}

	private Stats()
	{
		updateStats();
	}

	public void updateStats()
	{
		try(Connection conn = Database.getDataSource().getConnection())
		{
			PreparedStatement statement = conn.prepareStatement(
					"SELECT COUNT(cloc_login.id) AS nations, SUM(cloc_army.size) AS armySize, SUM(cloc_cities.civilian_industry) AS civilianFactories, " +
					"SUM(cloc_cities.military_industry) AS militaryFactories, SUM(cloc_cities.universities) AS universities, " +
					"SUM(cloc_cities.coal_mines) AS coalMines, SUM(cloc_cities.iron_mines) AS ironMines, SUM(cloc_cities.oil_wells) AS oilWells, " +
					"SUM(cloc_domestic.population) AS population, SUM(cloc_foreign.alignment=0) AS neutral, SUM(cloc_foreign.alignment=1) AS entente, " +
					"SUM(cloc_foreign.alignment=-1) AS central " +
					"FROM cloc_login, cloc_cities, cloc_army, cloc_domestic, cloc_foreign " +
					"WHERE cloc_login.id=cloc_domestic.id AND cloc_login.id=cloc_army.id AND cloc_login.id AND cloc_login.id=cloc_cities.owner AND cloc_login.id=cloc_foreign.id");
			ResultSet results = statement.executeQuery();
			results.first();
			this.totalNations = results.getInt("nations");
			this.totalArmies = results.getInt("armySize");
			this.totalCivilianFactories = results.getInt("civilianFactories");
			this.totalMilitaryFactories = results.getInt("militaryFactories");
			this.totalUniversities = results.getInt("universities");
			this.totalCoalMines = results.getInt("coalMines");
			this.totalIronMines = results.getInt("ironMines");
			this.totalOilWells = results.getInt("oilWells");
			this.totalPopulation = results.getInt("population");
			this.totalNeutralNations = results.getInt("neutral");
			this.totalEntenteNations = results.getInt("entente");
			this.totalCentralPowersNations = results.getInt("central");
			treatyMap = new HashMap<>();
			PreparedStatement treaties = conn.prepareStatement(
					"SELECT cloc_treaties.id AS id, SUM(cloc_army.size) AS armySize, AVG(cloc_army.size) AS averageArmy, " +
					"SUM(cloc_cities.civilian_industry) AS civilianFactories, AVG(cloc_cities.civilian_industry) AS averageCivilianIndustry, " +
					"SUM(cloc_cities.military_industry) AS militaryFactories, AVG(cloc_cities.military_industry) AS averageMilitaryIndustry, " +
					"SUM(cloc_cities.universities) AS universities, AVG(cloc_cities.universities) AS averageUniversities, " +
					"SUM(cloc_cities.coal_mines) AS coalMines, AVG(cloc_cities.coal_mines) AS averageCoalmines, SUM(cloc_cities.iron_mines) AS ironMines, " +
					"AVG(cloc_cities.iron_mines) AS averageIronMines, SUM(cloc_cities.oil_wells) AS oilWells, AVG(cloc_cities.oil_wells) AS averageOilWells, " +
					"SUM(cloc_domestic.population) AS population, AVG(cloc_domestic.population) AS averagePopulation  " +
					"FROM cloc_login, cloc_cities, cloc_army, cloc_domestic, cloc_foreign, cloc_treaties_members, cloc_treaties " +
					"WHERE cloc_login.id=cloc_domestic.id AND cloc_login.id=cloc_army.id AND cloc_login.id AND cloc_login.id=cloc_cities.owner " +
					"AND cloc_login.id=cloc_foreign.id AND cloc_login.id=cloc_treaties_members.nation_id AND cloc_treaties_members.alliance_id=cloc_treaties.id");
			ResultSet treatyResults = treaties.executeQuery();
			while(treatyResults.next())
			{
				treatyMap.put(treatyResults.getInt("id"), new TreatyStats(treatyResults));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public TreatyStats getTreatyStats(int id)
	{
		return treatyMap.get(id);
	}

	public LinkedHashMap<String, Long> getMap()
	{
		LinkedHashMap<String, Long> map = new LinkedHashMap<>();
		map.put("Total Nations", totalNations);
		map.put("Total Neutral Nations", totalNeutralNations);
		map.put("Total Entente Nations", totalEntenteNations);
		map.put("Total Central Powers Nations", totalCentralPowersNations);
		map.put("Global Population", totalPopulation);
		map.put("Total Soldiers", totalArmies);
		map.put("Total Civilian Factories", totalCivilianFactories);
		map.put("Total Military Factories", totalMilitaryFactories);
		map.put("Total Universities", totalUniversities);
		map.put("Total Iron Mines", totalIronMines);
		map.put("Total Coal Mines", totalCoalMines);
		map.put("Total Oil Wells", totalOilWells);
		return map;
	}

	public static @AllArgsConstructor class TreatyStats
	{
		private @Getter long totalArmies;
		private @Getter double averageArmy;
		private @Getter long totalCivilianFactories;
		private @Getter double averageCivilianFactories;
		private @Getter long totalUniversities;
		private @Getter double averageUniversities;
		private @Getter long totalMilitaryFactories;
		private @Getter double averageMilitaryFactories;
		private @Getter long totalCoalMines;
		private @Getter double averageCoalMines;
		private @Getter long totalIronMines;
		private @Getter double averageIronMines;
		private @Getter long totalOilWells;
		private @Getter double averageOilWells;
		private @Getter long totalPopulation;
		private @Getter double averagePopulation;

		public TreatyStats(ResultSet results) throws SQLException
		{
			this.totalArmies = results.getLong("armySize");
			this.averageArmy = results.getDouble("averageArmy");
			this.totalCivilianFactories = results.getLong("CivilianFactories");
			this.averageCivilianFactories = results.getDouble("averageCivilianIndustry");
			this.totalUniversities = results.getLong("universities");
			this.averageUniversities = results.getDouble("averageUniversities");
			this.totalMilitaryFactories = results.getLong("militaryFactories");
			this.averageMilitaryFactories = results.getDouble("averageMilitaryIndustry");
			this.totalCoalMines = results.getLong("coalMines");
			this.averageCoalMines = results.getDouble("averageCoalMines");
			this.totalIronMines = results.getLong("ironMines");
			this.averageIronMines = results.getDouble("averageIronMines");
			this.totalOilWells = results.getLong("oilWells");
			this.averageOilWells = results.getDouble("averageOilWells");
		}

		public LinkedHashMap<String, Object> getMap()
		{
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("Total Population", totalPopulation);
			map.put("Average Population", averagePopulation);
			map.put("Total Soldiers", totalArmies);
			map.put("Average Soldiers", averageArmy);
			map.put("Total Civilian Factories", totalCivilianFactories);
			map.put("Average Civilian Factories", averageCivilianFactories);
			map.put("Total Military Factories", totalMilitaryFactories);
			map.put("Average Military Factories", averageMilitaryFactories);
			map.put("Total Universities", totalUniversities);
			map.put("Average Universities", averageUniversities);
			map.put("Total Iron Mines", totalIronMines);
			map.put("Average Iron Mines", averageIronMines);
			map.put("Total Coal Mines", totalCoalMines);
			map.put("Average Coal Mines", averageCoalMines);
			map.put("Total Oil Wells", totalOilWells);
			map.put("Average Oil Wells", averageOilWells);
			return map;
		}
	}
}
