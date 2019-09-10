package com.watersfall.clocgame.model;

import lombok.Getter;

public enum LogType
{
	LAND("land"), NAVY("navy"), AIR("air"), CHEMICAL("chemical"), TRANSPORT("transport");

	private @Getter String name;

	LogType(String name)
	{
		this.name = name;
	}
}
