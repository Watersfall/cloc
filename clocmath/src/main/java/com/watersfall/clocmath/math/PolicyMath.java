package com.watersfall.clocmath.math;

import com.watersfall.clocmath.constants.PolicyConstants;
import com.watersfall.clocmath.util.Util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;

public class PolicyMath
{
	
	public static int getFactoryRmCost(ResultSet results)
	{
		try
		{
			Map<String, Object> map = Util.createMap(results);
			return getFactoryRmCost(map);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int getFactoryOilCost(ResultSet results)
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

	public static int getFactoryMgCost(ResultSet results)
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

	public static int getTrainingCost(ResultSet results)
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

	public static int getMineCost(ResultSet results)
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

	public static int getWellCost(ResultSet results)
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

	public static int getFactoryRmCost(Map results)
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
}
