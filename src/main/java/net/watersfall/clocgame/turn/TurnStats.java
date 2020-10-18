package net.watersfall.clocgame.turn;

import net.watersfall.clocgame.model.Stats;

public class TurnStats implements Runnable
{
	@Override
	public void run()
	{
		Stats.getInstance().updateStats();
	}
}
