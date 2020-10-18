package net.watersfall.clocgame.model.alignment;

import lombok.Data;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producibles;

public @Data class AlignmentTransaction
{
	private final Alignments alignment;
	private final Nation nation;
	private final Producibles equipment;
	private final long amount;
	private final long month;
}
