package in.jaxer.sdbms;

import java.sql.Connection;
import java.util.List;

public interface Session extends Connection
{
	<T> T find(Class<T> outputClass, Object primaryId);

	<T> List<T> find(Class<T> outputClass, Object... primaryId);

	<T> T find(Class<T> outputClass, List<Parameter> parameterList);

	<T> List<T> findList(Class<T> outputClass, List<Parameter> parameterList);

	<T> T merge(T t);

	<T> List<T> merge(T... t);

	<T> T delete(T t);

	<T> List<T> delete(T... t);

	<T> T persist(T t);

	<T> List<T> persist(T... t);

	long count(Class<?> outputClass, List<Parameter> parameterList);
}
