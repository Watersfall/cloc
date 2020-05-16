package com.watersfall.clocgame.model;

import lombok.Getter;

public enum SpamAction
{
	SEND_RESOURCE(3, 5 * 1000),
	SEND_DECLARATION(2, 30 * 1000),
	SEND_INVITE(3, 30 * 1000),
	UPDATE_FLAG(1, 12 * 60 * 60 * 1000),
	UPDATE_PORTRAIT(1, 12 * 60 * 60 * 1000),
	UPDATE_ALLIANCE_FLAG(1, 6 * 60 * 60 * 1000);


	private @Getter int amount;
	private @Getter long time;
	SpamAction(int amount, long time)
	{
		this.amount = amount;
		this.time = time;
	}
}