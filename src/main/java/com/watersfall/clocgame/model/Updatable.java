package com.watersfall.clocgame.model;

import com.watersfall.clocgame.util.SqlBuilder;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Updatable
{
	protected @Getter int id;
	protected @Getter HashMap<String, Object> fields;
	protected @Getter String tableName;

	public Updatable(String tableName, int id)
	{
		this.tableName = tableName;
		this.id = id;
		fields = new HashMap<>();
	}

	public void setField(String name, Object value)
	{
		fields.put(name, value);
	}

	public Object getField(String name)
	{
		return fields.get(name);
	}

	public void update(Connection conn) throws SQLException
	{
		if(!this.fields.isEmpty())
		{
			SqlBuilder builder = new SqlBuilder();
			builder.addTables(this.tableName);
			builder.addWheres(this.tableName);
			String[] strings = new String[this.fields.size()];
			builder.addFields(this.fields.keySet().toArray(strings));
			PreparedStatement statement = conn.prepareStatement(builder.build());
			int i = 1;
			for(Object object : fields.values())
			{
				if(object instanceof Integer)
				{
					statement.setInt(i, (Integer)object);
				}
				else if(object instanceof Double)
				{
					statement.setDouble(i, (Double)object);
				}
				else if(object instanceof Boolean)
				{
					statement.setBoolean(i, (Boolean)object);
				}
				else if(object instanceof String)
				{
					statement.setString(i, (String)object);
				}
				else if(object instanceof Long)
				{
					statement.setLong(i, (Long)object);
				}
				else
				{
					statement.setString(i, object.toString());
				}
				i++;
			}
			statement.setInt(i, id);
			statement.execute();
		}
	}
}
