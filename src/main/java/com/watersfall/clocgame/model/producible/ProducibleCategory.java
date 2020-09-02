package com.watersfall.clocgame.model.producible;

import com.watersfall.clocgame.model.database.Tables;
import lombok.Getter;

public enum ProducibleCategory
{
	INFANTRY_EQUIPMENT(Tables.CLOC_ARMY),
	ARTILLERY(Tables.CLOC_ARMY),
	TANK(Tables.CLOC_ARMY),
	FIGHTER_PLANE(Tables.CLOC_MILITARY),
	BOMBER_PLANE(Tables.CLOC_MILITARY),
	RECON_PLANE(Tables.CLOC_MILITARY);

	private @Getter Tables table;
	ProducibleCategory(Tables table)
	{
		this.table = table;
	}
}
