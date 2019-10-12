package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.nation.Nation;

import java.util.Collection;
import java.util.Map;

public interface Technology
{
	/**
	 * Method to check if a nation has this research available to it
	 * @param nation the nation to check
	 * @return true if the technology is available, false if it isn't
	 */
	boolean isAvailable(Nation nation);

	/**
	 * @param nation The nation to get the success chance for
	 * @return The calculated success chance of researching this technology
	 */
	int getSuccessChance(Nation nation);

	/**
	 * @return The enum constant for this tech
	 */
	Technologies getTechnology();

	/**
	 * @return How many successes are required before this technology is fully researched
	 */
	int getRequiredSuccesses();

	/**
	 * @return The display name of this technology
	 */
	String getName();

	/**
	 * @return The display description of this technology
	 */
	String getDesc();

	/**
	 * @return The SQL column name for this technology
	 */
	String getTableName();

	/**
	 * @return A collection containing all required technologies for researching this technology
	 */
	Collection<Technologies> getPrerequisites();

	/**
	 * @return A Map containing costs for researching this technology
	 * the Key represents the field name, and the Value the amount it costs
	 */
	Map<String, Integer> getCosts();

	/**
	 * @return A Map containing all non-consumed requirements for researching this technology
	 * The Key represents the field name, with the Value representing the required amount
	 */
	Map<String, Integer> getRequirements();
}
