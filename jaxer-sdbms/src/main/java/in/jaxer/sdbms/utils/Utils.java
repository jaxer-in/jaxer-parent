package in.jaxer.sdbms.utils;

import in.jaxer.sdbms.annotations.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir Ansari
 */
public class Utils
{

	public static List<Field> getColumnAnnotedFields(Class outputClass)
	{
		List<Field> fieldList = new ArrayList<>();

		Field[] fields = outputClass.getDeclaredFields();
		for (Field field : fields)
		{
			if (field.isAnnotationPresent(Column.class))
			{
				fieldList.add(field);
			}
		}
		return fieldList;
	}

}
