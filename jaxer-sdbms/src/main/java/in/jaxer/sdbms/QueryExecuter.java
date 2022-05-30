package in.jaxer.sdbms;

import in.jaxer.sdbms.exceptions.JaxerSDBMSException;
import in.jaxer.sdbms.utils.NamedStatementUtils;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class QueryExecuter
{

	public static List<Row> execute(Connection connection, String sql)
	{
		return QueryExecuter.execute(connection, sql, new ArrayList<>());
	}

	public static List<Row> execute(Connection connection, String sql, Parameter parameter)
	{
		return QueryExecuter.execute(connection, sql, Arrays.asList(parameter));
	}

	public static List<Row> execute(Connection connection, String sql, List<Parameter> parameterList)
	{
		log.debug("sql: {}", sql);
		log.debug("parameterList: {}", parameterList);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			NamedStatementUtils.setParameteres(namedStatement, parameterList);

			try (ResultSet resultSet = namedStatement.executeQuery())
			{
				return ResultsetMapper.getRowList(resultSet);
			}
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public static <T> List<T> execute(Connection connection, Class<T> outputClass, String sql)
	{
		return QueryExecuter.execute(connection, outputClass, sql, new ArrayList<>());
	}

	public static <T> List<T> execute(Connection connection, Class<T> outputClass, String sql, Parameter parameter)
	{
		return QueryExecuter.execute(connection, outputClass, sql, Arrays.asList(parameter));
	}

	public static <T> List<T> execute(Connection connection, Class<T> outputClass, String sql, List<Parameter> parameterList)
	{
		log.debug("sql: {}", sql);
		log.debug("outputClass: {}", outputClass);
		log.debug("parameterList: {}", parameterList);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			NamedStatementUtils.setParameteres(namedStatement, parameterList);

			try (ResultSet resultSet = namedStatement.executeQuery())
			{
				return ResultsetMapper.getObjectList(resultSet, outputClass);
			}
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public static <T> List<T> execute(Connection connection, String sql, List<Parameter> parameterList, RawMapper<T> rawMapper)
	{
		log.debug("sql: {}", sql);
		log.debug("rawMapper: {}", rawMapper);
		log.debug("parameterList: {}", parameterList);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			NamedStatementUtils.setParameteres(namedStatement, parameterList);

			List<T> list = new ArrayList<>();

			try (ResultSet resultSet = namedStatement.executeQuery())
			{
				int index = 0;
				while (resultSet.next())
				{
					list.add(rawMapper.map(resultSet, index++));
				}
			}

			return list;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public static int update(Connection connection, String sql)
	{
		return update(connection, sql, new ArrayList<>());
	}

	public static int update(Connection connection, String sql, Parameter parameter)
	{
		return update(connection, sql, Arrays.asList(parameter));
	}

	public static int update(Connection connection, String sql, List<Parameter> parameterList)
	{
		log.debug("sql: {}", sql);
		log.debug("parameterList: {}", parameterList);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			NamedStatementUtils.setParameteres(namedStatement, parameterList);

			return namedStatement.executeUpdate();
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public static long largeUpdate(Connection connection, String sql)
	{
		return largeUpdate(connection, sql, new ArrayList<>());
	}

	public static long largeUpdate(Connection connection, String sql, Parameter parameter)
	{
		return largeUpdate(connection, sql, Arrays.asList(parameter));
	}

	public static long largeUpdate(Connection connection, String sql, List<Parameter> parameterList)
	{
		log.debug("sql: {}", sql);
		log.debug("parameterList: {}", parameterList);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			NamedStatementUtils.setParameteres(namedStatement, parameterList);

			return namedStatement.executeLargeUpdate();
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}
}
