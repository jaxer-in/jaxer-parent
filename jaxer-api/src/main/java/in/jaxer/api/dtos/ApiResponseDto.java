package in.jaxer.api.dtos;

import in.jaxer.api.constants.ApiStatus;
import in.jaxer.api.exceptions.ApiException;
import in.jaxer.api.exceptions.UserException;
import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.Strings;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shakir
 * @since 0.0.1
 */
@Log4j2
@ToString
public class ApiResponseDto
{
	public Map<String, Object> taskResponseValue = null;

	public List<String> userMessageList = null;

	public ErrorDto errorDto = null;

	@SuppressWarnings("unused")
	public void addTaskResponseValue(String key, Object value)
	{
		JValidator.throwWhenBlank(key);
		JValidator.throwWhenNull(value);

		if (JValidator.isBlank(taskResponseValue))
		{
			taskResponseValue = new HashMap<>();
		}

		taskResponseValue.put(key, value);
	}

	public void addUserMessage(String msg)
	{
		JValidator.throwWhenBlank(msg);

		if (JValidator.isBlank(userMessageList))
		{
			userMessageList = new ArrayList<>();
		}

		userMessageList.add(msg);
	}

	public void addErrorDto(Exception exception)
	{
		JValidator.throwWhenNull(exception);

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
				errorDto.errorCode = apiStatus.getCode();
				errorDto.errorMessage = apiStatus.getMessage();
//				errorDto.httpStatus = apiStatus.getHttpStatus();
			} else
			{
				errorDto.errorMessage = apiException.getMessage();
			}
		}

		if (JValidator.isBlank(errorDto.errorMessage))
		{
			errorDto.errorMessage = exception.getMessage();

			if (JValidator.isBlank(errorDto.errorMessage))
			{
				errorDto.errorMessage = "Something went wrong";
			}
		}

		errorDto.stacktraceList = Strings.getListOfStackTraces(exception, null);

		if (JValidator.isNotBlank(errorDto.stacktraceList))
		{
			if (errorDto.stacktraceList.get(0).equalsIgnoreCase(errorDto.errorMessage))
			{
				errorDto.stacktraceList.remove(0);
			}
		}
	}
}
