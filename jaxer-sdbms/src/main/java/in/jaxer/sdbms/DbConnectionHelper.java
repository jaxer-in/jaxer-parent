package in.jaxer.sdbms;

import in.jaxer.core.constants.DbDriverClassNames;
import in.jaxer.core.utilities.JValidator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Shakir
 */
public class DbConnectionHelper
{
	public static Connection getConnection(String url) throws SQLException
	{
		return DriverManager.getConnection(url);
	}

	public static Connection getConnection(String driverClassName, String url, String username, String password) throws ClassNotFoundException, SQLException
	{
		Class.forName(driverClassName);
		return DriverManager.getConnection(url, username, password);
	}

	public static Connection getSpreadsheetXLSXConnection(File file) throws SQLException
	{
		return getSpreadsheetXLSXConnection(file.getAbsolutePath());
	}

	public static Connection getSpreadsheetXLSXConnection(String fileName) throws SQLException
	{
//		String url = "jdbc:odbc:DRIVER={Microsoft Excel Driver (*.xls)};" + "DBQ=X:/book1.xlsx;ReadOnly=0;";
		String url = "jdbc:odbc:DRIVER={Microsoft Excel Driver (*.xls)};" + "DBQ=" + fileName + ";ReadOnly=0;";
		return getConnection(url);
	}

	public static Connection getMySQLConnection(String host, String dbName, String username, String password) throws ClassNotFoundException, SQLException
	{
		return getMySQLConnection(host, null, dbName, username, password);
	}

	public static Connection getMySQLConnection(String host, String queryParam, String dbName, String username, String password) throws ClassNotFoundException, SQLException
	{
		String url;
		if (JValidator.isNullOrEmpty(queryParam))
		{
			url = "jdbc:mysql://" + host + "/" + dbName;
		} else
		{
			queryParam = queryParam.startsWith("?") ? queryParam : "?" + queryParam;
			url = "jdbc:mysql://" + host + "/" + dbName + queryParam;
		}

		return getConnection(DbDriverClassNames.COM_MYSQL_JDBC_DRIVER, url, username, password);
	}

	public static Connection getH2EmbeddedConnection(String dbName, String username, String password) throws ClassNotFoundException, SQLException
	{
		String url = "jdbc:h2:~/" + dbName;
		return getConnection(DbDriverClassNames.ORG_H2_DRIVER, url, username, password);
	}

	public static Connection getH2EmbeddedConnection(String dbName, String username, String password, String filePassword) throws ClassNotFoundException, SQLException
	{
		return getH2EmbeddedConnection(dbName, username, filePassword + " " + password);
	}

	public static Connection getH2ServerConnection(String host, String dbName, String username, String password) throws ClassNotFoundException, SQLException
	{
		String url = "jdbc:h2:tcp://" + host + "/~/" + dbName;
		return getConnection(DbDriverClassNames.ORG_H2_DRIVER, url, username, password);
	}

	public static Connection getH2ServerConnection(String host, String dbName, String username, String password, String filePassword) throws ClassNotFoundException, SQLException
	{
		return getH2ServerConnection(host, dbName, username, filePassword + " " + password);
	}

	public static Connection getHSQLConnection(String dbName, String username, String password) throws ClassNotFoundException, SQLException
	{
		String url = "jdbc:hsqldb:data/" + dbName;

		return getConnection(DbDriverClassNames.JDBC_JDBCDRIVER, url, username, password);
	}

	public static Connection getOracleConnection(String host, String port, String dbName, String username, String password) throws ClassNotFoundException, SQLException
	{
		//--- "jdbc:oracle:thin:@localhost:1521:databaseName"
		String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;

		return getConnection(DbDriverClassNames.ORACLE_JDBC_DRIVER_ORACLEDRIVER, url, username, password);
	}
}
