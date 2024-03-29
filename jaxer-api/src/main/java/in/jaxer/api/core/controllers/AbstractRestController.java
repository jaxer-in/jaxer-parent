package in.jaxer.api.core.controllers;

import in.jaxer.api.annotations.RestTask;
import in.jaxer.api.core.request.RestRequestHandler;
import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.listners.Authentication;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 * @author Shakir
 * @since 0.0.1
 */
@Log4j2
public abstract class AbstractRestController extends AbstractController
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
			RestRequestHandler requestHandler = new RestRequestHandler(getBasePackage(), RestTask.class);
			apiResponseDto = requestHandler.processRequest(connection, request, response, authentication);
		} catch (Exception exception)
		{
			log.error("Exception", exception);

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
