
package in.jaxer.core.utilities;

import in.jaxer.core.interfaces.Entity;
import java.lang.reflect.Field;

/**
 *
 * @author Shakir Ansari
 */
public class DtoMapper
{

	public static <T> T getDto(Entity entity, Class<T> coClass)
	{
		Field[] fields = coClass.getDeclaredFields();
		for (Field field : fields)
		{
		}
		return null;
	}

	public static void main(String[] args)
	{
	}
}
