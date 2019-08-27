package com.watersfall.clocgame.math;

public class Math
{
	public static double log(double x)
	{
		if(x <= 1 && x >= -1)
			return 0;
		else if(x < 0)
			return -(java.lang.Math.log(java.lang.Math.abs(x)));
		else
			return java.lang.Math.log(x);
	}

	public static double log10(double x)
	{
		if(x <= 1 && x >= -1)
			return 0;
		else if(x < 0)
			return -(java.lang.Math.log10(java.lang.Math.abs(x)));
		else
			return java.lang.Math.log10(x);
	}

	public static double sqrt(double x)
	{
		if(x == 0)
			return 0;
		else if(x < 0)
			return -(java.lang.Math.sqrt(java.lang.Math.abs(x)));
		else
			return java.lang.Math.sqrt(x);
	}

	public static double abs(double x)
	{
		if(x >= 0)
			return x;
		else
			return -x;
	}

	public static double pow(double e, double x)
	{
		if(e < 0 && x % 2 == 0)
			return -java.lang.Math.pow(e, x);
		else
			return java.lang.Math.pow(e, x);
	}

}