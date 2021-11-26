
package in.jaxer.core.utilities;

import in.jaxer.core.exceptions.ValidationException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Shakir Ansari
 */
public class Validator
{

	public static boolean isEmpty(String string)
	{
		return string == null || string.trim().isEmpty();
	}

	public static boolean isNotEmpty(String string)
	{
		return !isEmpty(string);
	}

	/**
	 *
	 * @param strings
	 * @return true if all empty
	 */
	public static boolean isEmpty(String... strings)
	{
		for (String string : strings)
		{
			if (isNotEmpty(string))
			{
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(Collection collection)
	{
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection collection)
	{
		return !isEmpty(collection);
	}

	public static boolean isEmpty(Map map)
	{
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(Map map)
	{
		return !isEmpty(map);
	}

	public static boolean isEmpty(Properties properties)
	{
		return properties == null || properties.isEmpty();
	}

	public static boolean isNotEmpty(Properties properties)
	{
		return !isEmpty(properties);
	}

	public static boolean isAnyEmpty(String... strings)
	{
		for (String string : strings)
		{
			if (isEmpty(string))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isAnyNotEmpty(String... strings)
	{
		return !isAnyEmpty(strings);
	}

	public static boolean isEmpty(char[] array)
	{
		return array == null || array.length < 0;
	}

	public static void requireNotEmpty(char[] array)
	{
		if (isEmpty(array))
		{
			throw new ValidationException("Array cannot be null or empty");
		}
	}

	/**
	 *
	 * @param str
	 */
	public static void requireNotEmpty(String str)
	{
		requireNotEmpty(str, "String cannot be null or empty");
	}

	public static void requireNotEmpty(String str, String exceptionMessage)
	{
		if (isEmpty(str))
		{
			throw new ValidationException(exceptionMessage);
		}
	}

	public static void requireNotEmpty(String str, Throwable throwable)
	{
		if (isEmpty(str))
		{
			throw new RuntimeException(throwable);
		}
	}

	/**
	 *
	 * @param collection
	 */
	public static void requireNotEmpty(Collection collection)
	{
		requireNotEmpty(collection, "Collection cannot not be empty");
	}

	public static void requireNotEmpty(Collection collection, String exceptionMessage)
	{
		if (Validator.isEmpty(collection))
		{
			throw new ValidationException(exceptionMessage);
		}
	}

	public static void requireNotEmpty(Collection collection, Throwable throwable)
	{
		if (isEmpty(collection))
		{
			throw new RuntimeException(throwable);
		}
	}

	/**
	 *
	 * @param object
	 */
	public static void requireNotNull(Object object)
	{
		if (object == null)
		{
			throw new NullPointerException();
		}
	}

	public static void requireNotNull(Object object, String exceptionMessage)
	{
		if (object == null)
		{
			throw new NullPointerException(exceptionMessage);
		}
	}

	public static void requireNotNull(Object object, Throwable throwable)
	{
		if (object == null)
		{
			throw new RuntimeException(throwable);
		}
	}

	public static boolean isEqualsToAny(String original, String... others)
	{
		for (String string : others)
		{
			if (original.equals(string))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isNotEqualsToAny(String original, String... others)
	{
		return !isEqualsToAny(original, others);
	}

	public static boolean isEqualsIgnoreCaseToAny(String original, String... others)
	{
		for (String string : others)
		{
			if (original.equalsIgnoreCase(string))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isNotEqualsIgnoreCaseToAny(String original, String... others)
	{
		return !isEqualsIgnoreCaseToAny(original, others);
	}
}
