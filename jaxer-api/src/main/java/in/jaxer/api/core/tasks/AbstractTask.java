package in.jaxer.api.core.tasks;

import in.jaxer.api.constants.RequestConstant;
import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.JsonHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Shakir
 * @since 0.0.1
 */
@Log4j2
public abstract class AbstractTask
{
	@Getter
	@Setter
	private RequestResponseDto requestResponseDto = null;

	public abstract void doTask(Connection connection) throws Exception;

	public void processAbstractTask(Connection connection) throws Exception
	{
		doBeforeTask(connection);

		doTask(connection);

		doAfterTask(connection);

		addBgTask(connection);
	}

	protected void doBeforeTask(Connection connection) throws Exception
	{
	}

	protected void doAfterTask(Connection connection) throws Exception
	{
	}

	protected void addBgTask(Connection connection) throws Exception
	{
	}

	public HttpServletRequest getHttpServletRequest()
	{
		return getRequestResponseDto().getHttpServletRequest();
	}

	public String getParameter(String paramName)
	{
		return getRequestResponseDto().getParameter(paramName);
	}

	public <T> T getParameter(String paramName, Class<T> T)
	{
		return JsonHandler.toObject(getParameter(paramName), T);
	}

	public int getParameterAsInt(String paramName)
	{
		if (getParameter(paramName) == null)
		{
			return 0;
		}
		return getParameter(paramName, Integer.class);
	}

	public <T> List<T> getParameterList(String paramName, Class<T> T)
	{
		return JsonHandler.toObjectList(getParameter(paramName), T);
	}

	public void setTemporaryObject(String key, Object value)
	{
		getRequestResponseDto().setTemporaryObject(key, value);
	}

	public <T> T getTemporaryObject(String key, Class<T> T)
	{
		return getRequestResponseDto().getTemporaryObject(key, T);
	}

	public void setParameter(String key, Object value)
	{
		getRequestResponseDto().setParameter(key, value);
	}

	public void addUserMessage(String userMessage)
	{
		getRequestResponseDto().addUserMessage(userMessage);
	}

	public Timestamp getTimeStamp()
	{
		Timestamp timestamp = getClientMilliseconds() == 0l
				? new Timestamp(System.currentTimeMillis())
				: new Timestamp(getClientMilliseconds());

		log.debug("timestamp: [" + timestamp + "]");

		return timestamp;
	}

	private long getClientMilliseconds()
	{
		if (JValidator.isNotNullAndNotEmpty(getParameter(RequestConstant.API_CLIENT_MILLISECONDS)))
		{
			return Long.parseLong(getParameter(RequestConstant.API_CLIENT_MILLISECONDS));
		}

		return 0l;
	}

//	public boolean isRequestSourceAndroid()
//	{
//		return requestResponseDto.isRequestSourceAndroid();
//	}
//
//	public boolean isRequestSourceWEB()
//	{
//		return requestResponseDto.isRequestSourceWEB();
//	}
}
