package com.watersfall.clocgame.model;

import com.watersfall.clocgame.database.Database;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
					"SUM(cloc_domestic.population) AS population " +
					"FROM cloc_login, cloc_cities, cloc_army, cloc_domestic " +
					"WHERE cloc_login.id = cloc_domestic.id AND cloc_login.id = cloc_army.id AND cloc_login.id AND cloc_login.id = cloc_cities.owner");
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
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public LinkedHashMap<String, Long> getMap()
	{
		LinkedHashMap<String, Long> map = new LinkedHashMap<>();
		map.put("Total Nations", totalNations);
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
}
