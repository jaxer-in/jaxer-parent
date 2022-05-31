package in.jaxer.sdbms.exceptions;

/**
 * @author Shakir
 *
 */
public class ReadOnlyException extends JaxerSDBMSException
{
	public ReadOnlyException()
	{
		super();
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
