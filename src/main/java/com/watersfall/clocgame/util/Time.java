package com.watersfall.clocgame.util;

public class Time
{
	private static int[] daysPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	public static String getCurrentDate()
	{
		long day = (Util.week * 7 + (Util.day % 7));
		long year = day / 365;
		day = day % 365;
		String month;
		long temp = day;
		int i = 0;
		while(temp > daysPerMonth[i])
		{
			temp -= daysPerMonth[i];
			i++;
		}
		return months[i] + " " + temp + ", " + (1900 + year);
	}

	public String getDate()
	{
		return getCurrentDate();
	}
}
