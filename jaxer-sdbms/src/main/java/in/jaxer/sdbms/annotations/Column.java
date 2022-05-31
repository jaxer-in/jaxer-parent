package in.jaxer.sdbms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Reference : javax.persistence.Column
 *
 * @author Shakir
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Column
{
	String value();

	boolean nullable() default true;

//	public boolean primaryKey() default false;
//	public boolean autoIncrement() default false;
//	public boolean unique() default false;
//	public boolean insertable() default true;
//	public boolean updatable() default true;
//	public String columnDefinition() default "";
//	public String table() default "";
//	public int length() default 255;
//	public int precision() default 0;
//	public int scale() default 0;
}
