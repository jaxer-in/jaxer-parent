package in.jaxer.api.dtos;

import in.jaxer.api.constants.RequestConstant;
import in.jaxer.core.utilities.JValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author Shakir
 * @since 0.0.1
 */
public class RequestResponseDto
{
	private final HashMap<String, Object> requestMap;
	private HashMap<String, Object> temporaryObjects = null;
	private ApiResponseDto apiResponseDto;
	private final HttpServletRequest httpServletRequest;
	private final HttpServletResponse httpServletResponse;
	private final boolean isMultipartRequest;

	public RequestResponseDto(HashMap<String, Object> requestMap, HttpServletRequest request, HttpServletResponse response, boolean isMultipartRequest)
	{
		this.requestMap = requestMap;

		this.httpServletRequest = request;
		this.httpServletResponse = response;

		this.apiResponseDto = new ApiResponseDto();

		this.isMultipartRequest = isMultipartRequest;
	}

	public void setTemporaryObject(String key, Object value)
	{
		if (JValidator.isBlank(key) || value == null)
		{
			return;
		}

		if (JValidator.isBlank(temporaryObjects))
		{
			temporaryObjects = new HashMap<>();
		}

//		temporaryObjects.put(key, Singletons.getGson(false).toJson(value));
		temporaryObjects.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getTemporaryObject(String key, Class<T> T)
	{
		if (JValidator.isBlank(key) || T == null)
		{
			return null;
		}

		Object raw = temporaryObjects.get(key);

		return (T) raw;
//		System.out.println("RequestResponseDto.getTemporaryObject() - raw: [" + raw + "]");
//		if (JValidator.isEmpty(raw))
//		{
//			return null;
//		}
//
//		return Singletons.getGson(false).fromJson(raw, T);
	}

	private Object getRequestParameter(String paramName)
	{
		return (isMultipartRequest ? httpServletRequest.getParameter(paramName) : requestMap.get(paramName));
	}

	public Object getRequestObject(String paramName)
	{
		return getRequestParameter(paramName);
	}

	public String getParameter(String paramName)
	{
		return (String) getRequestParameter(paramName);
	}

	public String getTaskName()
	{
		return getParameter(isMultipartRequest ? RequestConstant.MULTIPART_TASK_NAME : RequestConstant.API_TASK_NAME);
	}

	public String getAccessToken()
	{
		return getParameter(RequestConstant.API_ACCESS_TOKEN);
	}

	public String getRequestSource()
	{
		return getParameter(RequestConstant.API_REQUEST_SOURCE);
	}

	public void setParameter(String paramName, Object object)
	{
		if (apiResponseDto == null)
		{
			apiResponseDto = new ApiResponseDto();
		}

		if (apiResponseDto.taskResponseValue == null)
		{
			apiResponseDto.taskResponseValue = new HashMap<>();
		}

		apiResponseDto.taskResponseValue.put(paramName, object);
	}

	public void addUserMessage(String userMessage)
	{
		apiResponseDto.addUserMessage(userMessage);
	}

	public HttpServletRequest getHttpServletRequest()
	{
		return httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse()
	{
		return httpServletResponse;
	}

	public boolean isIsMultipartRequest()
	{
		return isMultipartRequest;
	}

	public ApiResponseDto getApiResponseDto()
	{
		return apiResponseDto;
	}

	public void setApiResponseDto(ApiResponseDto apiResponseDto)
	{
		this.apiResponseDto = apiResponseDto;
	}
}
