package com.watersfall.clocgame.schedulers;

import com.watersfall.clocgame.turn.TurnDay;
import it.sauronsoftware.cron4j.Scheduler;
import lombok.Getter;

public class DayScheduler
{
	private static @Getter
	Scheduler scheduler;

	private DayScheduler()
	{
		scheduler = new Scheduler();
		scheduler.schedule("0,8,17,25,34,42,51 * * * *", new TurnDay());
		scheduler.start();
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
