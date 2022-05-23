package in.jaxer.sdbms;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.sdbms.exceptions.JaxerSDBMSException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author Shakir Ansari
 */
@Log4j2
@Builder(setterPrefix = "with")
public class SimpleQuery
{

	@Getter
	private String sqlQuery;

	@Getter
	@Setter
	private Connection connection;

	private HashMap<String, Object> paramMap;

	private HashMap<String, Collection> paramListMap;

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

	private void processParams(NamedStatement namedStatement)
	{
		if (JValidator.isNotNullAndNotEmpty(paramListMap))
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
		if (JValidator.isNotNullAndNotEmpty(paramMap))
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

	public <T> List<T> getResultList(Class<T> entity)
	{
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
			throw new JaxerSDBMSException(exception);
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
			throw new JaxerSDBMSException(exception);
		}
	}
}
