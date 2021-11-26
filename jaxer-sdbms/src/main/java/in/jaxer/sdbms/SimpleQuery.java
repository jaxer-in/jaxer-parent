
package in.jaxer.sdbms;

import in.jaxer.core.utilities.Validator;
import in.jaxer.sdbms.exceptions.SDBMSException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
@Builder
public class SimpleQuery
{

	@Getter
	private String sqlQuery;

	@Getter
	@Setter
	private Connection connection;

	private HashMap<String, Object> paramMap;

	private HashMap<String, Collection> paramListMap;

	private Class entity;

	public void setNativeQuery(String sqlQuery)
	{
		this.sqlQuery = sqlQuery;
	}

	public void setParameter(String paramName, Object paramValue)
	{
		this.paramMap.put(paramName, paramValue);
	}

	public void setParameterList(String paramName, Collection collection)
	{
		this.paramListMap.put(paramName, collection);
	}

	public void addEntity(Class entity)
	{
		this.entity = entity;
	}

	private void processParams(NamedStatement namedStatement)
	{
		if (Validator.isNotEmpty(paramListMap))
		{
			Set<Map.Entry<String, Collection>> entrysetList = paramListMap.entrySet();
			for (Map.Entry<String, Collection> entry : entrysetList)
			{
				if (sqlQuery.contains(":" + entry.getKey()))
				{
					namedStatement.setParameterList(entry.getKey(), entry.getValue());
				}
			}
		}
		if (Validator.isNotEmpty(paramMap))
		{
			Set<Map.Entry<String, Object>> entryset = paramMap.entrySet();
			for (Map.Entry<String, Object> entry : entryset)
			{
				if (sqlQuery.contains(":" + entry.getKey()))
				{
					namedStatement.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	public <T> List<T> getResultList()
	{
		if (this.entity == null)
		{
			throw new SDBMSException("Entity is undefined");
		}

		try (NamedStatement namedStatement = new NamedStatement(connection, sqlQuery))
		{
			processParams(namedStatement);

			try (ResultSet resultSet = namedStatement.executeQuery();)
			{
				return ResultsetMapper.getObjectList(resultSet, entity);
			}
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new SDBMSException(exception);
		}
	}

	public List<Row> getRowList()
	{
		try (NamedStatement namedStatement = new NamedStatement(connection, sqlQuery))
		{
			processParams(namedStatement);

			try (ResultSet resultSet = namedStatement.executeQuery();)
			{
				return ResultsetMapper.getRowList(resultSet);
			}
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new SDBMSException(exception);
		}
	}
}
