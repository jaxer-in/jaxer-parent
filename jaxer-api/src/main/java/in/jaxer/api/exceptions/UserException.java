package in.jaxer.api.exceptions;

import in.jaxer.api.constants.ApiStatus;

/**
 * @author Shakir
 */
public class UserException extends JaxerApiException
{
	public UserException()
	{
		super();
	}

	public UserException(String message)
	{
		super(message);
	}

	public UserException(ApiStatus apiStatus)
	{
		super(apiStatus.toString());
	}

	public UserException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UserException(Throwable cause)
	{
		super(cause);
	}

	public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
