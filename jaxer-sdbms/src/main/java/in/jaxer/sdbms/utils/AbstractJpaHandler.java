package in.jaxer.sdbms.utils;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.sdbms.Parameter;
import in.jaxer.sdbms.annotations.Column;
import in.jaxer.sdbms.annotations.PrimaryKey;
import in.jaxer.sdbms.annotations.Table;
import in.jaxer.sdbms.exceptions.JaxerSDBMSException;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

/**
 * @author Shakir Ansari
 */
@Log4j2
public abstract class AbstractJpaHandler
{

	abstract public <T> T find(Connection connection, Class<T> outputClass, List<Parameter> parameterList);

	abstract public <T> List<T> findList(Connection connection, Class<T> outputClass, List<Parameter> parameterList);

	abstract public int delete(Connection connection, Class outputClass, Object id);

	abstract public <T> T merge(Connection connection, T t);

	abstract public <T> T persist(Connection connection, T t);

	abstract public long count(Connection connection, Class outputClass, List<Parameter> parameterList);

	abstract protected String getWhereClause(List<Parameter> parameterList);

	protected String getTableName(Class outputClass)
	{
		if (!outputClass.isAnnotationPresent(Table.class))
		{
			throw new JaxerSDBMSException("OutputClass [" + outputClass.getName() + "] should be decorated by @" + Table.class.getName());
		}

		String tableName = ((Table) outputClass.getAnnotation(Table.class)).value();
		if (JValidator.isNullOrEmpty(tableName))
		{
			throw new JaxerSDBMSException("Table name not found in " + outputClass.getName());
		}

		return tableName;
	}

	public HashMap<String, Object> getColumnKeyValue(Class outputClass, boolean primary)
	{
		HashMap<String, Object> hashMap = new HashMap<>();

		Field[] fields = outputClass.getDeclaredFields();

		for (Field field : fields)
		{
			if (field.isAnnotationPresent(Column.class))
			{
				Column column = field.getAnnotation(Column.class);
				if (primary)
				{
					if (field.isAnnotationPresent(PrimaryKey.class))
					{
						hashMap.put(column.value(), field.getName());
					}
				} else
				{
					if (!field.isAnnotationPresent(PrimaryKey.class))
					{
						hashMap.put(column.value(), field.getName());
					}
				}
			}
		}
		return hashMap;
	}

	protected String getPrimaryColumnName(Class aClass)
	{
		Field[] fields = aClass.getDeclaredFields();
		for (Field field : fields)
		{
			if (field.isAnnotationPresent(PrimaryKey.class)
					&& field.isAnnotationPresent(Column.class))
			{
				return field.getAnnotation(Column.class).value();
			}
		}

		throw new JaxerSDBMSException("Primary Column not found in " + aClass.getName());
	}

	protected String getPrimaryFieldName(Class aClass)
	{
		Field[] fields = aClass.getDeclaredFields();
		for (Field field : fields)
		{
			if (field.isAnnotationPresent(PrimaryKey.class)
					&& field.isAnnotationPresent(Column.class))
			{
				return field.getName();
			}
		}

		throw new JaxerSDBMSException("Primary Column not found in " + aClass.getName());
	}

	protected List<PrimaryKey> getPrimaryKeyColumnList(Class outputClass)
	{
		List<PrimaryKey> columnList = null;

		Field[] fields = outputClass.getDeclaredFields();

		for (Field field : fields)
		{
			if (field.isAnnotationPresent(PrimaryKey.class))
			{
				PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
				if (JValidator.isNullOrEmpty(columnList))
				{
					columnList = new ArrayList<>();
				}
				columnList.add(pk);
			}
		}
		return columnList;
	}

	protected PrimaryKey getPrimaryKey(Class outputClass)
	{
		List<PrimaryKey> primaryColumnList = getPrimaryKeyColumnList(outputClass);

		if (JValidator.isNullOrEmpty(primaryColumnList))
		{
			throw new IllegalArgumentException("Annotation @" + PrimaryKey.class.getName() + " not found in " + outputClass.getName());
		}

		if (primaryColumnList.size() > 1)
		{
			throw new IllegalArgumentException("Multiple (" + primaryColumnList.size() + ") primary keys found in " + outputClass.getName());
		}

		return primaryColumnList.get(0);
	}

	protected void verifyPrimaryColumnValue(Class outputClass)
	{
		PrimaryKey primaryKey = getPrimaryKey(outputClass);
		if (primaryKey.uuidValue() == false)
		{
			HashMap<String, Object> hashMap = getColumnKeyValue(outputClass, true);
			Set<Map.Entry<String, Object>> entrySet = hashMap.entrySet();
			for (Map.Entry<String, Object> entry : entrySet)
			{
				if (entry.getValue() == null)
				{
					throw new JaxerSDBMSException("Primary key cannot be null for " + outputClass.getName());
				}
			}
		}
	}

	public <T> T find(Connection connection, Class<T> outputClass, Object id)
	{
		JValidator.throwWhenNull(id, "Id cannot be null");

		return find(connection, outputClass, new Parameter(getPrimaryColumnName(outputClass), id, true));
	}

	public <T> T find(Connection connection, Class<T> outputClass, Parameter parameter)
	{
		JValidator.throwWhenNull(parameter, "Parameter cannot be null");

		return find(connection, outputClass, Arrays.asList(parameter));
	}

	public <T> List<T> findList(Connection connection, Class<T> outputClass)
	{
		return findList(connection, outputClass, new ArrayList<>());
	}

	public <T> List<T> findList(Connection connection, Class<T> outputClass, Parameter parameter)
	{
		return findList(connection, outputClass, Arrays.asList(parameter));
	}

	public <T> List<T> findByIdList(Connection connection, Class<T> outputClass, List<Integer> idList)
	{
		if (JValidator.isNullOrEmpty(idList))
		{
			return null;
		}

		List<T> objectList = new ArrayList<>();
		for (Object object : idList)
		{
			T t = find(connection, outputClass, object);

			if (t != null)
			{
				objectList.add(t);
			}
		}
		return objectList;
	}

	public long count(Connection connection, Class outputClass)
	{
		return count(connection, outputClass, new ArrayList<>());
	}

	public long count(Connection connection, Class outputClass, Parameter parameter)
	{
		log.debug("outputClass: {}, parameter: {}", outputClass, parameter);
		return count(connection, outputClass, Arrays.asList(parameter));
	}
}
