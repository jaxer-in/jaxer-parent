package in.jaxer.api.exceptions;

import in.jaxer.api.constants.ApiStatus;
import lombok.Getter;

/**
 * @author Shakir Ansari
 */
@Getter
public class ApiException extends JaxerApiException
{
	private ApiStatus apiStatus;

	public ApiException()
	{
		super();
	}

	public ApiException(String message)
	{
		super(message);
	}

	public ApiException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ApiException(Throwable cause)
	{
		super(cause);
	}

	public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApiException(ApiStatus apiStatus)
	{
		super(apiStatus.toString());
		this.apiStatus = apiStatus;
	}
}
