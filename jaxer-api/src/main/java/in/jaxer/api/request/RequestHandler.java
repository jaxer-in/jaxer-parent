
package in.jaxer.api.request;

import in.jaxer.api.annotations.ApiTask;
import in.jaxer.api.constants.RequestConstant;
import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.api.exceptions.ApiException;
import in.jaxer.api.listners.Authentication;
import in.jaxer.core.constants.Singletons;
import in.jaxer.core.net.Servlets;
import in.jaxer.core.utilities.Collections;
import in.jaxer.core.utilities.PackageScanner;
import in.jaxer.core.utilities.Validator;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public class RequestHandler // extends AbstractRequestHandler
{

	private RequestResponseDto requestResponseDto = null;

	private HashMap<String, Object> requestMap = null;

	private static List<Class> taskList = null;

	private boolean isMultipartRequest = false;

	public RequestHandler(String basePackage) throws Exception
	{
		if (Collections.isEmpty(taskList))
		{
			log.debug("initializing taskList");
			taskList = PackageScanner.findClasses(basePackage, ApiTask.class);
		}
	}

	private void init(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		this.isMultipartRequest = Servlets.isMultipartRequest(request);

		requestMap = new HashMap<>();

		if (!isMultipartRequest)
		{
			String requestBody = Servlets.getRequestBody(request);
			log.debug("requestBody: {}", requestBody);
			requestMap = Singletons.getGson(false).fromJson(requestBody, HashMap.class);
		}

		requestResponseDto = new RequestResponseDto(requestMap, request, response, isMultipartRequest);
	}

	protected Class<? extends Annotation> getRequestedTask(String requestedTaskName)
	{
		for (Class clazz : taskList)
		{
			if (requestedTaskName.equals(clazz.getSimpleName()))
			{
				return clazz;
			}
		}
		return null;
	}

	protected void preRequest()
	{
	}

	protected void postRequest()
	{
	}

	public RequestResponseDto getRequestResponseDto()
	{
		return requestResponseDto;
	}

	public void setRequestResponseDto(RequestResponseDto requestResponseDto)
	{
		this.requestResponseDto = requestResponseDto;
	}

	public ApiResponseDto doHandleRequest(Connection connection, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception
	{
		init(request, response);

		validateApiParams();

		final String requestedApiTaskName = getRequestResponseDto().getTaskName();
//		logger.info("requestedApiTaskName: [" + requestedApiTaskName + "]");
		Validator.requireNotEmpty(requestedApiTaskName, "Api task name cannot be empty");

		Class<? extends Annotation> clazz = getRequestedTask(requestedApiTaskName);
		Validator.requireNotNull(clazz, "Request ApiTask [" + getRequestResponseDto().getTaskName() + "] not found");

		ApiTask apiTask = (ApiTask) clazz.getAnnotation(ApiTask.class);

		if (!apiTask.isPublicApi()
				&& authentication != null)
		{
			authentication.doAuthentication(connection, requestResponseDto);
		}

		processApiTask((AbstractApiTask) clazz.newInstance(), connection);

		getRequestResponseDto().setParameter(RequestConstant.MESSAGE, RequestConstant.SUCCESS);

		return getRequestResponseDto().getApiResponseDto();
	}

	private void processApiTask(AbstractApiTask abstractApiTask, Connection connection) throws Exception
	{
		abstractApiTask.setRequestResponseDto(getRequestResponseDto());
		abstractApiTask.processAbstractTask(connection);
	}

	private void validateApiParams()
	{
		if (requestMap.get(RequestConstant.API_REQUEST_SOURCE) == null)
		{
			throw new ApiException("API_REQUEST_SOURCE is missing");
		}

		if (requestMap.get(RequestConstant.API_TASK_NAME) == null)
		{
			throw new ApiException("API_TASK_NAME is missing");
		}

		if (requestMap.get(RequestConstant.API_VERSION) == null)
		{
			throw new ApiException("API_VERSION is missing");
		}
	}
}
