package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.model.nation.Nation;

import java.util.HashMap;

public class Sessions
{
	private HashMap<Integer, Nation> nations;
	private static Sessions sessions;

	private Sessions()
	{
		nations = new HashMap<>();
	}

	public static Sessions getInstance()
	{
		if(sessions == null)
		{
			sessions = new Sessions();
		}
		return sessions;
	}

	public void addNation(int id, Nation nation)
	{
		nations.put(id, nation);
	}

	public void removeNation(int id)
	{
		nations.remove(id);
	}

	public Nation getNation(int id)
	{
		return nations.get(id);
	}
}
