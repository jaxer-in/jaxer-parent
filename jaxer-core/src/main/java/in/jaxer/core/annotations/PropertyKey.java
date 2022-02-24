
package in.jaxer.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Shakir Ansari
 */
@Target(value =
{
	ElementType.FIELD//, ElementType.METHOD
})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PropertyKey
{

	public String name();

	public String defaultValue() default "";

	public Class type() default String.class;

}
