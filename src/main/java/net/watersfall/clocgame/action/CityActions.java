package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.CityDao;
import net.watersfall.clocgame.exception.CityNotFoundException;
import net.watersfall.clocgame.exception.NationNotFoundException;
import net.watersfall.clocgame.exception.NotLoggedInException;
import net.watersfall.clocgame.model.TextKey;
import net.watersfall.clocgame.model.city.City;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.nation.NationStats;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Util;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;

public class CityActions
{
	public static String coalMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getFreeSlots() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noBuildSlots());
		}
		else
		{
			int cost = city.getMineCost();
			if(nation.getStats().getBudget() < cost)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			}
			else if(nation.getFreeLand() < City.LAND_MINE)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noLand());
			}
			else
			{
				city.setCoalMines(city.getCoalMines() + 1);
				nation.getStats().setBudget(nation.getStats().getBudget() - cost);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.coalMine());
				object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
				object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
				object.put(JsonFields.CITY_COAL_MINES.name() + "_" + city.getId(), city.getCoalMines());
				object.put(JsonFields.CITY_COAL_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_IRON_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_OIL_WELL_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_COAL_PRODUCTION.name() + "_" + city.getId(), city.getCoalProduction().get(TextKey.Resource.TOTAL_GAIN));
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
			}
		}
		return object.toString();
	}

	public static String ironMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getFreeSlots() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noBuildSlots());
		}
		else
		{
			int cost = city.getMineCost();
			if(nation.getStats().getBudget() < cost)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			}
			else if(nation.getFreeLand() < City.LAND_MINE)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noLand());
			}
			else
			{
				city.setIronMines(city.getIronMines() + 1);
				nation.getStats().setBudget(nation.getStats().getBudget() - cost);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.ironMine());
				object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
				object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
				object.put(JsonFields.CITY_IRON_MINES.name() + "_" + city.getId(), city.getIronMines());
				object.put(JsonFields.CITY_COAL_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_IRON_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_OIL_WELL_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_IRON_PRODUCTION.name() + "_" + city.getId(), city.getIronProduction().get(TextKey.Resource.TOTAL_GAIN));
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
			}
		}
		return object.toString();
	}

	public static String drill(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getFreeSlots() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noBuildSlots());
		}
		else
		{
			int cost = city.getMineCost();
			if(nation.getStats().getBudget() < cost)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noMoney());
			}
			else if(nation.getFreeLand() < City.LAND_MINE)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noLand());
			}
			else
			{
				city.setOilWells(city.getOilWells() + 1);
				nation.getStats().setBudget(nation.getStats().getBudget() - cost);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.drill());
				object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
				object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
				object.put(JsonFields.CITY_OIL_WELLS.name() + "_" + city.getId(), city.getOilWells());
				object.put(JsonFields.CITY_COAL_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_IRON_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_OIL_WELL_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
				object.put(JsonFields.CITY_OIL_PRODUCTION.name() + "_" + city.getId(), city.getOilProduction().get(TextKey.Resource.TOTAL_GAIN));
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
			}
		}
		return object.toString();
	}

	public static String industrialize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getFreeSlots() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noBuildSlots());
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noLand());
		}
		else
		{
			HashMap<String, Integer> cost = city.getFactoryCost();
			if(nation.getStats().getCoal() < cost.get("coal"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noCoal());
			}
			else if(nation.getStats().getIron() < cost.get("iron"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noIron());
			}
			else if(nation.getStats().getSteel() < cost.get("steel"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noSteel());
			}
			else
			{
				nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
				nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
				nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
				city.setIndustryCivilian(city.getIndustryCivilian() + 1);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.industrialize());
				object.put(JsonFields.CITY_CIVILIAN_INDUSTRY + "_" + city.getId(), city.getIndustryCivilian());
				object.put(JsonFields.COAL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getCoal()));
				object.put(JsonFields.COAL.name(), Util.formatNumber(nation.getStats().getCoal()));
				object.put(JsonFields.IRON_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getIron()));
				object.put(JsonFields.IRON.name(), Util.formatNumber(nation.getStats().getIron()));
				object.put(JsonFields.STEEL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getSteel()));
				object.put(JsonFields.STEEL.name(), Util.formatNumber(nation.getStats().getSteel()));
				object.put(JsonFields.CITY_STEEL_PRODUCTION + "_" + city.getId(), city.getSteelProduction().get(TextKey.Resource.TOTAL_GAIN));
				object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost().toString());
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());

			}
		}
		return object.toString();
	}

	public static String militarize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getFreeSlots() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noBuildSlots());
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noLand());
		}
		else
		{
			HashMap<String, Integer> cost = city.getFactoryCost();
			if(nation.getStats().getCoal() < cost.get("coal"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noCoal());
			}
			else if(nation.getStats().getIron() < cost.get("iron"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noIron());
			}
			else if(nation.getStats().getSteel() < cost.get("steel"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noSteel());
			}
			else
			{
				nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
				nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
				nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
				CityDao dao = new CityDao(nation.getConn(), true);
				dao.buildMilitaryIndustry(city);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.militarize());
				object.put(JsonFields.CITY_MILITARY_INDUSTRY + "_" + city.getId(), city.getIndustryMilitary() + 1);
				object.put(JsonFields.COAL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getCoal()));
				object.put(JsonFields.COAL.name(), Util.formatNumber(nation.getStats().getCoal()));
				object.put(JsonFields.IRON_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getIron()));
				object.put(JsonFields.IRON.name(), Util.formatNumber(nation.getStats().getIron()));
				object.put(JsonFields.STEEL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getSteel()));
				object.put(JsonFields.STEEL.name(), Util.formatNumber(nation.getStats().getSteel()));
				object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(true).toString());
				object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(true).toString());
				object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(true).toString());
				object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost(true).toString());
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots() - 1);
			}
		}
		return object.toString();
	}

	public static String nitrogen(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getFreeSlots() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noBuildSlots());
		}
		else if(nation.getFreeLand() < City.LAND_FACTORY)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noLand());
		}
		else
		{
			HashMap<String, Integer> cost = city.getFactoryCost();
			if(nation.getStats().getCoal() < cost.get("coal"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noCoal());
			}
			else if(nation.getStats().getIron() < cost.get("iron"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noIron());
			}
			else if(nation.getStats().getSteel() < cost.get("steel"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noSteel());
			}
			else
			{
				nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
				nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
				nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
				city.setIndustryNitrogen(city.getIndustryNitrogen() + 1);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.industrialize());
				object.put(JsonFields.CITY_NITROGEN_INDUSTRY + "_" + city.getId(), city.getIndustryNitrogen());
				object.put(JsonFields.COAL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getCoal()));
				object.put(JsonFields.COAL.name(), Util.formatNumber(nation.getStats().getCoal()));
				object.put(JsonFields.IRON_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getIron()));
				object.put(JsonFields.IRON.name(), Util.formatNumber(nation.getStats().getIron()));
				object.put(JsonFields.STEEL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getSteel()));
				object.put(JsonFields.STEEL.name(), Util.formatNumber(nation.getStats().getSteel()));
				object.put(JsonFields.CITY_NITROGEN_PRODUCTION + "_" + city.getId(), city.getNitrogenProduction().get(TextKey.Resource.TOTAL_GAIN));
				object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost().toString());
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());

			}
		}
		return object.toString();
	}

	public static String university(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getFreeSlots() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noBuildSlots());
		}
		else if(nation.getFreeLand() < City.LAND_UNIVERSITY)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noLand());
		}
		else
		{
			HashMap<String, Integer> cost = city.getUniversityCost();
			if(nation.getStats().getCoal() < cost.get("coal"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noCoal());
			}
			else if(nation.getStats().getIron() < cost.get("iron"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noIron());
			}
			else if(nation.getStats().getSteel() < cost.get("steel"))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noSteel());
			}
			else
			{
				nation.getStats().setCoal(nation.getStats().getCoal() - cost.get("coal"));
				nation.getStats().setIron(nation.getStats().getIron() - cost.get("iron"));
				nation.getStats().setSteel(nation.getStats().getSteel() - cost.get("steel"));
				city.setUniversities(city.getUniversities() + 1);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.industrialize());
				object.put(JsonFields.CITY_UNIVERSITIES + "_" + city.getId(), city.getUniversities());
				object.put(JsonFields.COAL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getCoal()));
				object.put(JsonFields.COAL.name(), Util.formatNumber(nation.getStats().getCoal()));
				object.put(JsonFields.IRON_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getIron()));
				object.put(JsonFields.IRON.name(), Util.formatNumber(nation.getStats().getIron()));
				object.put(JsonFields.STEEL_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getSteel()));
				object.put(JsonFields.STEEL.name(), Util.formatNumber(nation.getStats().getSteel()));
				object.put(JsonFields.CITY_RESEARCH_PRODUCTION + "_" + city.getId(), city.getResearchProduction().get(TextKey.Resource.TOTAL_GAIN));
				object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
				object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost().toString());
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
			}
		}
		return object.toString();
	}

	public static String port(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else
		{
			int cost = city.getPortCost();
			if(economy.getBudget() < cost)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
			}
			else if(!city.isCoastal())
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.notCoastal());
			}
			else if(city.getPorts() >= 10)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.limit());
			}
			else
			{
				city.setPorts(city.getPorts() + 1);
				economy.setBudget(economy.getBudget() - cost);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.port());
				object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
				object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
				object.put(JsonFields.CITY_PORTS.name() + "_" + city.getId(), city.getPorts());
				object.put(JsonFields.CITY_PORT_COST.name() + "_" + city.getId(), city.getPortCost());
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
			}
		}
		return object.toString();
	}

	public static String barrack(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else
		{
			int cost = city.getBarrackCost();
			if(economy.getBudget() < cost)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
			}
			else if(city.getBarracks() >= 10)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.limit());
			}
			else
			{
				city.setBarracks(city.getBarracks() + 1);
				economy.setBudget(economy.getBudget() - cost);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.barrack());
				object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
				object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
				object.put(JsonFields.CITY_BARRACKS.name() + "_" + city.getId(), city.getBarracks());
				object.put(JsonFields.CITY_BARRACK_COST.name() + "_" + city.getId(), city.getBarrackCost());
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
			}
		}
		return object.toString();
	}

	public static String railroad(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		NationStats economy = nation.getStats();
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else
		{
			int cost = city.getRailCost();
			if(economy.getBudget() < cost)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
			}
			else if(city.getRailroads() >= 10)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.limit());
			}
			else
			{
				city.setRailroads(city.getRailroads() + 1);
				economy.setBudget(economy.getBudget() - cost);
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.port());
				object.put(JsonFields.BUDGET_SHORT.name(), Util.formatDisplayNumber(nation.getStats().getBudget()));
				object.put(JsonFields.BUDGET.name(), Util.formatNumber(nation.getStats().getBudget()));
				object.put(JsonFields.CITY_INFRASTRUCTURE.name() + "_" + city.getId(), city.getRailroads());
				object.put(JsonFields.CITY_INFRASTRUCTURE_COST.name() + "_" + city.getId(), city.getRailCost());
				object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
				object.put(JsonFields.CITY_MAX_BUILD_SLOTS.name() + "_" + city.getId(), city.getBuildSlots());
			}
		}
		return object.toString();
	}

	public static String closeCoalMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getCoalMines() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("coal mines"));
		}
		else
		{
			city.setCoalMines(city.getCoalMines() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("coal mine"));
			object.put(JsonFields.CITY_COAL_MINES.name() + "_" + city.getId(), city.getCoalMines());
			object.put(JsonFields.CITY_COAL_PRODUCTION.name() + "_" + city.getId(), city.getCoalProduction().get(TextKey.Resource.TOTAL_GAIN));
			object.put(JsonFields.CITY_COAL_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_IRON_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_OIL_WELL_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
		}
		return object.toString();
	}

	public static String closeIronMine(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getIronMines() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("iron mines"));
		}
		else
		{
			city.setIronMines(city.getIronMines() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("iron mine"));
			object.put(JsonFields.CITY_IRON_MINES.name() + "_" + city.getId(), city.getIronMines());
			object.put(JsonFields.CITY_IRON_PRODUCTION.name() + "_" + city.getId(), city.getIronProduction().get(TextKey.Resource.TOTAL_GAIN));
			object.put(JsonFields.CITY_COAL_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_IRON_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_OIL_WELL_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
		}
		return object.toString();
	}

	public static String closeDrill(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getOilWells() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("oil wells"));
		}
		else
		{
			city.setOilWells(city.getOilWells() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("oil well"));
			object.put(JsonFields.CITY_OIL_WELLS.name() + "_" + city.getId(), city.getOilWells());
			object.put(JsonFields.CITY_OIL_PRODUCTION.name() + "_" + city.getId(), city.getOilProduction().get(TextKey.Resource.TOTAL_GAIN));
			object.put(JsonFields.CITY_COAL_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_IRON_MINE_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_OIL_WELL_COST.name() + "_" + city.getId(), Util.formatNumber(city.getMineCost()));
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
		}
		return object.toString();
	}

	public static String closeIndustrialize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getIndustryCivilian() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("factories"));
		}
		else
		{
			city.setIndustryCivilian(city.getIndustryCivilian() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("factory"));
			object.put(JsonFields.CITY_CIVILIAN_INDUSTRY.name() + "_" + city.getId(), city.getIndustryCivilian());
			object.put(JsonFields.CITY_STEEL_PRODUCTION.name() + "_" + city.getId(), city.getSteelProduction().get(TextKey.Resource.TOTAL_GAIN));
			object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
			object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
			object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
			object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost().toString());
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
		}
		return object.toString();
	}

	public static String closeMilitarize(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getIndustryMilitary() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("factories"));
		}
		else
		{
			new CityDao(nation.getConn(), true).removeMilitaryIndustry(city);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("factory"));
			object.put(JsonFields.CITY_MILITARY_INDUSTRY.name() + "_" + city.getId(), city.getIndustryMilitary() - 1);
			object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(false).toString());
			object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(false).toString());
			object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(false).toString());
			object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost(false).toString());
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots() + 1);
		}
		return object.toString();
	}

	public static String closeNitrogen(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getIndustryNitrogen() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("factories"));
		}
		else
		{
			city.setIndustryNitrogen(city.getIndustryNitrogen() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("factory"));
			object.put(JsonFields.CITY_NITROGEN_INDUSTRY.name() + "_" + city.getId(), city.getIndustryNitrogen());
			object.put(JsonFields.CITY_NITROGEN_PRODUCTION.name() + "_" + city.getId(), city.getNitrogenProduction().get(TextKey.Resource.TOTAL_GAIN));
			object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(false).toString());
			object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(false).toString());
			object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost(false).toString());
			object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost(false).toString());
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
		}
		return object.toString();
	}

	public static String closeUniversity(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getUniversities() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("universities"));
		}
		else
		{
			city.setUniversities(city.getUniversities() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("university"));
			object.put(JsonFields.CITY_UNIVERSITIES.name() + "_" + city.getId(), city.getUniversities());
			object.put(JsonFields.CITY_RESEARCH_PRODUCTION.name() + "_" + city.getId(), city.getResearchProduction().get(TextKey.Resource.TOTAL_GAIN));
			object.put(JsonFields.CITY_CIVILIAN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
			object.put(JsonFields.CITY_NITROGEN_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
			object.put(JsonFields.CITY_MILITARY_INDUSTRY_COST + "_" + city.getId(), city.getFactoryCost().toString());
			object.put(JsonFields.CITY_UNIVERSITY_COST + "_" + city.getId(), city.getUniversityCost().toString());
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
		}
		return object.toString();
	}

	public static String closePort(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getPorts() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("ports"));
		}
		else
		{
			city.setPorts(city.getPorts() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("port"));
			object.put(JsonFields.CITY_PORTS.name() + "_" + city.getId(), city.getPorts());
			object.put(JsonFields.CITY_PORT_COST.name() + "_" + city.getId(), city.getPortCost());
		}
		return object.toString();
	}

	public static String closeBarrack(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getBarracks() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("barracks"));
		}
		else
		{
			city.setBarracks(city.getBarracks() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("barrack"));
			object.put(JsonFields.CITY_BARRACKS.name() + "_" + city.getId(), city.getBarracks());
			object.put(JsonFields.CITY_BARRACK_COST.name() + "_" + city.getId(), city.getBarrackCost());
		}
		return object.toString();
	}

	public static String closeRailroad(Nation nation, long cityId) throws SQLException, NationNotFoundException, CityNotFoundException, NullPointerException, NotLoggedInException
	{
		City city = nation.getCities().get(cityId);
		JSONObject object = new JSONObject();
		if(city == null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourCity());
		}
		else if(city.getRailroads() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.no("infrastructure"));
		}
		else
		{
			city.setRailroads(city.getRailroads() - 1);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.close("infrastructure"));
			object.put(JsonFields.CITY_INFRASTRUCTURE.name() + "_" + city.getId(), city.getRailroads());
			object.put(JsonFields.CITY_INFRASTRUCTURE_COST.name() + "_" + city.getId(), city.getRailCost());
			object.put(JsonFields.CITY_MAX_BUILD_SLOTS.name() + "_" + city.getId(), city.getBuildSlots());
			object.put(JsonFields.CITY_BUILD_SLOTS.name() + "_" + city.getId(), city.getFreeSlots());
		}
		return object.toString();
	}



	public static String rename(City city, String name) throws SQLException, NullPointerException
	{
		JSONObject object = new JSONObject();
		if(name.length() > 64)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.tooLong("Name", 64));
		}
		else
		{
			city.setName(name);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.updated("Name"));
			object.put(JsonFields.CITY_NAME.name() + "_" + city.getId(), city.getName());
		}
		return object.toString();
	}
}