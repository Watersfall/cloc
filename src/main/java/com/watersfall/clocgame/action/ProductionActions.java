package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.ProductionDao;
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
		if(nation.getProduction().containsKey(id))
		{
			ProductionDao dao = new ProductionDao(nation.getConn(), true);
			dao.deleteProductionById(id);
			return Responses.deleted();
		}
		else
		{
			return Responses.genericError();
		}
	}

	public static String create(Nation nation) throws SQLException
	{
		if(nation.getFreeFactories() <= 0)
		{
			return Responses.noFactories();
		}
		else
		{
			ProductionDao dao = new ProductionDao(nation.getConn(), true);
			dao.createDefaultProduction(nation.getId());
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
					ProductionDao dao = new ProductionDao(nation.getConn(), true);
					if(newFactoryCount > production.getFactories().size())
					{
						dao.addFactories(production.getId(), newFactoryCount - production.getFactories().size());
					}
					else
					{
						dao.removeFactories(production.getId(), production.getFactories().size() - newFactoryCount);
					}
				}
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
