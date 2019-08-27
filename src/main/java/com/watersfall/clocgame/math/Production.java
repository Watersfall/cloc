package com.watersfall.clocgame.math;

import com.watersfall.clocgame.constants.ProductionConstants;
import com.watersfall.clocgame.model.nation.City;
import com.watersfall.clocgame.model.nation.NationCities;

import java.util.HashMap;

public class Production
{
	public static HashMap<String, Double> getCoalProduction(City city)
	{
		HashMap<String, Double> map = new HashMap<>();
		double mines = city.getCoalMines() * ProductionConstants.MINE_PER_WEEK;
		double bonus = city.getRailroads() * ProductionConstants.RAILROAD_BOOST * city.getCoalMines();
		double total = mines + bonus;
		double costs = (city.getIndustryCivilian() + city.getIndustryNitrogen() + city.getIndustryMilitary()) * ProductionConstants.FACTORY_COAL_PER_WEEK;
		double net = total - costs;
		map.put("mines", mines);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getIronProduction(City city)
	{
		HashMap<String, Double> map = new HashMap<>();
		double mines = city.getIronMines() * ProductionConstants.MINE_PER_WEEK;
		double bonus = city.getRailroads() * ProductionConstants.RAILROAD_BOOST * city.getIronMines();
		double total = mines + bonus;
		double costs = (city.getIndustryCivilian() + city.getIndustryNitrogen() + city.getIndustryMilitary()) * ProductionConstants.FACTORY_IRON_PER_WEEK;
		double net = total - costs;
		map.put("mines", mines);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getOilProduction(City city)
	{
		HashMap<String, Double> map = new HashMap<>();
		double wells = city.getIronMines() * ProductionConstants.WELL_PER_WEEK;
		double bonus = city.getRailroads() * ProductionConstants.RAILROAD_BOOST * city.getOilWells();
		double total = wells + bonus;
		double costs = (city.getIndustryCivilian() + city.getIndustryNitrogen() + city.getIndustryMilitary()) * ProductionConstants.FACTORY_OIL_PER_WEEK;
		double net = total - costs;
		map.put("wells", wells);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getSteelProduction(City city)
	{
		HashMap<String, Double> map = new HashMap<>();
		double factories = city.getIndustryCivilian() * ProductionConstants.STEEL_PER_WEEK;
		double bonus = city.getRailroads() * ProductionConstants.RAILROAD_BOOST * city.getIndustryCivilian();
		double total = factories + bonus;
		double costs = 0;
		double net = total - costs;
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getNitrogenProduction(City city)
	{
		HashMap<String, Double> map = new HashMap<>();
		double factories = city.getIndustryNitrogen() * ProductionConstants.NITROGEN_PER_WEEK;
		double bonus = city.getRailroads() * ProductionConstants.RAILROAD_BOOST * city.getIndustryNitrogen();
		double total = factories + bonus;
		double costs = 0e0;
		double net = total - costs;
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getWeaponsProduction(City city)
	{
		HashMap<String, Double> map = new HashMap<>();
		double factories = city.getIndustryNitrogen() * ProductionConstants.GUNS_PER_WEEK;
		double bonus = city.getRailroads() * ProductionConstants.RAILROAD_BOOST * city.getIndustryNitrogen();
		double total = factories + bonus;
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		return map;
	}

	public static HashMap<String, Double> getResearchProduction(City city)
	{
		HashMap<String, Double> map = new HashMap<>();
		double universities = city.getUniversities() * ProductionConstants.RESEARCH_PER_WEEK;
		double bonus = city.getRailroads() * ProductionConstants.RAILROAD_BOOST * city.getUniversities();
		double total = universities + bonus;
		map.put("universities", universities);
		map.put("bonus", bonus);
		map.put("total", total);
		return map;
	}

	public static HashMap<String, Double> getTotalCoalProduction(NationCities cities)
	{
		double mines = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : cities.getCities())
		{
			map = getCoalProduction(city);
			mines += map.get("mines");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("mines", mines);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getTotalIronProduction(NationCities cities)
	{
		double mines = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : cities.getCities())
		{
			map = getIronProduction(city);
			mines += map.get("mines");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("mines", mines);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getTotalOilProduction(NationCities cities)
	{
		double wells = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : cities.getCities())
		{
			map = getOilProduction(city);
			wells += map.get("wells");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("wells", wells);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getTotalSteelProduction(NationCities cities)
	{
		double factories = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : cities.getCities())
		{
			map = getSteelProduction(city);
			factories += map.get("factories");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getTotalNitrogenProduction(NationCities cities)
	{
		double factories = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : cities.getCities())
		{
			map = getNitrogenProduction(city);
			factories += map.get("factories");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public static HashMap<String, Double> getTotalWeaponsProduction(NationCities cities)
	{
		double factories = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		HashMap<String, Double> map;
		for(City city : cities.getCities())
		{
			map = getResearchProduction(city);
			factories += map.get("universities");
			bonus += map.get("bonus");
			total += map.get("total");
		}
		map =  new HashMap<>();
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		return map;
	}

	public static HashMap<String, Double> getTotalResearchProduction(NationCities cities)
	{
		double universities = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		HashMap<String, Double> map;
		for(City city : cities.getCities())
		{
			map = getResearchProduction(city);
			universities += map.get("universities");
			bonus += map.get("bonus");
			total += map.get("total");
		}
		map =  new HashMap<>();
		map.put("universities", universities);
		map.put("bonus", bonus);
		map.put("total", total);
		return map;
	}
}
