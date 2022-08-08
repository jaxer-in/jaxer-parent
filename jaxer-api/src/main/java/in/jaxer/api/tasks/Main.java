package in.jaxer.api.tasks;

import in.jaxer.api.annotations.RestTask;
import in.jaxer.core.utilities.PackageScanner;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author Shakir
 * 		date 2021-12-04 - 18:49
 * @since 0.0.1
 */
@Log4j2
public class Main
{
	public static void main(String[] args) throws Exception
	{
		long start = 0l, end = 0l;

//		Set<Class> refClasses1 = PackageScanner.getClasses("in.jaxer.api", RestTask.class);

		start = System.currentTimeMillis();
		Set<Class<? extends Annotation>> refClasses = PackageScanner.getClasses("in.jaxer.api", RestTask.class);
		end = System.currentTimeMillis();

		log.info("Guava took: {} ms", end - start);

		start = System.currentTimeMillis();
		Set<Class> refClassesP = PackageScanner.findClasses("in.jaxer.api", RestTask.class);
		end = System.currentTimeMillis();

		log.info("PackageScanner took: {} ms", end - start);
	}
}
