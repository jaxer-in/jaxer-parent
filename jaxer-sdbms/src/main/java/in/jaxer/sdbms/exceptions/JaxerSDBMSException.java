package in.jaxer.sdbms.exceptions;

import in.jaxer.core.exceptions.JaxerCoreException;
import lombok.Getter;

/**
 * @author Shakir
 * date 2021-10-25 23:28
 */
@Getter
public class JaxerSDBMSException extends JaxerCoreException
{
	public JaxerSDBMSException()
	{
		super();
	}

	public JaxerSDBMSException(String message)
	{
		super(message);
	}

	public JaxerSDBMSException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JaxerSDBMSException(Throwable cause)
	{
		super(cause);
	}

	public JaxerSDBMSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
