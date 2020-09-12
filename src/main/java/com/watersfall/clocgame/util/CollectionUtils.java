package com.watersfall.clocgame.util;

import java.util.*;

public class CollectionUtils
{
	@SafeVarargs
	public static <T> Set<T> immutableSetOf(T... objects)
	{
		Set<T> set = new java.util.HashSet<>(Arrays.asList(objects));
		return Collections.unmodifiableSet(set);
	}

	@SafeVarargs
	public static <T> List<T> immutableListOf(T... objects)
	{
		List<T> list = new ArrayList<>(Arrays.asList(objects));
		return Collections.unmodifiableList(list);
	}
}
