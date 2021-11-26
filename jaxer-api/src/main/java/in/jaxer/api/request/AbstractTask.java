
package in.jaxer.api.request;

import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.core.utilities.Utilities;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Shakir Ansari
 */
public abstract class AbstractTask
{

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
		return Utilities.toObject(getParameter(paramName), T);
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
		return Utilities.toObjectList(getParameter(paramName), T);
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
}
