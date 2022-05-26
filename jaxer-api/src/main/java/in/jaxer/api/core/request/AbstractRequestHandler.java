package in.jaxer.api.core.request;

import in.jaxer.api.constants.RequestConstant;
import in.jaxer.api.core.tasks.AbstractTask;
import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.api.listners.Authentication;
import in.jaxer.core.net.Servlets;
import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.JsonHandler;
import in.jaxer.core.utilities.PackageScanner;
import lombok.Getter;
import lombok.Setter;
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
	@Getter
	@Setter
	private RequestResponseDto requestResponseDto = null;

	@Getter
	@Setter
	private boolean isMultipartRequest = false;

	private HashMap<String, Object> requestMap = null;
	private Set<Class> taskList = null;
	private final String basePackage;
	private final Class<? extends Annotation> taskClass;

	abstract public AbstractTask handleRequest(Connection connection, Authentication authentication) throws Exception;

	abstract public void validateRequestParameters();

	public AbstractRequestHandler(String basePackage, Class<? extends Annotation> taskClass)
	{
		this.basePackage = basePackage;
		this.taskClass = taskClass;
	}

	private void init(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		this.isMultipartRequest = Servlets.isMultipartRequest(request);
		log.debug("isMultipartRequest: {}, taskList{}", isMultipartRequest, taskList);

		if (JValidator.isNullOrEmpty(taskList))
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
				requestMap = JsonHandler.getGson().fromJson(requestBody, HashMap.class);
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

		AbstractTask abstractHttpRequestTask = handleRequest(connection, authentication);

		processTask(abstractHttpRequestTask, connection);

		getRequestResponseDto().setParameter(RequestConstant.MESSAGE, RequestConstant.SUCCESS);

		return getRequestResponseDto().getApiResponseDto();
	}

	private void processTask(AbstractTask abstractHttpRequestTask, Connection connection) throws Exception
	{
		abstractHttpRequestTask.setRequestResponseDto(getRequestResponseDto());
		abstractHttpRequestTask.processAbstractTask(connection);
	}

	protected void preRequest() {}

	protected void postRequest() {}
}
