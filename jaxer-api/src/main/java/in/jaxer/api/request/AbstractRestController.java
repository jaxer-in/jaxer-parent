
package in.jaxer.api.request;

import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.listners.Authentication;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shakir Ansari
 */
public abstract class AbstractRestController extends HttpServlet
{

	abstract protected String getBasePackage();

	protected ApiResponseDto doProcess(HttpServletRequest request, HttpServletResponse response)
	{
		return this.doProcess(request, response, null, null);
	}

	protected ApiResponseDto doProcess(HttpServletRequest request, HttpServletResponse response, Connection connection, Authentication authentication)
	{
		ApiResponseDto apiResponseDto = null;

		try
		{
			RequestHandler requestHandler = new RequestHandler(getBasePackage());
			apiResponseDto = requestHandler.doHandleRequest(connection, request, response, authentication);
		} catch (Exception exception)
		{
			if (apiResponseDto == null)
			{
				apiResponseDto = new ApiResponseDto();
				apiResponseDto.addErrorDto(exception);
			}
		}

		if (apiResponseDto.errorDto != null)
		{
//			response.setStatus(apiResponseDto.errorDto.httpStatus);
		}

		return apiResponseDto;
	}

	protected ApiResponseDto doProcessException(HttpServletResponse response, Exception throwable) throws IOException
	{
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		apiResponseDto.addErrorDto(throwable);

		if (apiResponseDto.errorDto != null)
		{
//			response.setStatus(apiResponseDto.errorDto.httpStatus);
		}

		return apiResponseDto;
	}
}
