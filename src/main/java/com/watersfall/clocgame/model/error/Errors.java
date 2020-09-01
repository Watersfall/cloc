package com.watersfall.clocgame.model.error;

import com.watersfall.clocgame.text.Responses;
import lombok.Getter;

public enum Errors
{
	NOT_LOGGED_IN(Responses.noLogin()),
	TREATY_DOES_NOT_EXIST(Responses.noTreaty()),
	NATION_DOES_NOT_EXIST(Responses.noNation()),
	CITY_DOES_NOT_EXIST(Responses.noCity()),
	REGION_DOES_NOT_EXIST(Responses.noRegion());

	private @Getter String message;
	Errors(String message)
	{
		this.message = message;
	}
}
