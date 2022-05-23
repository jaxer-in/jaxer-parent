package in.jaxer.core.exceptions;

/**
 * @author Shakir
 * @Date 22 Dec, 2021 - 12:56:26 AM
 */
public class JaxerCoreException extends RuntimeException
{
	public JaxerCoreException()
	{
		super();
	}

	public JaxerCoreException(String message)
	{
		super(message);
	}

	public JaxerCoreException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JaxerCoreException(Throwable cause)
	{
		super(cause);
	}

	public JaxerCoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
