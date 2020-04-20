package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.action.EventActions;

public enum Event
{
	STRIKE;

	public static String generateEventText(Event event)
	{
		switch(event)
		{
			case STRIKE:
				return  "<h3 class=\"halfPad\">Strike!</h3>" +
						"<p class=\"halfPad\">Workers unions across the country have organized strikes and have ground the economy of %s to a halt. " +
						"We can either negotiate with the union leaders, ignore the situation or issue an injunction and enforce it with the army.</p>" +
						"<button onclick=\"runEvent('{event.id}', '" + EventActions.Action.STRIKE_GIVE_IN.name() +"');\">Give in to demands</button>" +
						"<button onclick=\"runEvent('{event.id}', '" + EventActions.Action.STRIKE_IGNORE.name() +"');\">Ignore them...</button>" +
						"<button onclick=\"runEvent('{event.id}', '" + EventActions.Action.STRIKE_SEND_ARMY.name() +"');\">Send in the army</button>";
			default:
				return "";
		}
	}
}
