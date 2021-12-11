
package in.jaxer.sdbms.exceptions;

/**
 *
 * @author Shakir Ansari
 */
public class ColumnNotFoundException extends RuntimeException
{

	public ColumnNotFoundException()
	{
		super();
	}

	public ColumnNotFoundException(String message)
	{
		super(message);
	}

	public ColumnNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ColumnNotFoundException(Throwable cause)
	{
		super(cause);
	}
}