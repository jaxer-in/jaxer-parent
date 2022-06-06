package in.jaxer.api.core.tasks;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;

/**
 * @author Shakir
 * @since 0.0.1
 */
@Log4j2
public abstract class AbstractApiTask extends AbstractTask
{
	public abstract void doApiTask(Connection connection) throws Exception;

	@Override
	public void doTask(Connection connection) throws Exception
	{
		doApiTask(connection);
	}
}
