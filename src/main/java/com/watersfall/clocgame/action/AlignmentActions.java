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
		else if(alignment.getAlignment() != nation.getForeign().getAlignment())
		{
			return Responses.genericError();
		}
		long cost = alignment.getProducibleBuyCost(purchase, nation);
		if(nation.getForeign().getReputation(alignment.getAlignment()) < cost)
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
			nation.getForeign().setReputation(alignment.getAlignment(), nation.getForeign().getReputation(alignment.getAlignment()) - (int)cost);
			nation.setProducibleValue(purchase, nation.getProducibleValue(purchase) + alignment.getTransactionAmount(purchase));
			return Responses.sent();
		}
	}

	public static String sell(Alignment alignment, Producibles purchase, Nation nation, AlignmentDao dao) throws SQLException
	{
		if(!Alignment.ALLOWED_PRODUCIBLES.contains(purchase))
		{
			return Responses.genericError();
		}
		else if(Alignments.opposites(alignment.getAlignment(), nation.getForeign().getAlignment()))
		{
			return Responses.genericError();
		}
		else if(nation.getProducibleValue(purchase) < alignment.getTransactionAmount(purchase))
		{
			return Responses.notEnough();
		}
		else
		{
			long cost = alignment.getProducibleSellCost(purchase, nation);
			dao.createTransaction("sell", purchase, alignment, nation);
			dao.updateProducible(purchase, alignment, alignment.getTransactionAmount(purchase));
			nation.getForeign().setReputation(alignment.getAlignment(), nation.getForeign().getReputation(alignment.getAlignment()) + (int)cost);
			nation.setProducibleValue(purchase, nation.getProducibleValue(purchase) - alignment.getTransactionAmount(purchase));
			return Responses.sent();
		}
	}
}