package net.watersfall.clocgame.model.technology;

import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;

import java.util.ArrayList;
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
	 * Returns whether this technology is something producible
	 * @return true if it can be produced, false otherwise
	 */
	boolean isProducible();

	/**
	 * Returns the Producible class associated with this technology
	 * @return The Producible class, or null if this technology is not producible
	 */
	default Producible getProducibleItem()
	{
		return null;
	}

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

	/**
	 * @return the displayable effects of researching this technology
	 */
	ArrayList<String> getEffects();
}
