package in.jaxer.api.core.controllers;

import in.jaxer.api.dtos.ApiResponseDto;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shakir
 * 		date 2021-12-21 - 17:46
 * @since 0.0.1
 */
@Log4j2
public abstract class AbstractController extends HttpServlet
{
	abstract protected String getBasePackage();

	protected ApiResponseDto doProcessException(HttpServletResponse response, Exception throwable) throws IOException
	{
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		apiResponseDto.addErrorDto(throwable);

		if (apiResponseDto.errorDto != null)
		{
//			response.setStatus(apiResponseDto.errorDto.httpStatus);
		}

		log.info("apiResponseDto: {}", apiResponseDto);
		return apiResponseDto;
	}
}
