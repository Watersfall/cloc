package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationEconomy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class CityActions
{
	public static String coalMine(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
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

	public static String ironMine(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
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

	public static String drill(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
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

	public static String industrialize(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
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

	public static String militarize(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
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
			city.setIndustryMilitary(city.getIndustryMilitary() + 1);
			nation.update();
			return Responses.militarize();
		}
	}

	public static String nitrogen(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
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

	public static String university(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
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

	public static String port(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		City city = new City(connection, idCity, true);
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
			city.update();
			economy.update();
			return Responses.port();
		}
	}

	public static String barrack(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		City city = new City(connection, idCity, true);
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
			city.update();
			economy.update();
			return Responses.barrack();
		}
	}

	public static String railroad(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		City city = new City(connection, idCity, true);
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
			city.update();
			economy.update();
			return Responses.railway();
		}
	}

	/**
	 * Called by the specific close actions with their specified type
	 * Should probably refactor out the specific methods in favor of just using this at some point
	 * @param connection The SQL Connection to use
	 * @param idNation The Nation ID the city is in
	 * @param idCity the City ID to remove from
	 * @param type Magic int representing the type of building to remove
	 * @return A displayable error message if one occurs, or null if there is no error
	 * @throws SQLException If a database error occurs
	 * @throws NationNotFoundException If the Nation with the specified ID does not exist
	 * @throws CityNotFoundException If the City with the specified ID does not exist
	 * @throws NotLoggedInException If the user attempting this is not logged in
	 */
	private static String remove(Connection connection, int idNation, int idCity, int type) throws SQLException, NationNotFoundException, CityNotFoundException, NotLoggedInException
	{
		City city = new City(connection, idCity, true);
		if(city.getOwner() != idNation)
		{
			return Responses.notYourCity();
		}
		else
		{
			switch(type)
			{
				case 0:
					if(city.getCoalMines() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setCoalMines(city.getCoalMines() - 1);
						break;
					}
				case 1:
					if(city.getIronMines() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setIronMines(city.getIronMines() - 1);
						break;
					}
				case 2:
					if(city.getOilWells() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setOilWells(city.getOilWells() - 1);
						break;
					}
				case 3:
					if(city.getIndustryCivilian() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setIndustryCivilian(city.getIndustryCivilian() - 1);
						break;
					}
				case 4:
					if(city.getIndustryMilitary() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setIndustryMilitary(city.getIndustryMilitary() - 1);
						break;
					}
				case 5:
					if(city.getIndustryNitrogen() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setIndustryNitrogen(city.getIndustryNitrogen() - 1);
						break;
					}
				case 6:
					if(city.getUniversities() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setUniversities(city.getUniversities() - 1);
						break;
					}
				case 7:
					if(city.getPorts() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setPorts(city.getPorts() - 1);
						break;
					}
				case 8:
					if(city.getBarracks() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setBarracks(city.getBarracks() - 1);
						break;
					}
				case 9:
					if(city.getRailroads() <= 0)
					{
						return Responses.noneLeft();
					}
					else
					{
						city.setRailroads(city.getRailroads() - 1);
						break;
					}
				default:
					return Responses.genericError();

			}
			city.update();
			return null;
		}
	}

	public static String unCoalMine(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 0);
		return (remove == null) ? Responses.close("coal mine") : remove;
	}

	public static String unIronMine(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 1);
		return (remove == null) ? Responses.closeN("iron mine") : remove;
	}

	public static String unDrill(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 2);
		return (remove == null) ? Responses.closeN("oil well") : remove;
	}

	public static String unIndustrialize(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 3);
		return (remove == null) ? Responses.close("factory") : remove;
	}

	public static String unMilitarize(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 4);
		return (remove == null) ? Responses.close("factory") : remove;
	}

	public static String unNitrogen(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 5);
		return (remove == null) ? Responses.close("factory") : remove;
	}

	public static String unUniversity(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 6);
		return (remove == null) ? Responses.close("university") : remove;
	}

	public static String unPort(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 7);
		return (remove == null) ? Responses.closePort() : remove;
	}

	public static String unBarrack(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 8);
		return (remove == null) ? Responses.closeBarrack() : remove;
	}

	public static String unRailroad(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		String remove = remove(connection, idNation, idCity, 9);
		return (remove == null) ? Responses.closeRailroad() : remove;
	}

	public static String rename(City city, String name) throws SQLException, NullPointerException
	{
		if(name.length() > 64)
		{
			return Responses.tooLong("Name", 64);
		}
		else
		{
			city.setName(name);
			city.update();
			return Responses.updated("Name");
		}
	}
}
