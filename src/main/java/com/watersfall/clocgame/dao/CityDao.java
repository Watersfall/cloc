package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.city.City;

import java.sql.*;
import java.util.HashMap;

public class CityDao extends Dao
{
	private static final String CITY_SQL_STATEMENT =
					"SELECT cities.*, COALESCE(military_industry, 0) AS military_industry\n" +
					"FROM cities\n" +
					"LEFT JOIN (\n" +
					"SELECT city_id, COUNT(id)\n" +
					"AS military_industry FROM factories GROUP BY city_id ) military_industry\n" +
					"ON military_industry.city_id=cities.id\n" +
					"WHERE cities.id=?\n";
	protected static final String CITIES_SQL_STATEMENT =
					"SELECT cities.*, COALESCE(military_industry, 0) AS military_industry\n" +
					"FROM cities\n" +
					"LEFT JOIN (\n" +
					"SELECT city_id, COUNT(id)\n" +
					"AS military_industry FROM factories GROUP BY city_id ) military_industry\n" +
					"ON military_industry.city_id=cities.id\n" +
					"WHERE cities.owner=?\n";
	private static final String BUILD_MILITARY_INDUSTRY_SQL_STATEMENT =
					"INSERT INTO factories (owner, city_id, production_id)\n" +
					"VALUES (?,?,?)\n";
	private static final String REMOVE_MILITARY_INDUSTRY_SQL_STATEMENT =
					"DELETE FROM factories\n" +
					"WHERE city_id=?\n" +
					"ORDER BY efficiency\n" +
					"LIMIT 1\n";
	private static final String CREATE_CITY_SQL_STATEMENT =
					"INSERT INTO cities\n" +
					"(owner, capital, coastal, railroads, ports, barracks, iron_mines, coal_mines, oil_wells,\n" +
					"civilian_industry, nitrogen_industry, universities, name, type, devastation, population)\n" +
					"VALUES (?,?,?,0,0,0,0,0,0,0,0,0,'New City','FARMING',0,5000)\n";

	public CityDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public City getCityById(long id) throws SQLException
	{
		PreparedStatement getCity;
		if(allowWriteAccess)
		{
			getCity = connection.prepareStatement(CITY_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			getCity = connection.prepareStatement(CITY_SQL_STATEMENT);
		}
		getCity.setLong(1, id);
		ResultSet cityResults = getCity.executeQuery();
		cityResults.first();
		return new City(cityResults);
	}

	public HashMap<Integer, City> getCitiesByNationId(int id) throws SQLException
	{
		PreparedStatement getCities;
		if(allowWriteAccess)
		{
			getCities = connection.prepareStatement(CITIES_SQL_STATEMENT + WRITE_ACCESS_SQL_STATEMENT);
		}
		else
		{
			getCities = connection.prepareStatement(CITIES_SQL_STATEMENT);
		}
		getCities.setInt(1, id);
		ResultSet citiesResults = getCities.executeQuery();
		HashMap<Integer, City> cities = new HashMap<>();
		while(citiesResults.next())
		{
			cities.put(citiesResults.getInt("cities.id"), new City(citiesResults));
		}
		return cities;
	}

	public void buildMilitaryIndustry(City city) throws SQLException
	{
		buildMilitaryIndustry(city.getOwner(), city.getId());
	}

	public void removeMilitaryIndustry(City city) throws SQLException
	{
		removeMilitaryIndustry(city.getId());
	}

	public void buildMilitaryIndustry(int nationId, long cityId) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement build = connection.prepareStatement(BUILD_MILITARY_INDUSTRY_SQL_STATEMENT);
		build.setInt(1, nationId);
		build.setLong(2, cityId);
		build.setNull(3, Types.INTEGER);
		build.execute();
	}

	public void removeMilitaryIndustry(long id) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement remove = connection.prepareStatement(REMOVE_MILITARY_INDUSTRY_SQL_STATEMENT);
		remove.setLong(1, id);
		remove.execute();
	}

	public int createNewCity(int owner) throws SQLException
	{
		requireWriteAccess();
		PreparedStatement statement = connection.prepareStatement(CREATE_CITY_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, owner);
		statement.setBoolean(2, false);
		statement.setBoolean(3, false);
		statement.execute();
		ResultSet key = statement.getGeneratedKeys();
		key.first();
		return key.getInt(1);
	}

	public void saveCity(City city) throws SQLException
	{
		requireWriteAccess();
		city.update(this.connection);
	}
}
