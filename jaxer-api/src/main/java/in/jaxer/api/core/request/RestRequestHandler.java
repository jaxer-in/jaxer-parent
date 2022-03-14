
package in.jaxer.api.core.request;

import in.jaxer.api.annotations.RestTask;
import in.jaxer.api.constants.RequestConstant;
import in.jaxer.api.core.tasks.AbstractApiTask;
import in.jaxer.api.core.tasks.AbstractRestTask;
import in.jaxer.api.exceptions.ApiException;
import in.jaxer.api.listners.Authentication;
import in.jaxer.core.utilities.JValidator;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public class RestRequestHandler extends AbstractRequestHandler
{

	public RestRequestHandler(String basePackage, Class<? extends Annotation> taskClass)
	{
		super(basePackage, taskClass);
	}

	@Override
	public AbstractRestTask handleRequest(Connection connection, Authentication authentication) throws Exception
	{
		final String requestedApiTaskName = getRequestResponseDto().getTaskName();
		log.debug("requestedApiTaskName: {}", requestedApiTaskName);
		JValidator.requireNotEmpty(requestedApiTaskName, "Api task name cannot be empty");

		Class<? extends Annotation> clazz = getRequestedTask(requestedApiTaskName);
		JValidator.requireNotNull(clazz, "Request ApiTask [" + requestedApiTaskName + "] not found");

		RestTask apiTask = (RestTask) clazz.getAnnotation(RestTask.class);

		if (!apiTask.isPublicApi())
		{
			if (authentication == null)
			{
				throw new NullPointerException("Please implement [" + Authentication.class.getName() + "]");
			}
			authentication.doAuthentication(connection, getRequestResponseDto());
		}

		return (AbstractApiTask) clazz.newInstance();
	}

	@Override
	public void validateRequestParameters()
	{
//		if (getRequestResponseDto().getParameter(paramName) == null)
//		{
//			throw new ApiException(RequestConstant.JSON_BODY_PAYLOAD + " is missing");
//		}

		if (getRequestResponseDto().getParameter(RequestConstant.API_REQUEST_SOURCE) == null)
		{
			throw new ApiException(RequestConstant.API_REQUEST_SOURCE + " is missing");
		}

		if (getRequestResponseDto().getParameter(RequestConstant.API_TASK_NAME) == null)
		{
			throw new ApiException(RequestConstant.API_TASK_NAME + " is missing");
		}

		if (getRequestResponseDto().getParameter(RequestConstant.API_VERSION) == null)
		{
			throw new ApiException(RequestConstant.API_VERSION + " missing");
		}
	}
}
