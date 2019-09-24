package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.nation.Nation;

import java.util.Collection;
import java.util.Map;

public interface Technology
{
	boolean isAvailable(Nation nation);

	int getSuccessChance(Nation nation);

	Technologies getTechnology();

	int getRequiredSuccesses();

	String getName();

	String getDesc();

	String getTableName();

	String getFieldName();

	Collection<Technologies> getPrerequisites();

	Map<String, Integer> getCosts();

	Map<String, Integer> getRequirements();
}
