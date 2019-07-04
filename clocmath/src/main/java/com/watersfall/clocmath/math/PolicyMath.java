package com.watersfall.clocmath.math;

import com.watersfall.clocmath.constants.PolicyConstants;
import com.watersfall.clocmath.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PolicyMath
{
	
	public static int getFactoryCoalCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getFactoryCoalCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getFactoryIronCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getFactoryIronCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getFactoryOilCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getFactoryOilCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getFactoryMgCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getFactoryMgCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getTrainingCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getTrainingCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getMineCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getMineCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getWellCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getWellCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getSubmarineOilCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getSubmarineOilCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getSubmarineMgCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getSubmarineMgCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getDestroyerOilCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getDestroyerOilCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getDestroyerMgCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getDestroyerMgCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getCruiserOilCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getCruiserOilCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getCruiserMgCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getCruiserMgCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getBattleshipOilCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getBattleshipOilCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getBattleshipMgCost(ResultSet... results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getBattleshipMgCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getFactoryCoalCost(Map results)
	{
		int mult = Integer.parseInt(results.get("civilian_industry").toString()) + Integer.parseInt(results.get("military_industry").toString());
		return PolicyConstants.COST_FACTORY_BASE_RM + (PolicyConstants.COST_FACTORY_MULT_RM * mult);
	}

	public static int getFactoryIronCost(Map results)
	{
		int mult = Integer.parseInt(results.get("civilian_industry").toString()) + Integer.parseInt(results.get("military_industry").toString());
		return PolicyConstants.COST_FACTORY_BASE_RM + (PolicyConstants.COST_FACTORY_MULT_RM * mult);
	}

	public static int getFactoryOilCost(Map results)
	{
		int mult = Integer.parseInt(results.get("civilian_industry").toString()) + Integer.parseInt(results.get("military_industry").toString());
		return PolicyConstants.COST_FACTORY_BASE_OIL + (PolicyConstants.COST_FACTORY_MULT_OIL * mult);
	}

	public static int getFactoryMgCost(Map results)
	{
		int mult = Integer.parseInt(results.get("civilian_industry").toString()) + Integer.parseInt(results.get("military_industry").toString());
		return PolicyConstants.COST_FACTORY_BASE_MG + (PolicyConstants.COST_FACTORY_MULT_MG * mult);
	}

	public static int getTrainingCost(Map results)
	{
		return (int) (Integer.parseInt(results.get("army_home").toString()) * Math.pow(Integer.parseInt(results.get("training_home").toString()), 2) / 100);
	}

	public static int getMineCost(Map results)
	{
		int mult = PolicyConstants.COST_MINE_MULT * (Integer.parseInt(results.get("iron_mines").toString()) + Integer.parseInt(results.get("coal_mines").toString()));
		return mult + PolicyConstants.COST_MINE_BASE;
	}

	public static int getWellCost(Map results)
	{
		int mult = PolicyConstants.COST_OIL_MULT * Integer.parseInt(results.get("oil_wells").toString());
		return mult + PolicyConstants.COST_OIL_BASE;
	}

	public static int getSubmarineOilCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_SUBMARINE_OIL * Integer.parseInt(results.get("submarines").toString());
		return mult + PolicyConstants.COST_SHIP_SUBMARINE_OIL;
	}

	public static int getSubmarineMgCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_SUBMARINE_MG * Integer.parseInt(results.get("submarines").toString());
		return mult + PolicyConstants.COST_SHIP_SUBMARINE_MG;
	}

	public static int getDestroyerOilCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_DESTROYER_OIL * Integer.parseInt(results.get("destroyers").toString());
		return mult + PolicyConstants.COST_SHIP_DESTROYER_OIL;
	}

	public static int getDestroyerMgCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_DESTROYER_MG * Integer.parseInt(results.get("destroyers").toString());
		return mult + PolicyConstants.COST_SHIP_DESTROYER_MG;
	}

	public static int getCruiserOilCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_CRUISER_OIL * Integer.parseInt(results.get("cruisers").toString());
		return mult + PolicyConstants.COST_SHIP_CRUISER_OIL;
	}

	public static int getCruiserMgCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_CRUISER_MG * Integer.parseInt(results.get("cruisers").toString());
		return mult + PolicyConstants.COST_SHIP_CRUISER_MG;
	}

	public static int getBattleshipOilCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_BATTLESHIP_OIL * Integer.parseInt(results.get("battleships").toString());
		return mult + PolicyConstants.COST_SHIP_BATTLESHIP_OIL;
	}

	public static int getBattleshipMgCost(Map results)
	{
		int mult = PolicyConstants.COST_MULT_SHIP_BATTLESHIP_MG * Integer.parseInt(results.get("battleships").toString());
		return mult + PolicyConstants.COST_SHIP_BATTLESHIP_MG;
	}
}
