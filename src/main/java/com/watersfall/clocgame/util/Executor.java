package com.watersfall.clocgame.util;

import com.watersfall.clocgame.exception.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface Executor
{
	String exec(Connection connection) throws IllegalArgumentException, NullPointerException, SQLException, NotLoggedInException,
			NationNotFoundException, CityNotFoundException, TreatyNotFoundException, TreatyPermissionException, IOException, ServletException;
}