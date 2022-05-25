package in.jaxer.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Shakir Ansari
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RestTask
{

	boolean isPublicApi() default false;

	long clientCachingTime() default 0l;

	long serverCachingTime() default 0l;

	boolean isDeprecated() default false;

	/**
	 * Only specific role will be allowed to access this task
	 * empty means anyone
	 *
	 * @return
	 */
	String[] allowedRole() default
			{
			};
}
