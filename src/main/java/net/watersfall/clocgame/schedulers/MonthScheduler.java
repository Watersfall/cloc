package net.watersfall.clocgame.schedulers;

import it.sauronsoftware.cron4j.Scheduler;
import lombok.Getter;
import net.watersfall.clocgame.turn.TurnMonth;

public class MonthScheduler
{
	private static @Getter
	Scheduler scheduler;

	private MonthScheduler()
	{
		scheduler = new Scheduler();
		scheduler.schedule("0 */12 * * *", new TurnMonth());
		scheduler.start();
	}

	public static void startMonth()
	{
		if(scheduler == null)
		{
			new MonthScheduler();
		}
	}

	public static void stopMonth()
	{
		if(scheduler != null)
		{
			scheduler.stop();
			scheduler = null;
		}
	}
}
