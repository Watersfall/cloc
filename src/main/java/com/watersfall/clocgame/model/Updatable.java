package com.watersfall.clocgame.model;

import lombok.Getter;

import java.sql.*;
import java.util.HashMap;
import java.util.Set;

public class Updatable
{
	protected @Getter int id;
	protected @Getter ResultSet results;
	protected @Getter HashMap<String, Object> fields;
	protected @Getter String tableName;

	public Updatable(String tableName, int id, ResultSet results)
	{
		this.tableName = tableName;
		this.id = id;
		this.results = results;
		fields = new HashMap<>();
	}

	public Object getByName(String name) throws SQLException
	{
		if(this.fields.get(name) != null)
		{
			return this.fields.get(name);
		}
		int column = results.findColumn(name);
		Object value;
		switch (results.getMetaData().getColumnType(column))
		{
			case Types.INTEGER:
			case Types.TINYINT:
			case Types.SMALLINT:
				value = results.getInt(name);
				break;
			case Types.VARCHAR:
			case Types.CHAR:
				value = results.getString(name);
				break;
			case Types.BIGINT:
				value = results.getLong(name);
				break;
			case Types.FLOAT:
			case Types.DECIMAL:
			case Types.DOUBLE:
				value = results.getDouble(name);
				break;
			default:
				value = results.getString(name);
		}
		return value;
	}

	public void setByName(String name, Object value) throws SQLException
	{
		this.addField(name, value);
	}

	public void addField(String name, Object value)
	{
		fields.put(name, value);
	}

	public Object getField(String name)
	{
		return fields.get(name);
	}

	public void update(Connection conn) throws SQLException
	{
		StringBuilder sql = new StringBuilder("UPDATE " + this.tableName + " SET ");
		Set<HashMap.Entry<String, Object>> entries = this.fields.entrySet();
		if(entries.size() > 0)
		{
			int index = 1;
			for(HashMap.Entry<String, Object> entry : entries)
			{
				sql.append(entry.getKey()).append("=?");
				if(index != entries.size())
				{
					sql.append(", ");
				}
				index++;
			}
			sql.append(" WHERE id=?");
			index = 1;
			PreparedStatement statement = conn.prepareStatement(sql.toString());
			for(HashMap.Entry<String, Object> entry : entries)
			{
				if(entry.getValue() instanceof Integer)
					statement.setInt(index, (Integer) entry.getValue());
				else if(entry.getValue() instanceof Double)
					statement.setDouble(index, (Double) entry.getValue());
				else if(entry.getValue() instanceof Long)
					statement.setLong(index, (Long)entry.getValue());
				else if(entry.getValue() instanceof Boolean)
					statement.setBoolean(index, (Boolean)entry.getValue());
				else
					statement.setString(index, entry.getValue().toString());
				index++;
			}
			statement.setInt(index, id);
			statement.execute();
		}
	}
}
