package net.watersfall.clocgame.database;


import com.zaxxer.hikari.HikariDataSource;

public class Database
{

	private static HikariDataSource database;

	private static HikariDataSource createDatabase()
	{
		database = new HikariDataSource();
		database.setJdbcUrl("jdbc:mysql://localhost/cloc?allowMultiQueries=true&rewriteBatchedStatements=true");
		database.setUsername("root");
		database.setPassword(System.getenv("CLOC_PASS"));
		database.setMaximumPoolSize(12);
		database.setAutoCommit(false);
		return database;
	}

	public static HikariDataSource getDataSource()
	{
		if(database == null)
		{
			database = createDatabase();
		}
		return database;
	}
}
