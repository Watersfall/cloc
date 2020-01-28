package com.watersfall.clocgame.turn;

import com.watersfall.clocgame.model.Stats;

public class TurnStats implements Runnable
{
	@Override
	public void run()
	{
		Stats.getInstance().updateStats();
	}
}
