package in.jaxer.sdbms.utils;

import in.jaxer.sdbms.DbConnectionHelper;
import in.jaxer.sdbms.NamedStatement;
import in.jaxer.sdbms.ResultsetMapper;
import in.jaxer.sdbms.Row;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class DbConnection
{

	private static final String HOST = "localhost:3306";

	private static final String DB_NAME = "at_v2";

	private static final String USERNAME = "root";

	private static final String PASSWORD = "";

	public static Connection getConnection()
	{
		try
		{
			return DbConnectionHelper.getMySQLConnection(HOST, DB_NAME, USERNAME, PASSWORD);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws SQLException
	{
		String sql = ""
				+ " SELECT * FROM `access_tokens`"
				+ " WHERE 1=1"
				+ " AND `user_id` IN (:userId)"
				+ " AND `type` = :type"
				+ " AND `access_token` IS :accessToken";

		log.debug("sql: {}", sql);

		try (Connection connection = getConnection();
			 NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			namedStatement.setParameterList("userId", Arrays.asList(1, 2));
			namedStatement.setParameter("type", "_WEB");
			namedStatement.setParameter("accessToken", null);

			log.debug("namedStatement: {}", namedStatement);

			try (ResultSet resultSet = namedStatement.executeQuery())
			{
				List<Row> rows = ResultsetMapper.getRowList(resultSet);
				for (Row row : rows)
				{
					log.info("row: {}", row);
				}
			}
			log.info("connection: {}", connection);
		}
	}
}
