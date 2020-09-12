package com.watersfall.clocgame.model.alignment;

public enum Alignments
{
	NEUTRAL,
	ENTENTE,
	CENTRAL_POWERS;

	public static boolean opposites(Alignments alignment1, Alignments alignment2)
	{
		if(alignment1 == ENTENTE)
			return alignment2 == CENTRAL_POWERS;
		else if(alignment1 == CENTRAL_POWERS)
			return alignment2 == ENTENTE;
		else
			return false;
	}
}
