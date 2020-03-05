package com.watersfall.clocgame.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public enum Policy
{
	CIVILIAN_ECONOMY("Civilian Economy", -10, "policy.factories", -10, "policy.extraction", 15, "policy.pop_growth"),
	EXTRACTION_ECONOMY("Extraction Economy", -15, "policy.factories", 10, "policy.extraction"),
	INDUSTRY_ECONOMY("Industrial Economy", 10, "policy.factories", -15, "policy.extraction"),
	AGRARIAN_ECONOMY("Agrarian Economy", -10, "policy.factories", -5, "policy.extraction", 15, "policy.farming"),
	WAR_ECONOMY("War Economy", -10, "policy.factories", -10, "policy.extraction", -10, "policy.farming", -15, "policy.pop_growth", 25, "policy.military_factories", 1, "policy.training"),
	DISARMED_MANPOWER("Disarmed Population", 5, "policy.manpower", 25, "policy.pop_growth"),
	VOLUNTEER_MANPOWER("Volunteer Army", 10, "policy.manpower", 10, "policy.pop_growth"),
	RECRUITMENT_MANPOWER("Incentivized Recruitment", 20, "policy.manpower"),
	MANDATORY_MANPOWER("Mandatory Drafting", 30, "policy.manpower", -10, "policy.pop_growth"),
	SCRAPING_THE_BARREL_MANPOWER("Scraping the Barrel", 45, "policy.manpower", -25, "policy.pop_growth"),
	RATIONING_FOOD("Mandatory Rationing", -35, "policy.food_consumption", -25, "policy.pop_growth"),
	NORMAL_FOOD("Normal"),
	FREE_FOOD("Free Food", 35, "policy.food_consumption", 15, "policy.pop_growth");

	private @Getter String name;
	private @Getter Object[] desc;

	Policy(String name, Object... desc)
	{
		this.name = name;
		this.desc = desc;
	}

	public LinkedHashMap<String, Integer> getMap(Policy policy)
	{

		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		for(int i = 0; i < policy.desc.length; i += 2)
		{
			map.put(getDescFromKey((String)policy.desc[i + 1]), (Integer)policy.desc[i]);
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
			default:
				return "";
		}
	}

	public static LinkedHashMap<String, List<Policy>> getPoliciesByCategory()
	{
		LinkedHashMap<String, List<Policy>> map = new LinkedHashMap<>();
		map.put("Economy", Arrays.asList(CIVILIAN_ECONOMY, EXTRACTION_ECONOMY, INDUSTRY_ECONOMY, AGRARIAN_ECONOMY, WAR_ECONOMY));
		map.put("Manpower", Arrays.asList(DISARMED_MANPOWER, VOLUNTEER_MANPOWER, RECRUITMENT_MANPOWER, MANDATORY_MANPOWER, SCRAPING_THE_BARREL_MANPOWER));
		map.put("Food", Arrays.asList(RATIONING_FOOD, NORMAL_FOOD, FREE_FOOD));
		return map;
	}
}
