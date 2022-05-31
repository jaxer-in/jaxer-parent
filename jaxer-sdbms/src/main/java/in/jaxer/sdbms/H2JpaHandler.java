package in.jaxer.sdbms;

import in.jaxer.sdbms.utils.AbstractJpaHandler;

import java.sql.Connection;
import java.util.List;

/**
 * @author Shakir
 * date 2022-02-23 21:05
 */
public class H2JpaHandler extends AbstractJpaHandler
{
	@Override
	public <T> T find(Connection connection, Class<T> outputClass, List<Parameter> parameterList)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> List<T> findList(Connection connection, Class<T> outputClass, List<Parameter> parameterList)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int delete(Connection connection, Class outputClass, Object id)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T merge(Connection connection, T t)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T persist(Connection connection, T t)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public long count(Connection connection, Class<?> outputClass, List<Parameter> parameterList)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected String getWhereClause(List<Parameter> parameterList)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
