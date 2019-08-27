package com.watersfall.clocgame.listeners;

import com.watersfall.clocgame.controller.Sessions;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class Session implements HttpSessionListener
{
	@Override
	public void sessionCreated(HttpSessionEvent se)
	{

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event)
	{
		Sessions sessions = Sessions.getInstance();
		sessions.removeNation(Integer.parseInt(event.getSession().getAttribute("user").toString()));
	}
}
