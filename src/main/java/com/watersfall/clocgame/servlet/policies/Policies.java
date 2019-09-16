package com.watersfall.clocgame.servlet.policies;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class Policies
{
	public Policies(){}

	//<editor-fold desc="Constants"
	//<editor-fold desc="Costs"
	private static final int CRACKDOWN_COST = 100;
	private static final int FREE_COST = 100;
	private static final int ALIGN_COST = 50;
	private static final int ARTILLERY_STEEL = 15;
	private static final int ARTILLERY_NITROGEN = 7;
	private static final int WEAPONS_STEEL = 5;
	//</editor-fold>
	//<editor-fold desc="Modifiers"
	private static final int CRACKDOWN_STAB = 5;
	private static final int CRACKDOWN_APPROVAL = -5;
	private static final int CRACKDOWN_GOV = -5;
	private static final int FREE_STAB = -5;
	private static final int FREE_APPROVAL = 5;
	private static final int FREE_GOV = 5;
	private static final int FREE_MONEY_ECON = 5;
	//</editor-fold>
	//<editor-fold desc="Gain">
	private static final int FREE_MONEY_GAIN = 1000;
	private static final int ARTILLERY_GAIN = 3;
	private static final int WEAPONS_GAIN = 25;
//</editor-fold>
	//</editor-fold>

	//<editor-fold desc="City Policies">
	public static String coalMine(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		NationCities cities = new NationCities(connection, idNation, true);
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		City city = cities.getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		int cost = city.getMineCost();
		if(economy.getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(domestic.getLand() - cities.getTotalLandUsage() < City.LAND_MINE)
		{
			return Responses.noLand();
		}
		else
		{
			city.setCoalMines(city.getCoalMines() + 1);
			economy.setBudget(economy.getBudget() - cost);
			city.update();
			economy.update();
			return Responses.coalMine();
		}
	}

	public static String ironMine(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		NationCities cities = new NationCities(connection, idNation, true);
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		City city = cities.getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		int cost = city.getMineCost();
		if(economy.getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(domestic.getLand() - cities.getTotalLandUsage() < City.LAND_MINE)
		{
			return Responses.noLand();
		}
		else
		{
			city.setIronMines(city.getIronMines() + 1);
			economy.setBudget(economy.getBudget() - cost);
			city.update();
			economy.update();
			return Responses.ironMine();
		}
	}

	public static String drill(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		NationCities cities = new NationCities(connection, idNation, true);
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		City city = cities.getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		int cost = city.getWellCost();
		if(economy.getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(domestic.getLand() - cities.getTotalLandUsage() < City.LAND_MINE)
		{
			return Responses.noLand();
		}
		else
		{
			city.setOilWells(city.getOilWells() + 1);
			economy.setBudget(economy.getBudget() - cost);
			city.update();
			economy.update();
			return Responses.drill();
		}
	}

	public static String industrialize(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		NationCities cities = new NationCities(connection, idNation, true);
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		City city = cities.getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(domestic.getLand() - cities.getTotalLandUsage() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(economy.getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(economy.getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(economy.getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			economy.setCoal(economy.getCoal() - cost.get("coal"));
			economy.setIron(economy.getIron() - cost.get("iron"));
			economy.setSteel(economy.getSteel() - cost.get("steel"));
			city.setIndustryCivilian(city.getIndustryCivilian() + 1);
			city.update();
			economy.update();
			return Responses.industrialize();
		}
	}

	public static String militarize(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		NationCities cities = new NationCities(connection, idNation, true);
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		City city = cities.getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(domestic.getLand() - cities.getTotalLandUsage() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(economy.getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(economy.getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(economy.getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			economy.setCoal(economy.getCoal() - cost.get("coal"));
			economy.setIron(economy.getIron() - cost.get("iron"));
			economy.setSteel(economy.getSteel() - cost.get("steel"));
			city.setIndustryMilitary(city.getIndustryMilitary() + 1);
			city.update();
			economy.update();
			return Responses.militarize();
		}
	}

	public static String nitrogen(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		NationCities cities = new NationCities(connection, idNation, true);
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		City city = cities.getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(domestic.getLand() - cities.getTotalLandUsage() < City.LAND_FACTORY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getFactoryCost();
		if(economy.getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(economy.getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(economy.getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			economy.setCoal(economy.getCoal() - cost.get("coal"));
			economy.setIron(economy.getIron() - cost.get("iron"));
			economy.setSteel(economy.getSteel() - cost.get("steel"));
			city.setIndustryNitrogen(city.getIndustryNitrogen() + 1);
			city.update();
			economy.update();
			return Responses.nitrogenPlant();
		}
	}

	public static String university(Connection connection, int idNation, int idCity) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		NationCities cities = new NationCities(connection, idNation, true);
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		City city = cities.getCities().get(idCity);
		if(city == null)
		{
			return Responses.notYourCity();
		}
		else if(domestic.getLand() - cities.getTotalLandUsage() < City.LAND_UNIVERSITY)
		{
			return Responses.noLand();
		}
		HashMap<String, Integer> cost = city.getUniversityCost();
		if(economy.getCoal() < cost.get("coal"))
		{
			return Responses.noCoal();
		}
		else if(economy.getIron() < cost.get("iron"))
		{
			return Responses.noIron();
		}
		else if(economy.getSteel() < cost.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			economy.setCoal(economy.getCoal() - cost.get("coal"));
			economy.setIron(economy.getIron() - cost.get("iron"));
			economy.setSteel(economy.getSteel() - cost.get("steel"));
			city.setUniversities(city.getUniversities() + 1);
			city.update();
			economy.update();
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

	private static String remove(Connection connection, int idNation, int idCity, int type) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
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
		String remove = remove(connection, idNation, idCity, 7);
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
	//</editor-fold>

	//<editor-fold desc="Domestic Policies">
	public static String arrest(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		if(economy.getBudget() < CRACKDOWN_COST)
		{
			return Responses.noMoney();
		}
		else if(domestic.getGovernment() < 5)
		{
			return Responses.noCriminals();
		}
		else if(domestic.getApproval() < 5)
		{
			return Responses.hated();
		}
		else
		{
			domestic.setStability(domestic.getStability() + CRACKDOWN_STAB);
			domestic.setApproval(domestic.getApproval() + CRACKDOWN_APPROVAL);
			domestic.setGovernment(domestic.getGovernment() + CRACKDOWN_GOV);
			economy.setBudget(economy.getBudget() - CRACKDOWN_COST);
			domestic.update();
			economy.update();
			return Responses.arrest();
		}
	}

	public static String free(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		if(economy.getBudget() < FREE_COST)
		{
			return Responses.noMoney();
		}
		else if(domestic.getGovernment() > 95)
		{
			return Responses.noPrisoners();
		}
		else if(domestic.getStability() < 5)
		{
			return Responses.unstable();
		}
		else
		{
			domestic.setStability(domestic.getStability() + FREE_STAB);
			domestic.setApproval(domestic.getApproval() + FREE_APPROVAL);
			domestic.setGovernment(domestic.getGovernment() + FREE_GOV);
			economy.setBudget(economy.getBudget() - FREE_COST);
			domestic.update();
			economy.update();
			return Responses.free();
		}
	}
	//</editor-fold>

	//<editor-fold desc="Economic Policies">
	public static String freeMoneyCapitalist(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		economy.setBudget(economy.getBudget() + FREE_MONEY_GAIN);
		economy.setEconomic(economy.getEconomic() + FREE_MONEY_ECON);
		economy.update();
		return Responses.freeMoneyCapitalist();
	}

	public static String freeMoneyCommunist(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		economy.setBudget(economy.getBudget() + FREE_MONEY_GAIN);
		economy.setEconomic(economy.getEconomic() - FREE_MONEY_ECON);
		economy.update();
		return Responses.freeMoneyCommunist();
	}
	//</editor-fold>

	//<editor-fold desc="Foreign Policies">
	private static String align(Connection connection, int idNation, int align) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationForeign foreign = new NationForeign(connection, idNation, true);
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		if(economy.getBudget() < ALIGN_COST)
		{
			return Responses.noMoney();
		}
		else if(foreign.getAlignment() == align)
		{
			return Responses.alreadyYourAlignment();
		}
		else
		{
			foreign.setAlignment(align);
			economy.setBudget(economy.getBudget() - ALIGN_COST);
			foreign.update();
			economy.update();
			return Responses.align(align);
		}
	}

	public static String alignEntente(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(connection, idNation, 1);
	}

	public static String alignNeutral(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(connection, idNation, 0);
	}

	public static String alignCentralPowers(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		return align(connection, idNation, -1);
	}
	//</editor-fold>

	//<editor-fold desc="Military Policies">
	public static String artillery(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationMilitary military = new NationMilitary(connection, idNation, true);
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		if(economy.getSteel() < ARTILLERY_STEEL)
		{
			return Responses.noSteel();
		}
		else if(economy.getNitrogen() < ARTILLERY_NITROGEN)
		{
			return Responses.noNitrogen();
		}
		else
		{
			military.setStockpileArtillery(military.getStockpileArtillery() + ARTILLERY_GAIN);
			economy.setSteel(economy.getSteel() - ARTILLERY_STEEL);
			economy.setNitrogen(economy.getNitrogen() - ARTILLERY_NITROGEN);
			military.update();
			economy.update();
			return Responses.artillery();
		}
	}

	public static String weapons(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationMilitary military = new NationMilitary(connection, idNation, true);
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		if(economy.getSteel() < WEAPONS_STEEL)
		{
			return Responses.noSteel();
		}
		else
		{
			military.setStockpileWeapons(military.getStockpileWeapons() + WEAPONS_GAIN);
			economy.setSteel(economy.getSteel() - WEAPONS_STEEL);
			military.update();
			economy.update();
			return Responses.weapons();
		}
	}
	//</editor-fold>

	//<editor-fold desc="Army Policies">
	public static String conscript(Connection conn, int idArmy, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		Nation nation = new Nation(conn, idNation, true);
		if(nation.getFreeManpower() < 2000)
		{
			return Responses.noManpower();
		}
		else if(nation.getArmies().getArmies().get(idArmy) == null)
		{
			return Responses.notYourArmy();
		}
		else
		{
			nation.getArmies().getArmies().get(idArmy).setArmy(nation.getArmies().getArmies().get(idArmy).getArmy() + 2);
			nation.getArmies().getArmies().get(idArmy).update();
			return Responses.conscript();
		}
	}

	public static String deconscript(Connection conn, int idArmy, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		Army army = new Army(conn, idArmy, true);
		if(army.getOwner() != idNation)
		{
			return Responses.notYourArmy();
		}
		else if(army.getArmy() <= 5)
		{
			return Responses.noTroops();
		}
		else
		{
			army.setArmy(army.getArmy() - 2);
			army.update();
			return Responses.deconscript();
		}
	}

	public static String train(Connection conn, int idArmy, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		Army army = new Army(conn, idArmy, true);
		NationEconomy economy = new NationEconomy(conn, idNation, true);
		if(army.getOwner() != idNation)
		{
			return Responses.notYourCity();
		}
		else if(economy.getBudget() < army.getTrainingCost())
		{
			return Responses.noMoney();
		}
		else if(army.getTraining() >= 100)
		{
			return Responses.fullTrained();
		}
		else
		{
			army.setTraining(army.getTraining() + 5);
			economy.setBudget(economy.getBudget() - army.getTrainingCost());
			army.update();
			economy.update();
			return Responses.train();
		}
	}
	//</editor-fold>

	//<editor-fold desc="EL Getters">
	public int getCrackdownCost()
	{
		return CRACKDOWN_COST;
	}

	public int getFreeCost()
	{
		return FREE_COST;
	}

	public int getAlignCost()
	{
		return ALIGN_COST;
	}

	public int getArtillerySteel()
	{
		return ARTILLERY_STEEL;
	}

	public int getArtilleryNitrogen()
	{
		return ARTILLERY_NITROGEN;
	}

	public int getWeaponsSteel()
	{
		return WEAPONS_STEEL;
	}
	//</editor-fold>
}
