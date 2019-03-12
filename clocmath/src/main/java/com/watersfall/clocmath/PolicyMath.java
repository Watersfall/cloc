package com.watersfall.clocmath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class PolicyMath
{
    public static int getFactoryRmCost(ResultSet results)
    {
        try
        {
            int mult = results.getInt("milindustry") + results.getInt("industry");
            return PolicyConstants.COST_FACTORY_BASE_RM + (PolicyConstants.COST_FACTORY_MULT_RM * mult);
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
            int mult = results.getInt("milindustry") + results.getInt("industry");
            return PolicyConstants.COST_FACTORY_BASE_OIL + (PolicyConstants.COST_FACTORY_MULT_OIL * mult);
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
            int mult = results.getInt("milindustry") + results.getInt("industry");
            return PolicyConstants.COST_FACTORY_BASE_MG + (PolicyConstants.COST_FACTORY_MULT_MG * mult);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getNitrogenRmCost(ResultSet results)
    {
        try
        {
            int mult = results.getInt("nitrogenplant") + results.getInt("university");
            return PolicyConstants.COST_NITROGEN_BASE_RM + (PolicyConstants.COST_NITROGEN_MULT_RM * mult);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getNitrogenOilCost(ResultSet results)
    {
        try
        {
            int mult = results.getInt("nitrogenplant") + results.getInt("university");
            return PolicyConstants.COST_NITROGEN_BASE_OIL + (PolicyConstants.COST_NITROGEN_MULT_OIL * mult);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getNitrogenMgCost(ResultSet results)
    {
        try
        {
            int mult = results.getInt("nitrogenplant") + results.getInt("university");
            return PolicyConstants.COST_NITROGEN_BASE_MG + (PolicyConstants.COST_NITROGEN_MULT_MG * mult);
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
            return (int) (results.getInt("army") * Math.pow(results.getInt("training"), 2) / 100);
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
            int mult = PolicyConstants.COST_MINE_MULT * results.getInt("mines");
            return mult + PolicyConstants.COST_MINE_BASE;
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
            int mult = PolicyConstants.COST_OIL_MULT * results.getInt("wells");
            return mult + PolicyConstants.COST_OIL_BASE;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getFactoryRmCost(SortedMap results)
    {
        int mult = Integer.parseInt(results.get("milindustry").toString()) + Integer.parseInt(results.get("industry").toString());
        return PolicyConstants.COST_FACTORY_BASE_RM + (PolicyConstants.COST_FACTORY_MULT_RM * mult);
    }

    public static int getFactoryOilCost(SortedMap results)
    {
        int mult = Integer.parseInt(results.get("milindustry").toString()) + Integer.parseInt(results.get("industry").toString());
        return PolicyConstants.COST_FACTORY_BASE_OIL + (PolicyConstants.COST_FACTORY_MULT_OIL * mult);
    }

    public static int getFactoryMgCost(SortedMap results)
    {
        int mult = Integer.parseInt(results.get("milindustry").toString()) + Integer.parseInt(results.get("industry").toString());
        return PolicyConstants.COST_FACTORY_BASE_MG + (PolicyConstants.COST_FACTORY_MULT_MG * mult);
    }

    public static int getNitrogenRmCost(SortedMap results)
    {
        int mult = Integer.parseInt(results.get("nitrogenplant").toString()) + Integer.parseInt(results.get("universities").toString());
        return PolicyConstants.COST_NITROGEN_BASE_RM + (PolicyConstants.COST_NITROGEN_MULT_RM * mult);
    }

    public static int getNitrogenOilCost(SortedMap results)
    {
        int mult = Integer.parseInt(results.get("nitrogenplant").toString()) + Integer.parseInt(results.get("universities").toString());
        return PolicyConstants.COST_NITROGEN_BASE_OIL + (PolicyConstants.COST_NITROGEN_MULT_OIL * mult);
    }

    public static int getNitrogenMgCost(SortedMap results)
    {
        int mult = Integer.parseInt(results.get("nitrogenplant").toString()) + Integer.parseInt(results.get("universities").toString());
        return PolicyConstants.COST_NITROGEN_BASE_MG + (PolicyConstants.COST_NITROGEN_MULT_MG * mult);
    }

    public static int getTrainingCost(SortedMap results)
    {
        return (int) (Integer.parseInt(results.get("army").toString()) * Math.pow(Integer.parseInt(results.get("training").toString()), 2) / 100);
    }

    public static int getMineCost(SortedMap results)
    {
        int mult = PolicyConstants.COST_MINE_MULT * Integer.parseInt(results.get("mines").toString());
        return mult + PolicyConstants.COST_MINE_BASE;
    }

    public static int getWellCost(SortedMap results)
    {
        int mult = PolicyConstants.COST_OIL_MULT * Integer.parseInt(results.get("wells").toString());
        return mult + PolicyConstants.COST_OIL_BASE;
    }
}
