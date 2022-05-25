package in.jaxer.api.tasks;

import in.jaxer.api.core.controllers.AbstractRestController;
import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.api.listners.Authentication;
import in.jaxer.core.net.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * @author Shakir Ansari
 */
@WebServlet(urlPatterns = "/jaxerRestController")
public class RestController extends AbstractRestController
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ApiResponseDto apiResponseDto = doProcess(request, response, null, new AuthenticationImpl());

		System.out.println("apiResponseDto: [" + apiResponseDto + "]");

		Servlets.printJsonResponse(request, response, apiResponseDto);
	}

	@Override
	protected String getBasePackage()
	{
		return "in.jaxer.*";
	}

	private class AuthenticationImpl implements Authentication
	{

		@Override
		public void doAuthentication(Connection connection, RequestResponseDto requestResponseObject)
		{
		}
	}

}
