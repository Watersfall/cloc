package com.watersfall.clocgame.model.error;

import com.watersfall.clocgame.text.Responses;
import lombok.Getter;

public enum Errors
{
	NOT_LOGGED_IN(Responses.noLogin());

	private @Getter String message;
	Errors(String message)
	{
		this.message = message;
	}
}
