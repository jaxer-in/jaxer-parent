package in.jaxer.core.utilities;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * @author Shakir Ansari
 * @since 0.0.1
 */
public class JValidator
{
	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isBlank(String)}
	 */
	@Deprecated
	public static boolean isNullOrEmpty(String str)
	{
		return Strings.isNullOrEmpty(str);
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isNotBlank(String)}
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(String str)
	{
		return Strings.isNotNullAndNotEmpty(str);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isBlank(String string)
	{
		return string == null || string.trim().isEmpty();
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isNotBlank(String string)
	{
		return string != null && !string.trim().isEmpty();
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isBlank(Collection)}
	 */
	@Deprecated
	public static boolean isNullOrEmpty(Collection collection)
	{
		return collection == null || collection.isEmpty();
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isNotBlank(Collection)}
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(Collection collection)
	{
		return collection != null && !collection.isEmpty();
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isBlank(Collection collection)
	{
		return collection == null || collection.isEmpty();
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isNotBlank(Collection collection)
	{
		return collection != null && !collection.isEmpty();
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isBlank(Map)}
	 */
	@Deprecated
	public static boolean isNullOrEmpty(Map map)
	{
		return map == null || map.isEmpty();
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isNotBlank(Map)}
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(Map map)
	{
		return map != null && !map.isEmpty();
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isBlank(Map map)
	{
		return map == null || map.isEmpty();
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isNotBlank(Map map)
	{
		return map != null && !map.isEmpty();
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isBlank(Properties)}
	 */
	@Deprecated
	public static boolean isNullOrEmpty(Properties properties)
	{
		return properties == null || properties.isEmpty();
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isNotBlank(Properties)}
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(Properties properties)
	{
		return properties != null && !properties.isEmpty();
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isBlank(Properties properties)
	{
		return properties == null || properties.isEmpty();
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isNotBlank(Properties properties)
	{
		return properties != null && !properties.isEmpty();
	}

	/**
	 * if any String in given array is null or empty
	 * this method will return true otherwise false
	 *
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isBlank(String...)}
	 */
	@Deprecated
	public static boolean isNullOrEmpty(String... strings)
	{
		for (String string : strings)
		{
			if (isNullOrEmpty(string))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * If any String in given array is not null and not empty
	 * this method will return true otherwise false
	 *
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isNotBlank(String...)}
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(String... strings)
	{
		for (String string : strings)
		{
			if (isNotNullAndNotEmpty(string))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * if any String in given array is null or empty
	 * this method will return true otherwise false
	 *
	 * @since 1.0.6-beta
	 */
	public static boolean isBlank(String... strings)
	{
		for (String string : strings)
		{
			if (isBlank(string))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * If any String in given array is not null and not empty
	 * this method will return true otherwise false
	 *
	 * @since 1.0.9-beta
	 */
	public static boolean isNotBlank(String... strings)
	{
		for (String string : strings)
		{
			if (isNotBlank(string))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isBlank(char[])}
	 */
	@Deprecated
	public static boolean isNullOrEmpty(char[] array)
	{
		return array == null || array.length < 0;
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #isNotBlank(char[])}
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(char[] array)
	{
		return array != null && array.length > 0;
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isBlank(char[] array)
	{
		return array == null || array.length < 0;
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static boolean isNotBlank(char[] array)
	{
		return array != null && array.length > 0;
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(char[] array)
	{
		if (isBlank(array))
		{
			throw new NullPointerException();
		}
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #throwWhenBlank(String)}
	 */
	@Deprecated
	public static void throwWhenNullOrEmpty(String str)
	{
		if (JValidator.isNullOrEmpty(str))
		{
			throw new NullPointerException();
		}
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #throwWhenBlank(String, String)}
	 */
	@Deprecated
	public static void throwWhenNullOrEmpty(String str, String customExceptionMessage)
	{
		if (JValidator.isNullOrEmpty(str))
		{
			throw new NullPointerException(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #throwWhenBlank(String, Throwable)}
	 */
	@Deprecated
	public static void throwWhenNullOrEmpty(String str, Throwable throwable)
	{
		if (JValidator.isNullOrEmpty(str))
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(String str)
	{
		if (JValidator.isBlank(str))
		{
			throw new NullPointerException();
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(String str, String customExceptionMessage)
	{
		if (JValidator.isBlank(str))
		{
			throw new NullPointerException(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(String str, Throwable throwable)
	{
		if (JValidator.isBlank(str))
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #throwWhenBlank(Collection)}
	 */
	@Deprecated
	public static void throwWhenNullOrEmpty(Collection collection)
	{
		if (JValidator.isNullOrEmpty(collection))
		{
			throw new NullPointerException();
		}
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #throwWhenBlank(Collection, String)}
	 */
	@Deprecated
	public static void throwWhenNullOrEmpty(Collection collection, String customExceptionMessage)
	{
		if (JValidator.isNullOrEmpty(collection))
		{
			throw new NullPointerException(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta, replaced by {@link #throwWhenBlank(Collection, Throwable)}
	 */
	@Deprecated
	public static void throwWhenNullOrEmpty(Collection collection, Throwable throwable)
	{
		if (JValidator.isNullOrEmpty(collection))
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(Collection collection)
	{
		if (JValidator.isNullOrEmpty(collection))
		{
			throw new NullPointerException();
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(Collection collection, String customExceptionMessage)
	{
		if (JValidator.isNullOrEmpty(collection))
		{
			throw new NullPointerException(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(Collection collection, Throwable throwable)
	{
		if (JValidator.isNullOrEmpty(collection))
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(Map map)
	{
		if (JValidator.isNullOrEmpty(map))
		{
			throw new NullPointerException();
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(Map map, String customExceptionMessage)
	{
		if (JValidator.isNullOrEmpty(map))
		{
			throw new NullPointerException(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenBlank(Map map, Throwable throwable)
	{
		if (JValidator.isNullOrEmpty(map))
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.6-beta
	 */
	public static void throwWhenNull(Object object)
	{
		if (object == null)
		{
			throw new NullPointerException();
		}
	}

	/**
	 * @since 1.0.6-beta
	 */
	public static void throwWhenNull(Object object, String customExceptionMessage)
	{
		if (object == null)
		{
			throw new NullPointerException(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.6-beta
	 */
	public static void throwWhenNull(Object object, Throwable throwable)
	{
		if (object == null)
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenTrue(boolean trueCondition)
	{
		if (trueCondition)
		{
			throw new AssertionError();
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenTrue(boolean trueCondition, String customExceptionMessage)
	{
		if (trueCondition)
		{
			throw new AssertionError(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenTrue(boolean trueCondition, Throwable throwable)
	{
		if (trueCondition)
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenFalse(boolean trueCondition)
	{
		if (!trueCondition)
		{
			throw new AssertionError();
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenFalse(boolean trueCondition, String customExceptionMessage)
	{
		if (!trueCondition)
		{
			throw new AssertionError(customExceptionMessage);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void throwWhenFalse(boolean trueCondition, Throwable throwable)
	{
		if (!trueCondition)
		{
			rethrow(throwable);
		}
	}

	/**
	 * @since 1.0.9-beta
	 */
	public static void rethrow(Throwable throwable)
	{
		throwWhenNull(throwable);

		if (throwable instanceof RuntimeException)
		{
			throw (RuntimeException) throwable;
		}

		if (throwable instanceof Error)
		{
			throw (Error) throwable;
		}
	}

	/**
	 * @since 0.0.1
	 */
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

	/**
	 * @since 0.0.1
	 */
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

	/**
	 * @since 0.0.1
	 */
	public static boolean isNotEqualsToAny(String original, String... others)
	{
		return !isEqualsToAny(original, others);
	}

	/**
	 * @since 0.0.1
	 */
	public static boolean isNotEqualsIgnoreCaseToAny(String original, String... others)
	{
		return !isEqualsIgnoreCaseToAny(original, others);
	}
}
