package com.watersfall.cloc.database;

import org.apache.commons.dbcp2.*;

import javax.sql.DataSource;

public class Database
{
    private static BasicDataSource database;

    private static BasicDataSource createDatabase()
    {
        database = new BasicDataSource();
        database.setUrl("jdbc:mysql://localhost/cloc");
        database.setUsername("root");
        database.setPassword("***REMOVED***");
        database.setMinIdle(5);
        database.setMaxIdle(10);
        database.setMaxOpenPreparedStatements(100);
        return database;
    }

    public static BasicDataSource getDataSource()
    {
        if(database == null)
        {
            database = createDatabase();
        }
        return database;
    }
}
