package net.watersfall.clocgame.model.producible;

import net.watersfall.clocgame.model.technology.Technology;

import java.util.LinkedHashMap;

public interface Producible
{
	/**
	 * Gets the category this Producible belongs to
	 * @return the category
	 */
	ProducibleCategory getCategory();

	/**
	 * Gets the technology that unlocks this Producible
	 * @return the technology
	 */
	Technology getTechnology();

	/**
	 * Gets the Producibles enum value for this Producible
	 * @return the enum value
	 */
	Producibles getEnumValue();

	/**
	 * Gets the production cost of this technology if it is producible, or -1.0 if it is not
	 * @return The production cost
	 */
	double getProductionICCost();

	/**
	 * Gets the resources required for production
	 * @return The production resource costs
	 */
	LinkedHashMap<String, Integer> getProductionResourceCost();
}