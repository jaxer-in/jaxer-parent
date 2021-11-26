
package in.jaxer.sdbms.exceptions;

/**
 *
 * @author Shakir Ansari
 */
public class ReadOnlyException extends RuntimeException
{

	public ReadOnlyException()
	{
	}

	public ReadOnlyException(String msg)
	{
		super(msg);
	}

	public ReadOnlyException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ReadOnlyException(Throwable cause)
	{
		super(cause);
	}
}
