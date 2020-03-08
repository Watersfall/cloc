package com.watersfall.clocgame.schedulers;

import com.watersfall.clocgame.turn.TurnWeek;
import it.sauronsoftware.cron4j.Scheduler;
import lombok.Getter;

public class WeekScheduler
{
	private static @Getter
	Scheduler scheduler;

	private WeekScheduler()
	{
		scheduler = new Scheduler();
		scheduler.schedule("0 * * * *", new TurnWeek());
		scheduler.start();
	}

	public static void startWeek()
	{
		if(scheduler == null)
		{
			new WeekScheduler();
		}
	}

	public static void stopWeek()
	{
		if(scheduler != null)
		{
			scheduler.stop();
			scheduler = null;
		}
	}
}
