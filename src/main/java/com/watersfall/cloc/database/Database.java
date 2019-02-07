package com.watersfall.cloc.database;

import org.apache.commons.dbcp2.*;

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
        database.setMaxIdle(25);
        database.setMaxTotal(100);
        database.setMaxOpenPreparedStatements(100);
        database.setDefaultAutoCommit(false);
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
