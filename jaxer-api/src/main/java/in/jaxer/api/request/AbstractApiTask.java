
package in.jaxer.api.request;

import java.sql.Connection;

/**
 *
 * @author Shakir Ansari
 */
public abstract class AbstractApiTask extends AbstractTask
{

	public abstract void doApiTask(Connection connection) throws Exception;

	@Override
	public void doTask(Connection connection) throws Exception
	{
		doApiTask(connection);
	}
}
