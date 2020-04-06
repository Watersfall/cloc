package com.watersfall.clocgame.model.military;

import java.util.ArrayList;
import java.util.Arrays;

public interface Plane
{
	public static ArrayList<Plane> getAllPlanes()
	{
		ArrayList<Plane> list = new ArrayList<>(Arrays.asList(Fighter.values()));
		list.addAll(Arrays.asList(Bomber.values()));
		return list;
	}
}
