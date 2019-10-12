package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class PolicyActions
{
	public PolicyActions(){}

	//<editor-fold desc="Constants"
	//<editor-fold desc="Costs"
	private static final int CRACKDOWN_COST = 100;
	private static final int FREE_COST = 100;
	private static final int ALIGN_COST = 50;
	private static final int ARTILLERY_STEEL = 15;
	private static final int ARTILLERY_NITROGEN = 7;
	private static final int WEAPONS_STEEL = 5;
	private static final int LAND_CLEARANCE = 10000;
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

	public static String landClearance(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationDomestic domestic = new NationDomestic(connection, idNation, true);
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		if(economy.getBudget() < LAND_CLEARANCE)
		{
			return Responses.noMoney();
		}
		else
		{
			int gain = (int)(Math.random() * 2500) + 500;
			economy.setBudget(economy.getBudget() - FREE_COST);
			domestic.setLand(domestic.getLand() + gain);
			domestic.update();
			economy.update();
			return Responses.landClearance(gain);
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
		if(nation.getEconomy().getBudget() < nation.getTrainingCost())
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
			nation.getEconomy().setBudget(nation.getEconomy().getBudget() - nation.getTrainingCost());
			nation.update();
			return Responses.train();
		}
	}

	public static String artillery(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationArmy army = new NationArmy(connection, idNation, true);
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
			army.setArtillery(army.getArtillery() + ARTILLERY_GAIN);
			economy.setSteel(economy.getSteel() - ARTILLERY_STEEL);
			economy.setNitrogen(economy.getNitrogen() - ARTILLERY_NITROGEN);
			army.update();
			economy.update();
			return Responses.artillery();
		}
	}

	public static String weapons(Connection connection, int idNation) throws SQLException, NationNotFoundException, NullPointerException, NotLoggedInException
	{
		NationArmy army = new NationArmy(connection, idNation, true);
		NationEconomy economy = new NationEconomy(connection, idNation, true);
		if(economy.getSteel() < WEAPONS_STEEL)
		{
			return Responses.noSteel();
		}
		else
		{
			army.setMusket(army.getMusket() + WEAPONS_GAIN);
			economy.setSteel(economy.getSteel() - WEAPONS_STEEL);
			army.update();
			economy.update();
			return Responses.weapons();
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

	public int getLandClearance()
	{
		return LAND_CLEARANCE;
	}
	//</editor-fold>
}
