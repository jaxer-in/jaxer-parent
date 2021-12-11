
package in.jaxer.sdbms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Shakir Ansari
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PrimaryKey
{
	/**
	 * When enabled an auto generated 32bit long random UUID will be inserted
	 * @return
	 */
	public boolean uuidValue() default false;
}
