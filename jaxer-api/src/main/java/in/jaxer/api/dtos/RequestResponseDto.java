
package in.jaxer.api.dtos;

import in.jaxer.api.constants.ApiRequestConstants;
import in.jaxer.core.utilities.Validator;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shakir Ansari
 */
public class RequestResponseDto
{

	private HashMap<String, Object> requestMap = null;

	private HashMap<String, Object> temporaryObjects = null;

	private ApiResponseDto apiResponseDto = null;

	private HttpServletRequest httpServletRequest = null;

	private HttpServletResponse httpServletResponse = null;

	private boolean isMultipartRequest = false;

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
		if (Validator.isEmpty(key) || value == null)
		{
			return;
		}

		if (Validator.isEmpty(temporaryObjects))
		{
			temporaryObjects = new HashMap<>();
		}

//		temporaryObjects.put(key, Singletons.getGson(false).toJson(value));
		temporaryObjects.put(key, value);
	}

	public <T> T getTemporaryObject(String key, Class<T> T)
	{
		if (Validator.isEmpty(key) || T == null)
		{
			return null;
		}

		Object raw = temporaryObjects.get(key);

		return (T) raw;
//		System.out.println("RequestResponseDto.getTemporaryObject() - raw: [" + raw + "]");
//		if (Validator.isEmpty(raw))
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
		return getParameter(isMultipartRequest ? ApiRequestConstants.API_MULTIPART_TASK_NAME : ApiRequestConstants.API_TASK_NAME);
	}

	public String getAccessToken()
	{
		return getParameter(ApiRequestConstants.API_ACCESS_TOKEN);
	}

	public String getRequestSource()
	{
		return getParameter(ApiRequestConstants.API_REQUEST_SOURCE);
	}

	public void setParameter(String paramName, Object object)
	{
		if (apiResponseDto == null)
		{
			apiResponseDto = new ApiResponseDto();
		}

		if (apiResponseDto.taskResponseValue == null)
		{
			apiResponseDto.taskResponseValue = new HashMap();
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
