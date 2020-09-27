package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.AlignmentDao;
import com.watersfall.clocgame.model.alignment.Alignment;
import com.watersfall.clocgame.model.alignment.Alignments;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.producible.Producibles;
import com.watersfall.clocgame.text.Responses;

import java.sql.SQLException;

public class AlignmentActions
{
	public static String buy(Alignment alignment, Producibles purchase, Nation nation, AlignmentDao dao) throws SQLException
	{
		if(!Alignment.ALLOWED_PRODUCIBLES.contains(purchase))
		{
			return Responses.genericError();
		}
		else if(alignment.getAlignment() == Alignments.NEUTRAL)
		{
			return Responses.genericError();
		}
		else if(alignment.getAlignment() != nation.getStats().getAlignment())
		{
			return Responses.genericError();
		}
		else if(!alignment.isAvailableToBuy(purchase, nation))
		{
			return Responses.genericError();
		}
		long cost = alignment.getProducibleBuyCost(purchase, nation);
		if(nation.getStats().getReputation(alignment.getAlignment()) < cost)
		{
			return Responses.genericError();
		}
		else if(alignment.getProducible(purchase) < alignment.getTransactionAmount(purchase))
		{
			return Responses.genericError();
		}
		else
		{
			dao.createTransaction("buy", purchase, alignment, nation);
			dao.updateProducible(purchase, alignment, -alignment.getTransactionAmount(purchase));
			nation.getStats().setReputation(alignment.getAlignment(), nation.getStats().getReputation(alignment.getAlignment()) - (int)cost);
			nation.getProducibles().setProducible(purchase, nation.getProducibles().getProducible(purchase) + alignment.getTransactionAmount(purchase));
			return Responses.sent();
		}
	}

	public static String sell(Alignment alignment, Producibles purchase, Nation nation, AlignmentDao dao) throws SQLException
	{
		if(!Alignment.ALLOWED_PRODUCIBLES.contains(purchase))
		{
			return Responses.genericError();
		}
		else if(Alignments.opposites(alignment.getAlignment(), nation.getStats().getAlignment()))
		{
			return Responses.genericError();
		}
		else if(nation.getProducibles().getProducible(purchase) < alignment.getTransactionAmount(purchase))
		{
			return Responses.notEnough();
		}
		else if(!alignment.isAvailableToSell(purchase, nation))
		{
			return Responses.genericError();
		}
		else
		{
			long cost = alignment.getProducibleSellCost(purchase, nation);
			dao.createTransaction("sell", purchase, alignment, nation);
			dao.updateProducible(purchase, alignment, alignment.getTransactionAmount(purchase));
			nation.getStats().setReputation(alignment.getAlignment(), nation.getStats().getReputation(alignment.getAlignment()) + (int)cost);
			nation.getProducibles().setProducible(purchase, nation.getProducibles().getProducible(purchase) - alignment.getTransactionAmount(purchase));
			nation.fixCurrentPlanes();
			return Responses.sent();
		}
	}
}