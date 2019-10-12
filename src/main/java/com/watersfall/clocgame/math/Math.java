package com.watersfall.clocgame.math;

/**
 * Sometimes it's me who's wrong...
 *
 * ...Doesn't mean I'm gonna stop though
 */
public class Math
{
	/**
	 * A log function that does the wrong thing with negatives
	 * @param x number to get the wrong log of
	 * @return The log of x for positive numbers, or
	 *         the negative log of the absolute value of x for negative numbers
	 */
	public static double log(double x)
	{
		if(x <= 1 && x >= -1)
			return 0;
		else if(x < 0)
			return -(java.lang.Math.log(java.lang.Math.abs(x)));
		else
			return java.lang.Math.log(x);
	}

	/**
	 * A log base 10 function that does the wrong thing with negatives
	 * @param x number to get the wrong log base 10 of
	 * @return The log base 10 of x for positive numbers, or
	 *         the negative log base 10 of the absolute value of x for negative numbers
	 */
	public static double log10(double x)
	{
		if(x <= 1 && x >= -1)
			return 0;
		else if(x < 0)
			return -(java.lang.Math.log10(java.lang.Math.abs(x)));
		else
			return java.lang.Math.log10(x);
	}

	/**
	 * A square root function that does the wrong thing with negatives
	 * @param x number to get the wrong log of
	 * @return The square root of x for positive numbers, or
	 *         the negative square root of the absolute value of x for negative numbers
	 */
	public static double sqrt(double x)
	{
		if(x < 0)
			return -java.lang.Math.sqrt(abs(x));
		else
			return java.lang.Math.sqrt(x);
	}

	/**
	 * Same as normal absolute value but put in this class to make things easier
	 * @param x number to take the absolute value of
	 * @return The standard absolute value
	 */
	public static double abs(double x)
	{
		if(x >= 0)
			return x;
		else
			return -x;
	}

	/**
	 * A custom pow method to do the wrong thing with negative numbers
	 * @param e The number to raise
	 * @param x The exponent
	 * @return Standard e^x for positive numbers, or
	 *         -1 * abs(e)^x for negative numbers
	 */
	public static double pow(double e, double x)
	{
		if(e < 0 && x % 2 == 0)
			return -java.lang.Math.pow(e, x);
		else
			return java.lang.Math.pow(e, x);
	}

}