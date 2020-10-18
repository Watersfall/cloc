package net.watersfall.clocgame.exception;

public class CityNotFoundException extends RuntimeException
{
	public CityNotFoundException()
	{
		super();
	}

	public CityNotFoundException(String message)
	{
		super(message);
	}
}
