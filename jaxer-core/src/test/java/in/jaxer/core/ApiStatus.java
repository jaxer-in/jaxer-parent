package in.jaxer.core;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD, CONSTRUCTOR, FIELD, PACKAGE})
@Retention(RUNTIME)
@Documented
public @interface ApiStatus
{
	String since() default "";

	Status status() default Status.EXPERIMENTAL;

	String statusChangedSince() default "";

	/**
	 * Indicates the status of an API element and therefore its level of
	 * stability as well.
	 */
	enum Status
	{
		DEPRECATED,
		EXPERIMENTAL,
		STABLE
	}
}
