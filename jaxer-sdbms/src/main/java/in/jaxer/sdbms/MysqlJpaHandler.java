package in.jaxer.sdbms;

import in.jaxer.core.utilities.Collections;
import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.Strings;
import in.jaxer.sdbms.annotations.PrimaryKey;
import in.jaxer.sdbms.exceptions.JaxerSDBMSException;
import in.jaxer.sdbms.utils.AbstractJpaHandler;
import in.jaxer.sdbms.utils.NamedStatementUtils;
import lombok.extern.log4j.Log4j2;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class MysqlJpaHandler extends AbstractJpaHandler
{

	private static MysqlJpaHandler instance;

	private MysqlJpaHandler()
	{
	}

	public static MysqlJpaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new MysqlJpaHandler();
		}
		return instance;
	}

	private <T> List<T> _find(final boolean isSingleObject, Connection connection, Class<T> outputClass, List<Parameter> params)
	{
		String tableName = getTableName(outputClass);

		String where = getWhereClause(params);

		String sql = "SELECT * FROM `" + tableName + "`";
		sql = sql + " " + where;

		if (isSingleObject)
		{
			sql = sql + " LIMIT 1";
		}

		log.debug("sql: {}, params: {}", sql, params);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			NamedStatementUtils.setParameteres(namedStatement, params);

			try (ResultSet resultSet = namedStatement.executeQuery())
			{
				List<T> objectList = ResultsetMapper.getObjectList(resultSet, outputClass);

				return objectList;
			}
		} catch (Exception exception)
		{
			log.error("Exception: ", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	@Override
	public <T> T find(Connection connection, Class<T> outputClass, List<Parameter> parameterList)
	{
		List<T> objectList = _find(true, connection, outputClass, parameterList);
		return JValidator.isEmpty(objectList)
				? null
				: objectList.get(0);
	}

	@Override
	public <T> List<T> findList(Connection connection, Class<T> outputClass, List<Parameter> parameterList)
	{
		return _find(true, connection, outputClass, parameterList);
	}

	@Override
	public int delete(Connection connection, Class outputClass, Object id)
	{
		String tableName = getTableName(outputClass);
		String primaryKeyColumnName = getPrimaryColumnName(outputClass);

		String sql = "DELETE FROM `" + tableName + "` WHERE `" + primaryKeyColumnName + "` = ?";

		log.debug("sql: {}", sql);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setObject(1, id);
			return preparedStatement.executeUpdate();
		} catch (Exception exception)
		{
			log.error("Exception: ", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	@Override
	public <T> T merge(Connection connection, T t)
	{
		Class outputClass = t.getClass();
		getPrimaryKey(outputClass);

		String tableName = getTableName(outputClass);
		String comma = ",";

		HashMap<String, Object> primaryHashMap = getColumnKeyValue(outputClass, true);
		HashMap<String, Object> columnHashMap = getColumnKeyValue(outputClass, false);

		String columns = "";
		Set<Map.Entry<String, Object>> columnEntryset = columnHashMap.entrySet();
		for (Map.Entry<String, Object> entry : columnEntryset)
		{
			if (JValidator.isNotNullAndNotEmpty(entry.getKey()))
			{
				columns += " `" + entry.getKey() + "` = :" + entry.getValue() + comma;
			}
		}

		if (columns.endsWith(comma))
		{
			columns = Strings.removeEndsWith(columns, comma);
		}

		String where = "WHERE 1=1";
		Set<Map.Entry<String, Object>> primaryEntryset = primaryHashMap.entrySet();
		for (Map.Entry<String, Object> primaryEntry : primaryEntryset)
		{
			where += " AND `" + primaryEntry.getKey() + "` = :" + primaryEntry.getValue();
			break;
		}

		String sql = "UPDATE `" + tableName + "` SET" + columns + " " + where;

		log.debug("sql: {}", sql);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			columnHashMap.putAll(primaryHashMap);

			NamedStatementUtils.setParameteres(namedStatement, columnHashMap, outputClass, t);

			namedStatement.executeUpdate();
			return t;
		} catch (Exception exception)
		{
			log.error("Exception: ", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	@Override
	public <T> T persist(Connection connection, T t)
	{
		Class outputClass = t.getClass();

		String tableName = getTableName(outputClass);
		String comma = ",";
		String columnNames = "";
		String value = "";

		HashMap<String, Object> columnHashMap = getColumnKeyValue(outputClass, false);
		Set<Map.Entry<String, Object>> columnEntryset = columnHashMap.entrySet();
		for (Map.Entry<String, Object> entry : columnEntryset)
		{
			if (JValidator.isNotNullAndNotEmpty(entry.getKey()))
			{
				columnNames += " `" + entry.getKey() + "`" + comma;
				value += " :" + entry.getValue() + comma;
			}
		}

		/**
		 * Adding primary column if auto_increment is disabled
		 */
		PrimaryKey primaryKey = getPrimaryKey(outputClass);
		HashMap<String, Object> primaryColumnHashMap = getColumnKeyValue(outputClass, true);
		Set<Map.Entry<String, Object>> primaryColumnEntryset = primaryColumnHashMap.entrySet();
		for (Map.Entry<String, Object> entry : primaryColumnEntryset)
		{
			if (JValidator.isNotNullAndNotEmpty(entry.getKey()))
			{
				columnNames += " `" + entry.getKey() + "`" + comma;

				Object object = entry.getValue();
				if (object == null && primaryKey.uuidValue())
				{
					value += " :" + Strings.getUUID().replace("-", "") + comma;
				} else
				{
					value += " :" + object + comma;
				}

				//Adding primary column key-value to previous non-primary hashmap
				columnHashMap.put(entry.getKey(), entry.getValue());
			}
		}

		columnNames = Strings.removeEndsWith(columnNames, comma);
		value = Strings.removeEndsWith(value, comma);

		String sql = "INSERT INTO `" + tableName + "` (" + columnNames + ") VALUES (" + value + ")";

		log.debug("sql: {}", sql);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql, Statement.RETURN_GENERATED_KEYS))
		{
			NamedStatementUtils.setParameteres(namedStatement, columnHashMap, outputClass, t);

			if (0 != namedStatement.executeUpdate())
			{
				try (ResultSet resultSet = namedStatement.getGeneratedKeys())
				{
					if (resultSet.next())
					{
//						new PropertyDescriptor(primaryKey.value(), outputClass).getWriteMethod().invoke(t, resultSet.getObject(1));
						new PropertyDescriptor(getPrimaryFieldName(outputClass), outputClass).getWriteMethod().invoke(t, resultSet.getObject(1));
					}
				}
			}
		} catch (Exception exception)
		{
			log.error("Exception: ", exception);
			throw new JaxerSDBMSException(exception);
		}

		return t;
	}

	@Override
	public long count(Connection connection, Class outputClass, List<Parameter> parameterList)
	{
		String tableName = getTableName(outputClass);

		String where = getWhereClause(parameterList);

		String sql = "SELECT COUNT(*) FROM `" + tableName + "` " + where;

		log.debug("sql: {}", sql);
		log.debug("parameterList: {}", parameterList);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			NamedStatementUtils.setParameteres(namedStatement, parameterList);
			try (ResultSet resultSet = namedStatement.executeQuery())
			{
				return resultSet.next()
						? resultSet.getLong(1)
						: 0;
			}
		} catch (Exception exception)
		{
			log.error("Exception: ", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	@Override
	protected String getWhereClause(List<Parameter> parameterList)
	{
		String where = "WHERE 1=1";

		if (Collections.isNotEmpty(parameterList))
		{
			for (Parameter parameter : parameterList)
			{
				if (parameter.getValue() instanceof Collection)
				{
					where += parameter.isEquals()
							? " AND `" + parameter.getName() + "`" + " IN (:" + parameter.getName() + ")"
							: " AND `" + parameter.getName() + "`" + " NOT IN (:" + parameter.getName() + ")";
				} else
				{
					where += parameter.isEquals()
							? " AND `" + parameter.getName() + "`" + " = :" + parameter.getName()
							: " AND `" + parameter.getName() + "`" + " != :" + parameter.getName();
				}
			}
		}

		return where;
	}

}
