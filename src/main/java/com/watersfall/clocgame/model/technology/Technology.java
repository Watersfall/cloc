package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.nation.Nation;

import java.util.Collection;
import java.util.LinkedHashMap;
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
	 * Returns whether this technology is something producible
	 * @return true if it can be produced, false otherwise
	 */
	boolean isProducible();

	/**
	 * Gets the production cost of this technology if it is producible, or -1.0 if it is not
	 * @return The production cost
	 */
	default double getProductionICCost() {return -1.0;}

	/**
	 * Gets the resources required for production
	 * @return The production resource costs
	 */
	default LinkedHashMap<String, Integer> getProductionResourceCost() {return null;}

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
	 * @return The SQL column name of this technologies production, or null if it isn't producible
	 */
	default String getProductionName() { return null; }

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
