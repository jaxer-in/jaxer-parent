package in.jaxer.sdbms;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.sdbms.annotations.PrimaryKey;
import in.jaxer.sdbms.annotations.Table;
import in.jaxer.sdbms.exceptions.JaxerSDBMSException;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>  - entity or model class
 * @param <ID> - type of primary key
 *
 * @author Shakir
 */
@Log4j2
public abstract class Repository<T, ID>
{
	public Connection getConnection()
	{
		String msg = System.lineSeparator()
				+ " Please Override getConnection() method"
				+ " from " + (Repository.class.getName())
				+ ", to configure " + Connection.class.getName();

		throw new JaxerSDBMSException(msg);
	}

	public abstract T persist(Connection connection, T t);

	public abstract int merge(Connection connection, T t);

	public abstract long count(Connection connection);

	public abstract int delete(Connection connection, T t);

	public T persist(T t)
	{
		try (Connection connection = getConnection())
		{
			connection.setAutoCommit(false);

			t = persist(connection, t);

			connection.commit();

			return t;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public List<T> persist(Connection connection, List<T> tList)
	{
		for (T t : tList)
		{
			t = persist(connection, t);
		}
		return tList;
	}

	public List<T> persist(List<T> tList)
	{
		try (Connection connection = getConnection())
		{
			connection.setAutoCommit(false);

			persist(connection, tList);

			connection.commit();
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
		return tList;
	}

	public T find(Connection connection, Class<T> outputClass, ID id)
	{
		log.debug("id: {}", id);
		log.debug("outputClass: {}", outputClass);

		if (!outputClass.isAnnotationPresent(Table.class))
		{
			throw new JaxerSDBMSException("OutputClass should be decorated by @" + Table.class.getName());
		}

		String tableName;

		Table table = outputClass.getAnnotation(Table.class);
		tableName = table.value();
		log.debug("tableName: {}", tableName);
		JValidator.throwWhenNullOrEmpty(tableName, "Table name not found in output class");

		String primaryKeyName = null;
		Field[] fields = outputClass.getDeclaredFields();

		for (Field field : fields)
		{
			if (field.isAnnotationPresent(in.jaxer.sdbms.annotations.Column.class)
					&& field.isAnnotationPresent(PrimaryKey.class))
			{
				primaryKeyName = field.getAnnotation(in.jaxer.sdbms.annotations.Column.class).value();
				break;
			}
		}

		log.debug("primaryKeyName: {}", primaryKeyName);
		JValidator.throwWhenNullOrEmpty(primaryKeyName, "Annotation @" + PrimaryKey.class.getName() + " not found in " + outputClass.getName() + " class");

		String sql = "SELECT * FROM `" + tableName + "` WHERE `" + primaryKeyName + "` = :pKey";
		log.debug("sql: {}", sql);

		try (NamedStatement namedStatement = new NamedStatement(connection, sql))
		{
			namedStatement.setParameter("pKey", id);
			try (ResultSet resultSet = namedStatement.executeQuery())
			{
//				T bean = outputClass.newInstance();
				T bean;

				List<T> objectList = ResultsetMapper.getObjectList(resultSet, outputClass);
				bean = JValidator.isNullOrEmpty(objectList) ? null : objectList.get(0);
				return bean;
			}
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public T find(Class<T> outputClass, ID id)
	{
		try (Connection connection = getConnection())
		{
			return find(connection, outputClass, id);
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public List<T> find(Connection connection, Class<T> outputClass, List<ID> idList)
	{
		List<T> tList = null;

		for (ID id : idList)
		{
			T t = find(connection, outputClass, id);
			if (t != null)
			{
				if (JValidator.isNullOrEmpty(tList))
				{
					tList = new ArrayList<>();
				}
				tList.add(t);
			}
		}

		return tList;
	}

	public List<T> find(Class<T> outputClass, List<ID> idList)
	{
		try (Connection connection = getConnection())
		{
			return find(connection, outputClass, idList);
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public int merge(T t)
	{
		try (Connection connection = getConnection())
		{
			int response = merge(connection, t);
			connection.commit();
			return response;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public long count()
	{
		try (Connection connection = getConnection())
		{
			return count(connection);
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public int delete(T t)
	{
		try (Connection connection = getConnection())
		{
			int response = delete(connection, t);

			connection.commit();

			return response;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}

	public int delete(Connection connection, List<T> listOfT)
	{
		int affectedRows = 0;

		for (T t : listOfT)
		{
			affectedRows += delete(connection, t);
		}

		return affectedRows;
	}

	public int delete(List<T> listOfT)
	{
		try (Connection connection = getConnection())
		{
			int response = delete(connection, listOfT);
			connection.commit();
			return response;
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerSDBMSException(exception);
		}
	}
}
