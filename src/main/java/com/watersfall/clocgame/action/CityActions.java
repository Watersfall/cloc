package com.watersfall.clocgame.action;

import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationEconomy;
import com.watersfall.clocgame.text.Responses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class CityActions
{
	public static String coalMine(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		City city = nation.getCities().getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		int cost = city.getMineCost();
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getFreeLand() < City.LAND_MINE)
		{
			return Responses.noLand();
		}
		else
		{
			city.setCoalMines(city.getCoalMines() + 1);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.coalMine();
		}
	}

	public static String ironMine(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		City city = nation.getCities().getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		int cost = city.getMineCost();
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getFreeLand() < City.LAND_MINE)
		{
			return Responses.noLand();
		}
		else
		{
			city.setIronMines(city.getIronMines() + 1);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.ironMine();
		}
	}

	public static String drill(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		City city = nation.getCities().getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		int cost = city.getWellCost();
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getFreeLand() < City.LAND_MINE)
		{
			return Responses.noLand();
		}
		else
		{
			city.setOilWells(city.getOilWells() + 1);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.drill();
		}
	}

	public static String industrialize(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		City city = nation.getCities().getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(nation.getEconomy().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getEconomy().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getEconomy().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getEconomy().setCoal(nation.getEconomy().getCoal() - cost.get("coal"));
			nation.getEconomy().setIron(nation.getEconomy().getIron() - cost.get("iron"));
			nation.getEconomy().setSteel(nation.getEconomy().getSteel() - cost.get("steel"));
			city.setIndustryCivilian(city.getIndustryCivilian() + 1);
			nation.update();
			return Responses.industrialize();
		}
	}

	public static String militarize(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		City city = nation.getCities().getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(nation.getEconomy().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getEconomy().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getEconomy().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getEconomy().setCoal(nation.getEconomy().getCoal() - cost.get("coal"));
			nation.getEconomy().setIron(nation.getEconomy().getIron() - cost.get("iron"));
			nation.getEconomy().setSteel(nation.getEconomy().getSteel() - cost.get("steel"));
			city.buildMilitaryIndustry(conn);
			nation.update();
			return Responses.militarize();
		}
	}

	public static String nitrogen(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		City city = nation.getCities().getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(nation.getEconomy().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getEconomy().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getEconomy().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getEconomy().setCoal(nation.getEconomy().getCoal() - cost.get("coal"));
			nation.getEconomy().setIron(nation.getEconomy().getIron() - cost.get("iron"));
			nation.getEconomy().setSteel(nation.getEconomy().getSteel() - cost.get("steel"));
			city.setIndustryNitrogen(city.getIndustryNitrogen() + 1);
			nation.update();
			return Responses.militarize();
		}
	}

	public static String university(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		City city = nation.getCities().getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_UNIVERSITY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getUniversityCost();
		if(nation.getEconomy().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getEconomy().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getEconomy().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getEconomy().setCoal(nation.getEconomy().getCoal() - cost.get("coal"));
			nation.getEconomy().setIron(nation.getEconomy().getIron() - cost.get("iron"));
			nation.getEconomy().setSteel(nation.getEconomy().getSteel() - cost.get("steel"));
			city.setUniversities(city.getUniversities() + 1);
			nation.update();
			return Responses.nitrogenPlant();
		}
	}

	public static String port(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		NationEconomy economy = nation.getEconomy();
		City city = nation.getCities().getCities().get(idCity);
		if(idNation != city.getOwner())
		{
			return Responses.notYourCity();
		}
		int cost = city.getPortCost();
		if(economy.getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(!city.isCoastal())
		{
			return Responses.notCoastal();
		}
		else if(city.getPorts() >= 10)
		{
			return Responses.limit();
		}
		else
		{
			city.setPorts(city.getPorts() + 1);
			economy.setBudget(economy.getBudget() - cost);
			city.update(conn);
			economy.update(conn);
			return Responses.port();
		}
	}

	public static String barrack(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		NationEconomy economy = nation.getEconomy();
		City city = nation.getCities().getCities().get(idCity);
		if(idNation != city.getOwner())
		{
			return Responses.notYourCity();
		}
		int cost = city.getBarrackCost();
		if(economy.getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(city.getBarracks() >= 10)
		{
			return Responses.limit();
		}
		else
		{
			city.setBarracks(city.getBarracks() + 1);
			economy.setBudget(economy.getBudget() - cost);
			city.update(conn);
			economy.update(conn);
			return Responses.barrack();
		}
	}

	public static String railroad(Connection conn, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(conn, idNation, true);
		NationEconomy economy = nation.getEconomy();
		City city = nation.getCities().getCities().get(idCity);
		if(idNation != city.getOwner())
		{
			return Responses.notYourCity();
		}
		int cost = city.getRailCost();
		if(economy.getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(city.getRailroads() >= 10)
		{
			return Responses.limit();
		}
		else
		{
			city.setRailroads(city.getRailroads() + 1);
			economy.setBudget(economy.getBudget() - cost);
			city.update(conn);
			economy.update(conn);
			return Responses.railway();
		}
	}

	/**
	 * Called by the specific close actions with their specified type
	 * Should probably refactor out the specific methods in favor of just using this at some point
	 * @param conn The SQL Connection to use
	 * @param idNation The Nation ID the city is in
	 * @param idCity the City ID to remove from
	 * @param type Column name for the building to remove
	 * @return A displayable error message if one occurs, or null if there is no error
	 * @throws SQLException If a database error occurs
	 * @throws NationNotFoundException If the Nation with the specified ID does not exist
	 * @throws CityNotFoundException If the City with the specified ID does not exist
	 * @throws NotLoggedInException If the user attempting this is not logged in
	 */
	public static String remove(Connection conn, int idNation, int idCity, String type) throws SQLException, NationNotFoundException, CityNotFoundException, NotLoggedInException
	{
		City city = City.getCity(conn, idCity);
		if(city.getOwner() != idNation)
		{
			return Responses.notYourCity();
		}
		else if((Integer)city.getByName(type) <= 0)
		{
			return Responses.noneLeft();
		}
		else if(type.equalsIgnoreCase("military_industry"))
		{
			city.closeMilitaryIndustry(conn);
		}
		else
		{
			city.setByName(type, (Integer)city.getByName(type) - 1);
			city.update(conn);
		}
		return Responses.close(type);
	}

	public static String rename(City city, String name, Connection conn) throws SQLException, NullPointerException
	{
		if(name.length() > 64)
		{
			return Responses.tooLong("Name", 64);
		}
		else
		{
			city.setName(name);
			city.update(conn);
			return Responses.updated("Name");
		}
	}
}
