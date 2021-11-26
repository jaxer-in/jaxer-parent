
package in.jaxer.api.dtos;

import in.jaxer.api.constants.ApiStatus;
import in.jaxer.api.exceptions.ApiException;
import in.jaxer.api.exceptions.UserException;
import in.jaxer.core.utilities.Strings;
import in.jaxer.core.utilities.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shakir Ansari
 */
public class ApiResponseDto
{
//	private static final Logger logger = Logger.getLogger(ApiResponseCO.class);

	public Map<String, Object> taskResponseValue = null;

	public List<String> userMessageList = null;

	public ErrorDto errorDto = null;

	public void addTaskResponseValue(String key, Object value)
	{
		Validator.requireNotEmpty(key);
		Validator.requireNotNull(value);

		if (Validator.isEmpty(taskResponseValue))
		{
			taskResponseValue = new HashMap();
		}

		taskResponseValue.put(key, value);
	}

	public void addUserMessage(String msg)
	{
		Validator.requireNotEmpty(msg);

		if (Validator.isEmpty(userMessageList))
		{
			userMessageList = new ArrayList<>();
		}

		userMessageList.add(msg);
	}

	public void addErrorDto(Exception exception)
	{
		Validator.requireNotNull(exception);

		if (exception instanceof UserException)
		{
			addUserMessage(exception.getMessage());
			return;
		}

		if (errorDto == null)
		{
			errorDto = new ErrorDto();
		}

		if (exception instanceof ApiException)
		{
			ApiException apiException = (ApiException) exception;
			ApiStatus apiStatus = apiException.getApiStatus();

			if (apiStatus != null)
			{
				errorDto.errorMessage = apiStatus.getMessage();
//				errorDto.httpStatus = apiStatus.getHttpStatus();
				errorDto.errorCode = apiStatus.getCode();
			}
		} else
		{
			errorDto.errorMessage = "Something went wrong";
		}

		errorDto.stacktraceHashMap = Strings.getStackTraces(exception, null);
	}
}
