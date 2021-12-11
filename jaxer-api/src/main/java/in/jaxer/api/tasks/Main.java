
package in.jaxer.api.tasks;

import in.jaxer.api.annotations.ApiTask;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

/**
 *
 * @author Shakir
 * Date 4 Dec, 2021 - 6:49:57 PM
 */
@Log4j2
public class Main
{

	public static void main(String[] args) throws Exception
	{
		long start = 0l, end = 0l;

		start = System.currentTimeMillis();
		Set<Class<?>> refClasses = new Reflections("in.jaxer.api").getTypesAnnotatedWith(ApiTask.class);
		end = System.currentTimeMillis();

		log.info("Reflections took: {} ms", end - start);
	}
}
