package in.jaxer.sdbms.exceptions;

/**
 * @author Shakir
 */
public class ColumnNotFoundException extends JaxerSDBMSException
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
