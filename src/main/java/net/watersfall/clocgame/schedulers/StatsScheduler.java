package net.watersfall.clocgame.schedulers;

import it.sauronsoftware.cron4j.Scheduler;
import lombok.Getter;
import net.watersfall.clocgame.turn.TurnStats;

public class StatsScheduler
{
	private static @Getter Scheduler scheduler;

	private StatsScheduler()
	{
		scheduler = new Scheduler();
		scheduler.schedule("*/10 * * * *", new TurnStats());
		scheduler.start();
	}

	public static void startStats()
	{
		if(scheduler == null)
		{
			new StatsScheduler();
		}
	}

	public static void stopStats()
	{
		if(scheduler != null)
		{
			scheduler.stop();
			scheduler = null;
		}
	}
}
