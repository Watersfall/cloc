package com.watersfall.clocgame.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

public class SqlBuilder
{
	private ArrayList<String> tables;
	private ArrayList<String> fields;
	private ArrayList<String> where;
	private @Getter int fieldCount;

	public SqlBuilder()
	{
		tables = new ArrayList<>();
		fields = new ArrayList<>();
		where = new ArrayList<>();
		fieldCount = 0;
	}

	public SqlBuilder addTables(String... strings)
	{
		tables.addAll(Arrays.asList(strings));
		return this;
	}

	public SqlBuilder addFields(String... strings)
	{
		fields.addAll(Arrays.asList(strings));
		return this;
	}

	public SqlBuilder addWheres(String... strings)
	{
		where.addAll(Arrays.asList(strings));
		return this;
	}

	public String build()
	{
		StringBuilder sql = new StringBuilder("UPDATE ");
		for(int i = 0; i < tables.size(); i++)
		{
			if(i != 0)
			{
				sql.append(", ");
			}
			sql.append(tables.get(i));
		}
		sql.append(" SET ");
		for(int i = 0; i < fields.size(); i++)
		{
			if(i != 0)
			{
				sql.append(", ");
			}
			sql.append(fields.get(i)).append("=?");
			fieldCount++;
		}
		if(where.size() > 0)
		{
			sql.append(" WHERE ").append(where.get(0)).append(".id=?");
			fieldCount++;
		}
		for(int i = 1; i < where.size(); i++)
		{
			sql.append(" AND ").append(where.get(i - 1)).append(".id=").append(where.get(i)).append(".id");
		}
		return sql.toString();
	}
}