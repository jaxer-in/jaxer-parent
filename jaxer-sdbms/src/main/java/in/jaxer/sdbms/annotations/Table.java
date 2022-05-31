package in.jaxer.sdbms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Reference javax.persistence.Table
 *
 * @author Shakir
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Table
{
	String value();

	String catalog() default "";

	String schema() default "";

//	public UniqueConstraint[] uniqueConstraints() default {};
//	public Index[] indexes() default {};
}
