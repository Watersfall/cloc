package com.watersfall.clocgame.exception;

public class NationNotFoundException extends RuntimeException
{
	public NationNotFoundException()
	{
		super();
	}

	public NationNotFoundException(String message)
	{
		super(message);
	}
}
