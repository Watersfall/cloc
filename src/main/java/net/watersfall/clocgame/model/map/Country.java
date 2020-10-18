package net.watersfall.clocgame.model.map;

import net.watersfall.clocgame.model.alignment.Alignments;

import java.util.HashMap;

public interface Country
{
	HashMap<String, String> getAttributes();

	Alignments getAlignment();
}
