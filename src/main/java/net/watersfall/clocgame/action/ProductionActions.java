package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.ProductionDao;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.nation.Production;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.text.Responses;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductionActions
{
	public static String delete(Nation nation, int id) throws SQLException, NullPointerException
	{
		JSONObject object = new JSONObject();
		if(nation.getProduction().containsKey(id))
		{
			ProductionDao dao = new ProductionDao(nation.getConn(), true);
			dao.deleteProductionById(id);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.deleted());
		}
		else
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		return object.toString();
	}

	public static String create(Nation nation) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(nation.getFreeFactories() <= 0)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noFactories());
		}
		else
		{
			ProductionDao dao = new ProductionDao(nation.getConn(), true);
			return String.valueOf(dao.createDefaultProduction(nation.getId()));
		}
		return object.toString();
	}

	public static String update(Nation nation, int id, int newFactoryCount, Producibles producible) throws SQLException, NullPointerException, IllegalArgumentException
	{
		JSONObject object = new JSONObject();
		Production production = nation.getProductionById(id);
		if(newFactoryCount > 15 || newFactoryCount < 0)
		{
			throw new IllegalArgumentException();
		}
		if(nation.getFreeFactories() >= newFactoryCount - production.getFactories().size())
		{
			if(producible != production.getProduction())
			{
				PreparedStatement statement;
				if(production.getProduction().getProducible().getCategory() == producible.getProducible().getCategory())
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
				production.setProduction(producible);
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
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.updated());
		}
		else
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noFactories());
		}
		return object.toString();
	}
}
