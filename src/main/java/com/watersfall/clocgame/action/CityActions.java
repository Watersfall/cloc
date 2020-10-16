package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.CityDao;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.city.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationStats;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;
import java.util.HashMap;

public class CityActions
{
	public static String coalMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		if(city.getFreeSlots() <= 0)
		{
			return Responses.noBuildSlots();
		}
		int cost = city.getMineCost();
		if(nation.getStats().getBudget() < cost)
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
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.coalMine();
		}
	}

	public static String ironMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		if(city.getFreeSlots() <= 0)
		{
			return Responses.noBuildSlots();
		}
		int cost = city.getMineCost();
		if(nation.getStats().getBudget() < cost)
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
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.ironMine();
		}
	}

	public static String drill(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		if(city.getFreeSlots() <= 0)
		{
			return Responses.noBuildSlots();
		}
		int cost = city.getWellCost();
		if(nation.getStats().getBudget() < cost)
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
			nation.getStats().setBudget(nation.getStats().getBudget() - cost);
			return Responses.drill();
		}
	}

	public static String industrialize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		if(city.getFreeSlots() <= 0)
		{
			return Responses.noBuildSlots();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(nation.getStats().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getStats().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getStats().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
			nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
			nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
			city.setIndustryCivilian(city.getIndustryCivilian() + 1);
			return Responses.industrialize();
		}
	}

	public static String militarize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		if(city.getFreeSlots() <= 0)
		{
			return Responses.noBuildSlots();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(nation.getStats().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getStats().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getStats().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
			nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
			nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
			CityDao dao = new CityDao(nation.getConn(), true);
			dao.buildMilitaryIndustry(city);
			return Responses.militarize();
		}
	}

	public static String nitrogen(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		if(city.getFreeSlots() <= 0)
		{
			return Responses.noBuildSlots();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(nation.getStats().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getStats().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getStats().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
			nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
			nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
			city.setIndustryNitrogen(city.getIndustryNitrogen() + 1);
			return Responses.militarize();
		}
	}

	public static String university(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(nation.getFreeLand() < City.LAND_UNIVERSITY)
		{
			return Responses.noLand();
		}
		if(city.getFreeSlots() <= 0)
		{
			return Responses.noBuildSlots();
		}
		HashMap<String, Integer> cost = city.getUniversityCost();
		if(nation.getStats().getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(nation.getStats().getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(nation.getStats().getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
			nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
			nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
			city.setUniversities(city.getUniversities() + 1);
			return Responses.nitrogenPlant();
		}
	}

	public static String port(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		City city = nation.getCities().get(cityId);
		if(city == null)
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
			return Responses.port();
		}
	}

	public static String barrack(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		City city = nation.getCities().get(cityId);
		if(city == null)
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
			return Responses.barrack();
		}
	}

	public static String railroad(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		City city = nation.getCities().get(cityId);
		if(city == null)
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
			return Responses.railway();
		}
	}

	public static String closeCoalMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		if(city.getCoalMines() <= 0)
		{
			return Responses.no("coal mines");
		}
		else
		{
			city.setCoalMines(city.getCoalMines() - 1);
			return Responses.close("coal mine");
		}
	}

	public static String closeIronMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		if(city.getIronMines() <= 0)
		{
			return Responses.no("iron mines");
		}
		else
		{
			city.setIronMines(city.getIronMines() - 1);
			return Responses.close("iron mine");
		}
	}

	public static String closeDrill(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		if(city.getOilWells() <= 0)
		{
			return Responses.no("oil wells");
		}
		else
		{
			city.setOilWells(city.getOilWells() - 1);
			return Responses.close("oil well");
		}
	}

	public static String closeIndustrialize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(city.getIndustryCivilian() <= 0)
		{
			return Responses.no("factories");
		}
		else
		{
			city.setIndustryCivilian(city.getIndustryCivilian() - 1);
			return Responses.close("factory");
		}
	}

	public static String closeMilitarize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(city.getIndustryMilitary() <= 0)
		{
			return Responses.no("factories");
		}
		else
		{
			CityDao dao = new CityDao(nation.getConn(), true);
			dao.removeMilitaryIndustry(city);
			return Responses.close("factory");
		}
	}

	public static String closeNitrogen(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(city.getIndustryNitrogen() <= 0)
		{
			return Responses.no("factories");
		}

		else
		{
			city.setIndustryNitrogen(city.getIndustryNitrogen() - 1);
			return Responses.close("factory");
		}
	}

	public static String closeUniversity(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		if(city.getUniversities() <= 0)
		{
			return Responses.no("universities");
		}
		else
		{
			city.setUniversities(city.getUniversities() - 1);
			return Responses.close("university");
		}
	}

	public static String closePort(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(city.getPorts() <= 0)
		{
			return Responses.no("ports");
		}
		else
		{
			city.setPorts(city.getPorts() - 1);
			return Responses.close("port");
		}
	}

	public static String closeBarrack(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(city.getBarracks() <= 0)
		{
			return Responses.no("barracks");
		}
		else
		{
			city.setBarracks(city.getBarracks() - 1);
			return Responses.close("barracks");
		}
	}

	public static String closeRailroad(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(city.getRailroads() <= 0)
		{
			return Responses.no("railroads");
		}
		else
		{
			city.setRailroads(city.getRailroads() - 1);
			return Responses.close("railway");
		}
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
			return Responses.updated("Name");
		}
	}
}