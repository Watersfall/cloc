package com.watersfall.clocgame.model.policies;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public enum Policy
{
	CIVILIAN_ECONOMY("Civilian Economy", PolicyCategory.ECONOMY,-10, "policy.factories", -10, "policy.extraction", 15, "policy.pop_growth"),
	EXTRACTION_ECONOMY("Extraction Economy", PolicyCategory.ECONOMY, -15, "policy.factories", 10, "policy.extraction"),
	INDUSTRY_ECONOMY("Industrial Economy", PolicyCategory.ECONOMY, 10, "policy.factories", -15, "policy.extraction"),
	AGRARIAN_ECONOMY("Agrarian Economy", PolicyCategory.ECONOMY, -10, "policy.factories", -5, "policy.extraction", 15, "policy.farming"),
	WAR_ECONOMY("War Economy", PolicyCategory.ECONOMY, -10, "policy.factories", -10, "policy.extraction", -10, "policy.farming", -15, "policy.pop_growth", 25, "policy.military_factories", 1, "policy.training"),
	DISARMED_MANPOWER("Disarmed Population", PolicyCategory.MANPOWER, 5, "policy.manpower", 25, "policy.pop_growth"),
	VOLUNTEER_MANPOWER("Volunteer Army", PolicyCategory.MANPOWER, 10, "policy.manpower", 10, "policy.pop_growth"),
	RECRUITMENT_MANPOWER("Incentivized Recruitment", PolicyCategory.MANPOWER, 20, "policy.manpower"),
	MANDATORY_MANPOWER("Mandatory Drafting", PolicyCategory.MANPOWER, 30, "policy.manpower", -10, "policy.pop_growth"),
	SCRAPING_THE_BARREL_MANPOWER("Scraping the Barrel", PolicyCategory.MANPOWER, 45, "policy.manpower", -25, "policy.pop_growth"),
	RATIONING_FOOD("Mandatory Rationing", PolicyCategory.FOOD, -35, "policy.food_consumption", -25, "policy.pop_growth"),
	NORMAL_FOOD("Normal", PolicyCategory.FOOD),
	FREE_FOOD("Free Food", PolicyCategory.FOOD, 35, "policy.food_consumption", 15, "policy.pop_growth"),
	MAX_FORTIFICATION("Fully Occupied", PolicyCategory.FORTIFICATION, 100, "policy.fortification_upkeep", 50, "policy.fortification_growth", 100, "policy.fortification_max"),
	FULL_FUNDING_FORTIFICATION("Extra Supplies", PolicyCategory.FORTIFICATION, 25, "policy.fortification_upkeep", 25, "policy.fortification_growth", 100, "policy.fortification_max"),
	PARTIAL_FUNDING_FORTIFICATION("Standing Garrison", PolicyCategory.FORTIFICATION, 0, "policy.fortification_upkeep", 75, "policy.fortification_max"),
	MINIMAL_FUNDING_FORTIFICATION("Minimal Garrison", PolicyCategory.FORTIFICATION, -25, "policy.fortification_upkeep", -50, "policy.fortification_growth", 40, "policy.fortification_max"),
	UNOCCUPIED_FORTIFICATION("Unoccupied", PolicyCategory.FORTIFICATION, -100, "policy.fortification_upkeep", -125, "policy.fortification_growth", 15, "policy.fortification_max"),
	NO_SUBSIDIES_FARMING("No Farming Subsidies", PolicyCategory.FARM_SUBSIDIZATION, -75, "policy.farm_upkeep", -50, "policy.farm_tech_bonus"),
	REDUCED_SUBSIDIES_FARMING("Reduced Farming Subsidies", PolicyCategory.FARM_SUBSIDIZATION, -25, "policy.farm_upkeep", -15, "policy.farm_tech_bonus"),
	STANDARD_SUBSIDIES_FARMING("Standard Farming Subsidies", PolicyCategory.FARM_SUBSIDIZATION),
	SUBSTANTIAL_SUBSIDIES_FARMING("Substantial Farming Subsidies", PolicyCategory.FARM_SUBSIDIZATION, 50, "policy.farm_upkeep", 50, "policy.farm_tech_bonus");

	private @Getter String name;
	private @Getter PolicyCategory category;
	private @Getter Object[] desc;

	Policy(String name, PolicyCategory category, Object... desc)
	{
		this.name = name;
		this.category = category;
		this.desc = desc;
	}

	public LinkedHashMap<String, Integer> getMap()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		for(int i = 0; i < this.desc.length; i += 2)
		{
			map.put(getDescFromKey((String)this.desc[i + 1]), (Integer)this.desc[i]);
		}
		return map;
	}

	public static String getDescFromKey(String key)
	{
		switch(key)
		{
			case "policy.factories":
				return "% to factory production";
			case "policy.extraction":
				return "% to mine and well production";
			case "policy.farming":
				return "% to farm output";
			case "policy.pop_growth":
				return "% to pop growth";
			case "policy.military_factories":
				return "% to military factory output";
			case "policy.manpower":
				return "% recruitable population";
			case "policy.food_consumption":
				return "% food consumption";
			case "policy.training":
				return " training per turn (net 0 change)";
			case "policy.fortification_upkeep":
				return "% fortification upkeep cost";
			case "policy.defense":
				return "% to defense";
			case "policy.fortification_bonus":
				return "% defense bonus from fortifications";
			case "policy.fortification_growth":
				return "% to fortification speed";
			case "policy.fortification_max":
				return "% max fortification";
			case "policy.farm_upkeep":
				return "% farm upkeep";
			case "policy.farm_tech_bonus":
				return "% farming technology bonus";
			default:
				return "";
		}
	}

	public static LinkedHashMap<PolicyCategory, List<Policy>> getPoliciesByCategory()
	{
		LinkedHashMap<PolicyCategory, List<Policy>> map = new LinkedHashMap<>();
		for(Policy policy : Policy.values())
		{
			if(map.get(policy.getCategory()) == null)
			{
				map.put(policy.getCategory(), new ArrayList<>(Arrays.asList(policy)));
			}
			else
			{
				map.get(policy.getCategory()).add(policy);
			}
		}
		return map;
	}
}
