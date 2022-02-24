
package in.jaxer.api.core.controllers;

import in.jaxer.api.annotations.MultipartTask;
import in.jaxer.api.core.request.MultipartRequestHandler;
import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.listners.Authentication;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public abstract class AbstractMultipartController extends AbstractController
{

	protected ApiResponseDto doProcess(HttpServletRequest request, HttpServletResponse response)
	{
		return this.doProcess(request, response, null, null);
	}

	protected ApiResponseDto doProcess(HttpServletRequest request, HttpServletResponse response, Connection connection, Authentication authentication)
	{
		ApiResponseDto apiResponseDto = null;

		try
		{
			MultipartRequestHandler multipartRequestHandler = new MultipartRequestHandler(getBasePackage(), MultipartTask.class);
			apiResponseDto = multipartRequestHandler.processRequest(connection, request, response, authentication);
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
			log.info("apiResponseDto: {}", apiResponseDto);
//			response.setStatus(apiResponseDto.errorDto.httpStatus);
		}

		return apiResponseDto;
	}
}