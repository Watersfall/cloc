package com.watersfall.clocgame.schedulers;

import com.watersfall.clocgame.turn.TurnDay;
import com.watersfall.clocgame.util.Util;
import it.sauronsoftware.cron4j.Scheduler;
import lombok.Getter;

public class DayScheduler
{
	private static @Getter
	Scheduler scheduler;

	private DayScheduler()
	{
		scheduler = new Scheduler();
		switch(Util.currentMonth)
		{
			case 0:
			case 2:
			case 4:
			case 6:
			case 7:
			case 9:
			case 11:
				scheduler.schedule("0,23,46 0,12 * * *", new TurnDay());
				scheduler.schedule("10,34,58 1,13 * * *", new TurnDay());
				scheduler.schedule("21,44 2,14 * * *", new TurnDay());
				scheduler.schedule("8,32,56 3,15 * * *", new TurnDay());
				scheduler.schedule("19,42 4,16 * * *", new TurnDay());
				scheduler.schedule("06,30,54 5,17 * * *", new TurnDay());
				scheduler.schedule("17,40 6,18 * * *", new TurnDay());
				scheduler.schedule("04,28,52 7,19 * * *", new TurnDay());
				scheduler.schedule("15,38 8,20 * * *", new TurnDay());
				scheduler.schedule("02,26,50 9,21 * * *", new TurnDay());
				scheduler.schedule("13,36,59 10,22 * * *", new TurnDay());
				scheduler.schedule("23,47 11,23 * * *", new TurnDay());
				break;
			case 3:
			case 5:
			case 8:
			case 10:
				scheduler.schedule("0,24,48 0,2,4,6,8,10,12,14,16,18,20,22 * * *", new TurnDay());
				scheduler.schedule("12,36 1,3,5,7,9,11,13,15,17,19,21,23 * * *", new TurnDay());
				break;
			case 1:
				scheduler.schedule("0,25,50 0,5,10,15,20 * * *", new TurnDay());
				scheduler.schedule("15,40 1,6,11,16,21 * * *", new TurnDay());
				scheduler.schedule("5,30,55 2,7,12,17,22 * * *", new TurnDay());
				scheduler.schedule("20,45 3,8,13,18,23 * * *", new TurnDay());
				scheduler.schedule("10,35 4,9,14,19 * * *", new TurnDay());
				scheduler.schedule("* * * * *", new TurnDay());
				break;
		}
		scheduler.start();
	}

	public static void resetIncrement()
	{
		stopDay();
		startDay();
	}

	public static void startDay()
	{
		if(scheduler == null)
		{
			new DayScheduler();
		}
	}

	public static void stopDay()
	{
		if(scheduler != null)
		{
			scheduler.stop();
			scheduler = null;
		}
	}
}
