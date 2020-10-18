package net.watersfall.clocgame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.util.Time;
import net.watersfall.clocgame.util.Util;

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

	}

	public void writeLog()
	{
		Connection conn = null;
		try
		{
			conn = Database.getDataSource().getConnection();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO global_stats_history " +
					"(month, total_nations, total_neutral_nations, total_entente_nations, total_central_powers_nations, " +
					"total_population, total_soldiers, total_civilian_factories, total_military_factories, total_universities, " +
					"total_iron_mines, total_coal_mines, total_oil_wells) " +
					"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setLong(1, Time.month);
			statement.setLong(2, this.totalNations);
			statement.setLong(3, this.totalNeutralNations);
			statement.setLong(4, this.totalEntenteNations);
			statement.setLong(5, this.totalCentralPowersNations);
			statement.setLong(6, this.totalPopulation);
			statement.setLong(7, this.totalArmies);
			statement.setLong(8, this.totalCivilianFactories);
			statement.setLong(9, this.totalMilitaryFactories);
			statement.setLong(10, this.totalUniversities);
			statement.setLong(11, this.totalIronMines);
			statement.setLong(12, this.totalCoalMines);
			statement.setLong(13, this.totalOilWells);
			statement.execute();
			conn.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try
			{
				conn.rollback();
			}
			catch(Exception e2)
			{
				//Ignore
			}
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{
				//Ignore
			}
		}
	}

	public TreatyStats getTreatyStats(int id)
	{
		return treatyMap.get(id);
	}

	public LinkedHashMap<String, String> getMap()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("total_nations", Util.formatNumber(totalNations) + " nations");
		map.put("total_neutral_nations", Util.formatNumber(totalNeutralNations) + " nations");
		map.put("total_entente_nations", Util.formatNumber(totalEntenteNations) + " nations");
		map.put("total_central_powers_nations", Util.formatNumber(totalCentralPowersNations) + " nations");
		map.put("total_population", Util.formatNumber(totalPopulation) + " People");
		map.put("total_soldiers", Util.formatNumber(totalArmies) + "k Troops");
		map.put("total_civilian_factories", Util.formatNumber(totalCivilianFactories) + " Factories");
		map.put("total_military_factories", Util.formatNumber(totalMilitaryFactories) + " Factories");
		map.put("total_universities", Util.formatNumber(totalUniversities) + " Universities");
		map.put("total_iron_mines", Util.formatNumber(totalIronMines) + " Mines");
		map.put("total_coal_mines", Util.formatNumber(totalCoalMines) + " Mines");
		map.put("total_oil_wells", Util.formatNumber(totalOilWells) + " Wells");
		return map;
	}

	public String getStringFromKey(String key)
	{
		switch(key)
		{
			case "total_nations":
				return "Total Nations";
			case "total_neutral_nations":
				return "Total Neutral Nations";
			case "total_entente_nations":
				return "Total Entente Nations";
			case "total_central_powers_nations":
				return "Total Central Powers Nations";
			case "total_population":
				return "Global Population";
			case "total_soldiers":
				return "Total Soldiers";
			case "total_civilian_factories":
				return "Total Civilian Factories";
			case "total_military_factories":
				return "Total Military Factories";
			case "total_universities":
				return "Total Universities";
			case "total_iron_mines":
				return "Total Iron Mines";
			case "total_coal_mines":
				return "Total Coal Mines";
			case "total_oil_wells":
				return "Total Oil Wells";
			default:
				return "";
		}
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
			this.totalPopulation = results.getLong("population");
			//this.averagePopulation = results.getLong("averagePopulation");
			this.totalArmies = results.getLong("armySize");
			this.averageArmy = results.getDouble("averageArmy");
			this.totalCivilianFactories = results.getLong("CivilianFactories");
			this.averageCivilianFactories = results.getDouble("averageCivilianIndustry");
			this.totalUniversities = results.getLong("universities");
			this.averageUniversities = results.getDouble("averageUniversities");
			//this.totalMilitaryFactories = results.getLong("militaryFactories");
			//this.averageMilitaryFactories = results.getDouble("averageMilitaryIndustry");
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
			map.put("Total Population", Util.formatNumber(totalPopulation) + " People");
			//map.put("Average Population", Util.formatNumber(averagePopulation) + " People");
			map.put("Total Soldiers", Util.formatNumber(totalArmies) + "k Troops");
			map.put("Average Soldiers", Util.formatNumber(averageArmy) + "k Troops");
			map.put("Total Civilian Factories", Util.formatNumber(totalCivilianFactories) + " Factories");
			map.put("Average Civilian Factories", Util.formatNumber(averageCivilianFactories) + " Factories");
			//map.put("Total Military Factories", Util.formatNumber(totalMilitaryFactories) + " Factories");
			//map.put("Average Military Factories", Util.formatNumber(averageMilitaryFactories) + " Factories");
			map.put("Total Universities", Util.formatNumber(totalUniversities) + " Universities");
			map.put("Average Universities", Util.formatNumber(averageUniversities) + " Universities");
			map.put("Total Iron Mines", Util.formatNumber(totalIronMines) + " Mines");
			map.put("Average Iron Mines", Util.formatNumber(averageIronMines) + " Mines");
			map.put("Total Coal Mines", Util.formatNumber(totalCoalMines) + " Mines");
			map.put("Average Coal Mines", Util.formatNumber(averageCoalMines) + " Mines");
			map.put("Total Oil Wells", Util.formatNumber(totalOilWells) + " Wells");
			map.put("Average Oil Wells", Util.formatNumber(averageOilWells) + " Wells");
			return map;
		}
	}
}
