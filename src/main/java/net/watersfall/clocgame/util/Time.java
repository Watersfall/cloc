package net.watersfall.clocgame.util;

public class Time
{
	public static final int[] daysPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	public static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	public static long month = 0;
	public static long day = 0;
	public static int currentMonth = 0;

	public static String getCurrentDate()
	{
		long year = day / 365;
		long temp = day % 365;
		int i = 0;
		while(temp > daysPerMonth[i])
		{
			temp -= daysPerMonth[i];
			i++;
		}
		return months[i] + " " + temp + ", " + (1900 + year);
	}

	public static int getCurrentYear()
	{
		return (int)(day / 365);
	}

	public String getDate()
	{
		return getCurrentDate();
	}
}
