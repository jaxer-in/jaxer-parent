
package in.jaxer.api.core.tasks;

import in.jaxer.api.constants.RequestConstant;
import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.core.utilities.JUtilities;
import in.jaxer.core.utilities.JValidator;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public abstract class AbstractRestTask
{

	private RequestResponseDto requestResponseDto = null;

	public abstract void doTask(Connection connection) throws Exception;

	public void processAbstractTask(Connection connection) throws Exception
	{
		log.debug("--------------------");

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

	protected RequestResponseDto getRequestResponseDto()
	{
		return requestResponseDto;
	}

	public void setRequestResponseDto(RequestResponseDto requestResponseDto)
	{
		this.requestResponseDto = requestResponseDto;
	}

	public HttpServletRequest getHttpServletRequest()
	{
		return requestResponseDto.getHttpServletRequest();
	}

	public String getParameter(String paramName)
	{
		return requestResponseDto.getParameter(paramName);
	}

	public <T> T getParameter(String paramName, Class<T> T)
	{
		return JUtilities.toObject(getParameter(paramName), T);
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
		return JUtilities.toObjectList(getParameter(paramName), T);
	}

	public void setTemporaryObject(String key, Object value)
	{
		requestResponseDto.setTemporaryObject(key, value);
	}

	public <T> T getTemporaryObject(String key, Class<T> T)
	{
		return requestResponseDto.getTemporaryObject(key, T);
	}

	public void setParameter(String key, Object value)
	{
		requestResponseDto.setParameter(key, value);
	}

	public void addUserMessage(String userMessage)
	{
		requestResponseDto.addUserMessage(userMessage);
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
		if (JValidator.isNotEmpty(getParameter(RequestConstant.API_CLIENT_MILLISECONDS)))
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
