package com.watersfall.clocgame.model;

import java.sql.Connection;
import java.sql.SQLException;

public interface Updatable
{
	void update(Connection connection) throws SQLException;
}
