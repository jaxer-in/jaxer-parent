package in.jaxer.api.tasks.api;

import in.jaxer.api.annotations.RestTask;
import in.jaxer.api.core.tasks.AbstractApiTask;
import in.jaxer.core.utilities.Strings;
import in.jaxer.core.utilities.Time;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.util.Date;

/**
 * @author Shakir Ansari
 */
@Log4j2
@RestTask(isPublicApi = true)
public class Test extends AbstractApiTask
{
	@Override
	public void doApiTask(Connection connection)
	{
		log.info("Rest task invoked at {}", new Date());

		setParameter("hello", "world");
		setParameter("key", Strings.getUUID());
		setParameter("date", Time.formatMySqlDateTime());
	}
}
