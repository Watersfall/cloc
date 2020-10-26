package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.AlignmentDao;
import net.watersfall.clocgame.model.alignment.Alignment;
import net.watersfall.clocgame.model.alignment.Alignments;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producibles;
import net.watersfall.clocgame.text.Responses;
import org.json.JSONObject;

import java.sql.SQLException;

public class AlignmentActions
{
	public static String buy(Alignment alignment, Producibles purchase, Nation nation, AlignmentDao dao) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(!Alignment.ALLOWED_PRODUCIBLES.contains(purchase))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(alignment.getAlignment() == Alignments.NEUTRAL)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(alignment.getAlignment() != nation.getStats().getAlignment())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(!alignment.isAvailableToBuy(purchase, nation))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else
		{
			long cost = alignment.getProducibleBuyCost(purchase, nation);
			if(nation.getStats().getReputation(alignment.getAlignment()) < cost)
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.genericError());
			}
			else if(alignment.getProducible(purchase) < alignment.getTransactionAmount(purchase))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.genericError());
			}
			else
			{
				dao.createTransaction("buy", purchase, alignment, nation);
				dao.updateProducible(purchase, alignment, -alignment.getTransactionAmount(purchase));
				nation.getStats().setReputation(alignment.getAlignment(), nation.getStats().getReputation(alignment.getAlignment()) - (int)cost);
				nation.getProducibles().setProducible(purchase, nation.getProducibles().getProducible(purchase) + alignment.getTransactionAmount(purchase));
				object.put(JsonFields.SUCCESS.name(), true);
				object.put(JsonFields.MESSAGE.name(), Responses.sent());
			}
		}
		return object.toString();
	}

	public static String sell(Alignment alignment, Producibles purchase, Nation nation, AlignmentDao dao) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(!Alignment.ALLOWED_PRODUCIBLES.contains(purchase))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(Alignments.opposites(alignment.getAlignment(), nation.getStats().getAlignment()))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else if(nation.getProducibles().getProducible(purchase) < alignment.getTransactionAmount(purchase))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notEnough());
		}
		else if(!alignment.isAvailableToSell(purchase, nation))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.genericError());
		}
		else
		{
			long cost = alignment.getProducibleSellCost(purchase, nation);
			dao.createTransaction("sell", purchase, alignment, nation);
			dao.updateProducible(purchase, alignment, alignment.getTransactionAmount(purchase));
			nation.getStats().setReputation(alignment.getAlignment(), nation.getStats().getReputation(alignment.getAlignment()) + (int)cost);
			nation.getProducibles().setProducible(purchase, nation.getProducibles().getProducible(purchase) - alignment.getTransactionAmount(purchase));
			nation.fixCurrentPlanes();
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.sent());
		}
		return object.toString();
	}
}