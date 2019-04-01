package com.watersfall.clocmath;

public class PopulationConstants
{
	public static final double POP_FOOD_COST = 1 / 1000d;
	public static final double FOOD_PER_LAND = 1 / 200d;
	public static final int MINE_POPULATION = 50000;
	public static final int WELL_POPULATION = 50000;
	public static final int FACTORY_POPULATION = 750000;
	public static final int NITROGEN_POPULATION = 1500000;
	public static final int FARM_POPULATION = 10000;
	public static final double BASE_GROWTH = 0.02d;
	public static final double BASE_MANPOWER = 0.10d;

	public double getPOP_FOOD_COST()
	{
		return POP_FOOD_COST;
	}

	public double getFOOD_PER_LAND()
	{
		return FOOD_PER_LAND;
	}

	public int getMINE_POPULATION()
	{
		return MINE_POPULATION;
	}

	public int getWELL_POPULATION()
	{
		return WELL_POPULATION;
	}

	public int getFACTORY_POPULATION()
	{
		return FACTORY_POPULATION;
	}

	public int getFARM_POPULATION()
	{
		return FARM_POPULATION;
	}

	public double getBASE_GROWTH()
	{
		return BASE_GROWTH;
	}

	public double getBASE_MANPOWER()
	{
		return BASE_MANPOWER;
	}

	public double getNITROGEN_POPULATION()
	{
		return NITROGEN_POPULATION;
	}
}