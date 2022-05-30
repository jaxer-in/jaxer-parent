package in.jaxer.sdbms;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.sdbms.annotations.Column;
import in.jaxer.sdbms.annotations.Table;
import in.jaxer.sdbms.exceptions.JaxerSDBMSException;
import lombok.extern.log4j.Log4j2;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class ResultsetMapper
{
	private ResultsetMapper()
	{
	}

	private static <T> List<T> getMultiColumnListList(ResultSet resultSet, Class<T> outputClass)
	{
		if (!outputClass.isAnnotationPresent(Table.class))
		{
			throw new JaxerSDBMSException(outputClass.getName() + " must be decorated by @" + Table.class.getName());
		}

		try
		{
			List<T> outputList = null;
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			Field[] fields = outputClass.getDeclaredFields();
			while (resultSet.next())
			{
				T bean = outputClass.newInstance();

				for (int i = 0; i < resultSetMetaData.getColumnCount(); i++)
				{
					Object columnValue = resultSet.getObject(i + 1);
					if (columnValue == null)
					{
						continue;
					}

					String columnName = resultSetMetaData.getColumnName(i + 1);

					for (Field field : fields)
					{
						if (field.isAnnotationPresent(Column.class))
						{
							Column column = field.getAnnotation(Column.class);
							if (column.value().equalsIgnoreCase(columnName))
							{
//								field.set(bean, resultSet.getObject(columnName, field.getType()));
								Class<?> fieldType = field.getType();
								if (fieldType.equals(java.util.Date.class))
								{
									new PropertyDescriptor(field.getName(), outputClass)
											.getWriteMethod()
											.invoke(bean, columnValue);
								} else
								{
									new PropertyDescriptor(field.getName(), outputClass)
											.getWriteMethod()
											//.invoke(bean, resultSet.getObject(columnName, fieldType));
											.invoke(bean, columnValue);
								}
								break;
							}
						}
					}
				}

				if (outputList == null)
				{
					outputList = new ArrayList<>();
				}

				outputList.add(bean);
			}

			return outputList;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	private static <T> List<T> getSingleColumnList(ResultSet resultSet, Class<T> outputClass)
	{
		try
		{
			List<T> outputList = new ArrayList<>();

			while (resultSet.next())
			{
				outputList.add(resultSet.getObject(1, outputClass));
			}

			return JValidator.isNullOrEmpty(outputList) ? null : outputList;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public static <T> List<T> getObjectList(ResultSet resultSet, Class<T> outputClass)
	{
		try
		{
			if (resultSet.getMetaData().getColumnCount() > 1)
			{
				return getMultiColumnListList(resultSet, outputClass);
			} else
			{
				return getSingleColumnList(resultSet, outputClass);
			}
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public static List<Row> getRowList(ResultSet resultSet)
	{
		try
		{
			List<Row> rowList = new ArrayList<>();

			final ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			while (resultSet.next())
			{
				List<in.jaxer.sdbms.Column> columns = new ArrayList<>();

				for (int i = 0; i < resultSetMetaData.getColumnCount(); i++)
				{
					int currentCoulmnCount = i + 1;

					in.jaxer.sdbms.Column column = new in.jaxer.sdbms.Column(
							currentCoulmnCount,//index
							resultSetMetaData.getColumnName(currentCoulmnCount),//name
							resultSet.getObject(currentCoulmnCount),//value
							resultSetMetaData.isAutoIncrement(currentCoulmnCount),//autoIncrement
							resultSetMetaData.isNullable(currentCoulmnCount) == 1//nullable
					);

					columns.add(column);
				}
				rowList.add(new Row(columns));
			}
			return rowList;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	@Deprecated
	public static List<Object[]> getObjectList(ResultSet resultSet)
	{
		try
		{
			List<Object[]> objectList = new ArrayList<>();

			final int columnCount = resultSet.getMetaData().getColumnCount();

			while (resultSet.next())
			{
				Object[] objectArray = new Object[columnCount];

				for (int i = 0; i < columnCount; i++)
				{
					objectArray[i] = resultSet.getObject(i + 1);
				}

				objectList.add(objectArray);
			}

			return objectList;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}
}
