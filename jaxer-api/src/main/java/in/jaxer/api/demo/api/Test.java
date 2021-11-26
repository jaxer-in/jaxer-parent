
package in.jaxer.api.demo.api;

import in.jaxer.api.annotations.ApiTask;
import in.jaxer.api.request.AbstractApiTask;
import in.jaxer.core.utilities.Strings;
import in.jaxer.core.utilities.Time;
import java.sql.Connection;

/**
 *
 * @author Shakir Ansari
 */
@ApiTask(isPublicApi = true)
public class Test extends AbstractApiTask
{
//	private static final Logger logger = Logger.getLogger(Test.class);

	@Override
	public void doApiTask(Connection connection)
	{
//		logger.fatal(Test.class.getSimpleName() + " Api invoked at " + Test.class.getName());

		setParameter("hello", "world");
		setParameter("key", Strings.getUUID());
		setParameter("date", Time.formatMySqlDateTime());
	}
}
