package com.watersfall.clocgame.model.alignment;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.producible.Producibles;
import lombok.Data;

public @Data class AlignmentTransaction
{
	private final Alignments alignment;
	private final Nation nation;
	private final Producibles equipment;
	private final long amount;
	private final long month;
}
