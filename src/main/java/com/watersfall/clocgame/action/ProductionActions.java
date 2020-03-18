package com.watersfall.clocgame.action;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.Production;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.text.Responses;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductionActions
{
	public static String delete(Nation nation, int id) throws SQLException, NullPointerException
	{
		Production.deleteProductionById(nation.getProductionById(id).getId(), nation.getConn());
		return Responses.deleted();
	}

	public static String create(Nation nation) throws SQLException
	{
		if(nation.getFreeFactories() <= 0)
		{
			return Responses.noFactories();
		}
		else
		{
			Production.createDefaultProduction(nation.getId(), nation.getConn());
			return Responses.created();
		}
	}

	public static String update(Nation nation, int id, int newFactoryCount, String productionString) throws SQLException, NullPointerException, IllegalArgumentException
	{
		Technologies newProduction = Technologies.valueOf(productionString);
		Production production = nation.getProductionById(id);
		if(newFactoryCount > 15 || newFactoryCount < 0)
		{
			throw new IllegalArgumentException();
		}
		if(newProduction.getTechnology().isProducible())
		{
			if(nation.getFreeFactories() >= newFactoryCount - production.getFactories().size())
			{
				if(!newProduction.name().equalsIgnoreCase(production.getProduction()))
				{
					PreparedStatement statement;
					if(production.getProductionAsTechnology().getCategory() == newProduction.getCategory())
					{
						statement = nation.getConn().prepareStatement("UPDATE factories " +
								"SET efficiency=GREATEST(efficiency / 2, 1500) WHERE production_id=?");
					}
					else
					{
						statement = nation.getConn().prepareStatement("UPDATE factories " +
								"SET efficiency=1500 WHERE production_id=?");
					}
					statement.setInt(1, id);
					statement.execute();
					production.setProgress(0);
					production.setProduction(newProduction.name());
				}
				if(newFactoryCount != production.getFactories().size())
				{
					if(newFactoryCount > production.getFactories().size())
					{
						production.addFactories(newFactoryCount - production.getFactories().size(), nation.getConn());
					}
					else
					{
						production.removeFactories(production.getFactories().size() - newFactoryCount, nation.getConn());
					}
				}
				production.update(nation.getConn());
				return Responses.updated();
			}
			else
			{
				return Responses.noFactories();
			}
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
}
