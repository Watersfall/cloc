package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.NationArmy;
import com.watersfall.clocgame.model.nation.NationEconomy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PolicyActions
{
	public PolicyActions(){}

	//<editor-fold desc="Constants"
	//<editor-fold desc="IDs">
	public static final int ID_COAL_MINE = 0;
	public static final int ID_IRON_MINE = 1;
	public static final int ID_DRILL = 2;
	public static final int ID_INDUSTRIALIZE = 3;
	public static final int ID_MILITARIZE = 4;
	public static final int ID_NITROGEN_PLANT = 5;
	public static final int ID_UNIVERSITY = 6;
	public static final int ID_PORT = 7;
	public static final int ID_BARRACK = 8;
	public static final int ID_RAILROAD = 9;
	public static final int ID_ARREST = 10;
	public static final int ID_FREE = 11;
	public static final int ID_LAND_CLEARANCE = 12;
	public static final int ID_PROPAGANDA = 13;
	public static final int ID_WAR_PROPAGANDA = 14;
	public static final int ID_ALIGN = 15;
	public static final int ID_TRAIN = 16;
	public static final int ID_BUILD_MUSKETS = 17;
	public static final int ID_BUILD_ARTILLERY = 18;
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
	private static final int WEAPONS_GAIN = 1000;
	//</editor-fold>
	//</editor-fold>

	//<editor-fold desc="City Policies">
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
	//</editor-fold>

	//<editor-fold desc="Domestic Policies">
	public static String arrest(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
		int cost = nation.getPolicyCost(ID_ARREST);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getGovernment() < 5)
		{
			return Responses.noCriminals();
		}
		else if(nation.getDomestic().getApproval() < 5)
		{
			return Responses.hated();
		}
		else
		{
			nation.getDomestic().setStability(nation.getDomestic().getStability() + CRACKDOWN_STAB);
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + CRACKDOWN_APPROVAL);
			nation.getDomestic().setGovernment(nation.getDomestic().getGovernment() + CRACKDOWN_GOV);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.getDomestic().update();
			nation.getEconomy().update();
			return Responses.arrest();
		}
	}

	public static String free(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
		int cost = nation.getPolicyCost(ID_FREE);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getGovernment() > 95)
		{
			return Responses.noPrisoners();
		}
		else if(nation.getDomestic().getStability() < 5)
		{
			return Responses.unstable();
		}
		else
		{
			nation.getDomestic().setStability(nation.getDomestic().getStability() + FREE_STAB);
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + FREE_APPROVAL);
			nation.getDomestic().setGovernment(nation.getDomestic().getGovernment() + FREE_GOV);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.getDomestic().update();
			nation.getEconomy().update();
			return Responses.free();
		}
	}

	public static String landClearance(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
		int cost = nation.getPolicyCost(ID_LAND_CLEARANCE);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else
		{
			int gain = (int)(Math.random() * 2500) + 500;
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.getDomestic().setLand(nation.getDomestic().getLand() + gain);
			nation.getDomestic().update();
			nation.getEconomy().update();
			return Responses.landClearance(gain);
		}
	}

	public static String propaganda(Connection connection, int id) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, id, true);
		int cost = nation.getPolicyCost(ID_PROPAGANDA);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getApproval() > 99)
		{
			return Responses.propagandaMaxApproval();
		}
		else
		{
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + 10);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.propaganda();
		}
	}

	public static String warPropaganda(Connection connection, int id) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, id, true);
		int cost = nation.getPolicyCost(ID_WAR_PROPAGANDA);
		if(nation.getOffensive() == 0 && nation.getDefensive() == 0)
		{
			return Responses.propagandaNoWar();
		}
		else if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getDomestic().getApproval() > 99)
		{
			return Responses.propagandaMaxApproval();
		}
		else
		{
			nation.getDomestic().setApproval(nation.getDomestic().getApproval() + 10);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.propaganda();
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
		Nation nation = new Nation(connection, idNation, true);
		int cost = nation.getPolicyCost(ID_ALIGN);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getForeign().getAlignment() == align)
		{
			return Responses.alreadyYourAlignment();
		}
		else
		{
			nation.getForeign().setAlignment(align);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.getForeign().update();
			nation.getEconomy().update();
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
	public static String conscript(Connection conn, int id) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		Nation nation = new Nation(conn, id, true);
		if(nation.getFreeManpower() < 2000)
		{
			return Responses.noManpower();
		}
		else
		{
			nation.getArmy().setSize(nation.getArmy().getSize() + 2);
			nation.getArmy().update();
			return Responses.conscript();
		}
	}

	public static String deconscript(Connection conn, int id) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		NationArmy army = new NationArmy(conn, id, true);
		if(army.getSize() <= 5)
		{
			return Responses.noTroops();
		}
		else
		{
			army.setSize(army.getSize() - 2);
			army.update();
			return Responses.deconscript();
		}
	}

	public static String train(Connection conn, int id) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException, CityNotFoundException
	{
		Nation nation = new Nation(conn, id, true);
		int cost = nation.getPolicyCost(ID_TRAIN);
		if(nation.getEconomy().getBudget() < cost)
		{
			return Responses.noMoney();
		}
		else if(nation.getArmy().getTraining() >= 100)
		{
			return Responses.fullTrained();
		}
		else
		{
			nation.getArmy().setTraining(nation.getArmy().getTraining() + 5);
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - cost);
			nation.update();
			return Responses.train();
		}
	}

	public static String artillery(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
		Map<String, Integer> costs = nation.getPolicyCostMap(ID_BUILD_ARTILLERY);
		if(nation.getEconomy().getSteel() < costs.get("steel"))
		{
			return Responses.noSteel();
		}
		else if(nation.getEconomy().getNitrogen() < costs.get("nitrogen"))
		{
			return Responses.noNitrogen();
		}
		else
		{
			nation.getArmy().setArtillery(nation.getArmy().getArtillery() + ARTILLERY_GAIN);
			nation.getEconomy().setSteel(nation.getEconomy().getSteel() - costs.get("steel"));
			nation.getEconomy().setNitrogen(nation.getEconomy().getNitrogen() - costs.get("nitrogen"));
			nation.getArmy().update();
			nation.getEconomy().update();
			return Responses.artillery();
		}
	}

	public static String weapons(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		Nation nation = new Nation(connection, idNation, true);
		Map<String, Integer> costs = nation.getPolicyCostMap(ID_BUILD_MUSKETS);
		if(nation.getEconomy().getSteel() < costs.get("steel"))
		{
			return Responses.noSteel();
		}
		else
		{
			nation.getArmy().setMusket(nation.getArmy().getMusket() + WEAPONS_GAIN);
			nation.getEconomy().setSteel(nation.getEconomy().getSteel() - costs.get("steel"));
			nation.getArmy().update();
			nation.getEconomy().update();
			return Responses.weapons();
		}
	}
	//</editor-fold>
}
