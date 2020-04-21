package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SingleTechnology implements Technology
{
	protected @Getter String name;
	protected @Getter String desc;
	protected @Getter ArrayList<Technologies> prerequisites;
	protected @Getter HashMap<String, Integer> costs;
	protected @Getter HashMap<String, Integer> requirements;
	protected @Getter String tableName;
	protected @Getter String fieldName;
	protected @Getter int requiredSuccesses;
	protected @Getter ArrayList<String> effects;

	/**
	 * Default constructor
	 */
	public SingleTechnology()
	{
		this.name = "NULL";
		this.desc = "NULL";
		this.prerequisites = null;
		this.costs = null;
		this.requirements = null;
		this.tableName = "NULL";
		this.fieldName = "NULL";
		this.requiredSuccesses = 0;
		effects = new ArrayList<>();
	}

	/**
	 *
	 * @param name The name that would display on a view
	 * @param desc The description that would display on a view
	 * @param tableName The technologies SQL name
	 * @param requiredSuccesses How many successful research attempts are required before this technology is researched
	 */
	public SingleTechnology(String name, String desc, String tableName, String fieldName, int requiredSuccesses)
	{
		this.name = name;
		this.desc = desc;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.requiredSuccesses = requiredSuccesses;
		effects = new ArrayList<>();
	}

	/**
	 * @param nation The nation to check
	 * @return true if nation has the prerequisite technologies to research this tech, false otherwise
	 */
	public boolean isAvailable(Nation nation)
	{
		if(nation.getTech().getResearchedTechs().contains(this.getTechnology()))
		{
			return false;
		}
		for(Technologies tech : prerequisites)
		{
			if(!nation.getTech().getResearchedTechs().contains(tech))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param nation The nation to check
	 * @return An int representing the percent chance nation would succeed a research attempt for this technology
	 *         100 would indicate this technology always succeeds, 0 would never succeed
	 */
	public abstract int getSuccessChance(Nation nation);

	/**
	 *
	 * @return The Enum value of this technology
	 */
	public abstract Technologies getTechnology();
}

