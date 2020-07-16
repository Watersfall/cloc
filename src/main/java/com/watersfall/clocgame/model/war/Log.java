package com.watersfall.clocgame.model.war;

import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.military.LogType;
import lombok.Getter;

public class Log
{
	private @Getter int id;
	private @Getter int attacker;
	private @Getter Region region;
	private @Getter LogType type;
	private @Getter int amount;

	private Log(int id, int attacker, Region region, LogType type, int amount)
	{
		this.id = id;
		this.attacker = attacker;
		this.region = region;
		this.type = type;
		this.amount = amount;
	}
}
