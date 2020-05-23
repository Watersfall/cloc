package com.watersfall.clocgame.model;

public interface Key
{
	enum Modifiers implements Key
	{
		RESOURCE_PRODUCTION,
		COAL_PRODUCTION,
		IRON_PRODUCTION,
		OIL_PRODUCTION,
		MINE_PRODUCTION,
		CIVILIAN_INDUSTRY_PRODUCTION,
		MILITARY_INDUSTRY_PRODUCTION,
		FACTORY_PRODUCTION,
		STABILITY_PER_MONTH,
		APPROVAL_PER_MONTH,
		POPULATION_GROWTH
	}
}
