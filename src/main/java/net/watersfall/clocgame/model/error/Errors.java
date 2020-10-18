package net.watersfall.clocgame.model.error;

import lombok.Getter;
import net.watersfall.clocgame.text.Responses;

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
