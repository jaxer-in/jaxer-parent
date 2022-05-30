package in.jaxer.api.core.request;

import in.jaxer.api.annotations.MultipartTask;
import in.jaxer.api.constants.RequestConstant;
import in.jaxer.api.core.tasks.AbstractMultipartTask;
import in.jaxer.api.core.tasks.AbstractTask;
import in.jaxer.api.exceptions.ApiException;
import in.jaxer.api.listners.Authentication;
import in.jaxer.core.utilities.JValidator;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.sql.Connection;

/**
 * @author Shakir
 * Date 21 Dec, 2021 - 9:23:46 PM
 */
@Log4j2
public class MultipartRequestHandler extends AbstractRequestHandler
{

	public MultipartRequestHandler(String basePackage, Class<? extends Annotation> taskClass)
	{
		super(basePackage, taskClass);
	}

	@Override
	public AbstractTask handleRequest(Connection connection, Authentication authentication) throws Exception
	{
		final String requestedMultipartTaskName = getRequestResponseDto().getTaskName();
		log.debug("requestedMultipartTaskName: {}", requestedMultipartTaskName);
		JValidator.throwWhenNullOrEmpty(requestedMultipartTaskName, "Multipart task name cannot be empty");

		Class<? extends Annotation> clazz = getRequestedTask(requestedMultipartTaskName);
		JValidator.throwWhenNull(clazz, "Request Multipart task [" + requestedMultipartTaskName + "] not found");

		MultipartTask multipartTask = clazz.getAnnotation(MultipartTask.class);

		if (!multipartTask.isPublicTask())
		{
			if (authentication == null)
			{
				throw new NullPointerException("Please implement [" + Authentication.class.getName() + "]");
			}
			authentication.doAuthentication(connection, getRequestResponseDto());
		}

		return (AbstractMultipartTask) clazz.newInstance();
	}

	@Override
	public void validateRequestParameters()
	{
		if (getRequestResponseDto().getParameter(RequestConstant.API_ACCESS_TOKEN) == null)
		{
			throw new ApiException(RequestConstant.API_ACCESS_TOKEN + " is missing");
		}

		if (getRequestResponseDto().getParameter(RequestConstant.API_REQUEST_SOURCE) == null)
		{
			throw new ApiException(RequestConstant.API_REQUEST_SOURCE + " is missing");
		}

		if (getRequestResponseDto().getParameter(RequestConstant.MULTIPART_TASK_NAME) == null)
		{
			throw new ApiException(RequestConstant.MULTIPART_TASK_NAME + " is missing");
		}

		if (getRequestResponseDto().getParameter(RequestConstant.API_VERSION) == null)
		{
			throw new ApiException(RequestConstant.API_VERSION + " missing");
		}
	}
}
