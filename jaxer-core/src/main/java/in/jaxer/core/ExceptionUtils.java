package in.jaxer.core;

import in.jaxer.core.utilities.JValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir
 * @since v1.0.9-beta [2022-08-08]
 */
public class ExceptionUtils
{
	/**
	 * @since v1.0.9-beta
	 */
	public static void rethrow(Throwable throwable)
	{
		if (throwable == null)
		{
			throw new NullPointerException();
		}

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
	 * @since v1.0.9-beta
	 */
	public static List<String> getStackTraces(Throwable throwable, String packageFilter)
	{
		if (throwable == null)
		{
			return null;
		}

		List<String> traces = new ArrayList<>();

		if (JValidator.isNotBlank(throwable.getMessage()))
		{
			traces.add(throwable.getMessage());
		}

		StackTraceElement[] stackTraceElements = throwable.getStackTrace();
		//noinspection ConfusingArgumentToVarargsMethod
		if (JValidator.isNotBlank(stackTraceElements))
		{
			for (StackTraceElement stackTraceElement : stackTraceElements)
			{
				if (JValidator.isBlank(packageFilter))
				{
					traces.add(stackTraceElement.toString());
				} else
				{
					if (stackTraceElement.toString().startsWith(packageFilter))
					{
						traces.add(stackTraceElement.toString());
					}
				}
			}
		}

		return traces;
	}

	/**
	 * @since v1.0.9-beta
	 */
	public static List<String> getStackTraces(Throwable throwable)
	{
		return getStackTraces(throwable, null);
	}
}
