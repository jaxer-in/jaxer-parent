package in.jaxer.api.core.request;

import in.jaxer.api.constants.RequestConstant;
import in.jaxer.api.core.tasks.AbstractRestTask;
import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.api.listners.Authentication;
import in.jaxer.core.constants.Singletons;
import in.jaxer.core.net.Servlets;
import in.jaxer.core.utilities.Collections;
import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.PackageScanner;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Shakir
 * Date 21 Dec, 2021 - 9:11:23 PM
 */
@Log4j2
public abstract class AbstractRequestHandler
{

	abstract public AbstractRestTask handleRequest(Connection connection, Authentication authentication) throws Exception;

	abstract public void validateRequestParameters();

	private RequestResponseDto requestResponseDto = null;

	private HashMap<String, Object> requestMap = null;

	private Set<Class> taskList = null;

	private boolean isMultipartRequest = false;

	private final String basePackage;

	private final Class<? extends Annotation> taskClass;

	public AbstractRequestHandler(String basePackage, Class<? extends Annotation> taskClass)
	{
		this.basePackage = basePackage;
		this.taskClass = taskClass;
	}

	private void init(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		this.isMultipartRequest = Servlets.isMultipartRequest(request);
		log.info("isMultipartRequest: {}", isMultipartRequest);

		if (Collections.isEmpty(taskList))
		{
			log.debug("initializing taskList");

			taskList = PackageScanner.getClasses(basePackage, this.taskClass);

			log.debug("taskList: {}", taskList);
		}

		requestMap = new HashMap<>();

		if (!isMultipartRequest)
		{
			String requestBody = Servlets.getRequestBody(request);
			log.debug("requestBody: {}", requestBody);

			if (JValidator.isNotNullAndNotEmpty(requestBody))
			{
				requestMap = Singletons.getGson().fromJson(requestBody, HashMap.class);
			}
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

	public ApiResponseDto processRequest(Connection connection, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception
	{
		init(request, response);

		validateRequestParameters();

		AbstractRestTask abstractHttpRequestTask = handleRequest(connection, authentication);

		processTask(abstractHttpRequestTask, connection);

		getRequestResponseDto().setParameter(RequestConstant.MESSAGE, RequestConstant.SUCCESS);

		return getRequestResponseDto().getApiResponseDto();
	}

	private void processTask(AbstractRestTask abstractHttpRequestTask, Connection connection) throws Exception
	{
		abstractHttpRequestTask.setRequestResponseDto(getRequestResponseDto());
		abstractHttpRequestTask.processAbstractTask(connection);
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
}
